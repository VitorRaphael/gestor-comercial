package com.vitorraphael.gestor_comercial.service;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vitorraphael.gestor_comercial.exception.RecursoNaoEncontradoException;
import com.vitorraphael.gestor_comercial.exception.RegraDeNegocioException;
import com.vitorraphael.gestor_comercial.model.Categoria;
import com.vitorraphael.gestor_comercial.model.Produto;
import com.vitorraphael.gestor_comercial.repository.ComboItemRepository;
import com.vitorraphael.gestor_comercial.repository.FichaTecnicaRepository;
import com.vitorraphael.gestor_comercial.repository.ItemComandaRepository;
import com.vitorraphael.gestor_comercial.repository.ProdutoRepository;

@Service
public class ProdutoService {

    private static final Set<String> EXTENSOES_FOTO_PERMITIDAS = Set.of("jpg", "jpeg", "png", "webp");
    private static final Path DIRETORIO_FOTOS = Path.of("uploads", "produtos");

    private final ProdutoRepository produtoRepository;
    private final CategoriaService categoriaService;
    private final ItemComandaRepository itemComandaRepository;
    private final ComboItemRepository comboItemRepository;
    private final FichaTecnicaRepository fichaTecnicaRepository;

    public ProdutoService(ProdutoRepository produtoRepository, CategoriaService categoriaService,
            ItemComandaRepository itemComandaRepository, ComboItemRepository comboItemRepository,
            FichaTecnicaRepository fichaTecnicaRepository) {
        this.produtoRepository = produtoRepository;
        this.categoriaService = categoriaService;
        this.itemComandaRepository = itemComandaRepository;
        this.comboItemRepository = comboItemRepository;
        this.fichaTecnicaRepository = fichaTecnicaRepository;
    }

    public Produto criar(String nome, BigDecimal preco, BigDecimal custo, Long categoriaId, String descricao) {
        Categoria categoria = categoriaService.buscarPorId(categoriaId);

        Produto produto = new Produto();
        produto.setNome(nome);
        produto.setPreco(preco);
        produto.setCusto(custo);
        produto.setCategoria(categoria);
        produto.setAtivo(true);
        produto.setDescricao(descricao);
        return produtoRepository.save(produto);
    }

    public List<Produto> listarAtivos() {
        return produtoRepository.findAll().stream()
                .filter(Produto::isAtivo)
                .filter(p -> p.getCategoria().isAtivo())
                .toList();
    }

    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    public Produto atualizar(Long produtoId, String nome, BigDecimal preco, BigDecimal custo, Long categoriaId, String descricao) {
        Produto produto = buscarPorId(produtoId);
        Categoria categoria = categoriaService.buscarPorId(categoriaId);

        produto.setNome(nome);
        produto.setPreco(preco);
        produto.setCusto(custo);
        produto.setCategoria(categoria);
        produto.setDescricao(descricao);
        return produtoRepository.save(produto);
    }

    /**
     * Salva a foto em disco (fora do classpath, para sobreviver a rebuilds do
     * jar) e substitui a anterior, se houver. Servida via WebConfig em
     * /uploads/**.
     */
    public Produto atualizarFoto(Long produtoId, MultipartFile foto) {
        Produto produto = buscarPorId(produtoId);

        if (foto == null || foto.isEmpty()) {
            throw new RegraDeNegocioException("A foto enviada está vazia.");
        }

        String extensao = extensaoDe(foto.getOriginalFilename());
        if (!EXTENSOES_FOTO_PERMITIDAS.contains(extensao)) {
            throw new RegraDeNegocioException("Formato de imagem não suportado. Use JPG, PNG ou WEBP.");
        }

        try {
            Files.createDirectories(DIRETORIO_FOTOS);
            String nomeArquivo = produtoId + "." + extensao;
            Path destino = DIRETORIO_FOTOS.resolve(nomeArquivo);
            for (String extensaoAntiga : EXTENSOES_FOTO_PERMITIDAS) {
                if (!extensaoAntiga.equals(extensao)) {
                    Files.deleteIfExists(DIRETORIO_FOTOS.resolve(produtoId + "." + extensaoAntiga));
                }
            }
            foto.transferTo(destino);
            produto.setFotoUrl("/uploads/produtos/" + nomeArquivo + "?v=" + System.currentTimeMillis());
        } catch (IOException e) {
            throw new UncheckedIOException("Falha ao salvar a foto do produto.", e);
        }

        return produtoRepository.save(produto);
    }

    private String extensaoDe(String nomeArquivo) {
        if (nomeArquivo == null || !nomeArquivo.contains(".")) {
            return "";
        }
        return nomeArquivo.substring(nomeArquivo.lastIndexOf('.') + 1).toLowerCase();
    }

    public Produto desativar(Long produtoId) {
        Produto produto = buscarPorId(produtoId);
        produto.setAtivo(false);
        return produtoRepository.save(produto);
    }

    public void excluir(Long produtoId) {
        Produto produto = buscarPorId(produtoId);
        if (itemComandaRepository.existsByProdutoId(produtoId)) {
            throw new RegraDeNegocioException(
                    "Não é possível excluir o produto '" + produto.getNome() + "' pois ele já foi vendido em alguma comanda.");
        }
        if (comboItemRepository.existsByProdutoComboId(produtoId) || comboItemRepository.existsByProdutoComponenteId(produtoId)) {
            throw new RegraDeNegocioException(
                    "Não é possível excluir o produto '" + produto.getNome() + "' pois ele está vinculado a um combo.");
        }
        if (fichaTecnicaRepository.existsByProdutoId(produtoId)) {
            throw new RegraDeNegocioException(
                    "Não é possível excluir o produto '" + produto.getNome() + "' pois ele tem ficha técnica cadastrada.");
        }
        produtoRepository.delete(produto);
    }

    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado: " + id));
    }
}
