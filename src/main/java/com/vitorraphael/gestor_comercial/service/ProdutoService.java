package com.vitorraphael.gestor_comercial.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

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

    public Produto criar(String nome, BigDecimal preco, BigDecimal custo, Long categoriaId) {
        Categoria categoria = categoriaService.buscarPorId(categoriaId);

        Produto produto = new Produto();
        produto.setNome(nome);
        produto.setPreco(preco);
        produto.setCusto(custo);
        produto.setCategoria(categoria);
        produto.setAtivo(true);
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

    public Produto atualizar(Long produtoId, String nome, BigDecimal preco, BigDecimal custo, Long categoriaId) {
        Produto produto = buscarPorId(produtoId);
        Categoria categoria = categoriaService.buscarPorId(categoriaId);

        produto.setNome(nome);
        produto.setPreco(preco);
        produto.setCusto(custo);
        produto.setCategoria(categoria);
        return produtoRepository.save(produto);
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
