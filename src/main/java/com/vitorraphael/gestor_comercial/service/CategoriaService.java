package com.vitorraphael.gestor_comercial.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vitorraphael.gestor_comercial.exception.RecursoNaoEncontradoException;
import com.vitorraphael.gestor_comercial.exception.RegraDeNegocioException;
import com.vitorraphael.gestor_comercial.model.Categoria;
import com.vitorraphael.gestor_comercial.model.Impressora;
import com.vitorraphael.gestor_comercial.repository.CategoriaRepository;
import com.vitorraphael.gestor_comercial.repository.ProdutoRepository;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final ImpressoraService impressoraService;
    private final ProdutoRepository produtoRepository;

    public CategoriaService(CategoriaRepository categoriaRepository, ImpressoraService impressoraService,
            ProdutoRepository produtoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.impressoraService = impressoraService;
        this.produtoRepository = produtoRepository;
    }

    public Categoria criar(String nome) {
        categoriaRepository.findByNome(nome).ifPresent(c -> {
            throw new RegraDeNegocioException("Já existe uma categoria com o nome '" + nome + "'.");
        });

        Categoria categoria = new Categoria();
        categoria.setNome(nome);
        return categoriaRepository.save(categoria);
    }

    public List<Categoria> listarTodas() {
        return categoriaRepository.findAll();
    }

    public Categoria buscarPorId(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Categoria não encontrada: " + id));
    }

    public Categoria associarImpressora(Long categoriaId, Long impressoraId) {
        Categoria categoria = buscarPorId(categoriaId);
        Impressora impressora = impressoraService.buscarPorId(impressoraId);
        categoria.setImpressora(impressora);
        return categoriaRepository.save(categoria);
    }

    public Categoria editar(Long id, String nome) {
        Categoria categoria = buscarPorId(id);
        categoriaRepository.findByNome(nome).ifPresent(c -> {
            if (!c.getId().equals(id)) {
                throw new RegraDeNegocioException("Já existe uma categoria com o nome '" + nome + "'.");
            }
        });
        categoria.setNome(nome);
        return categoriaRepository.save(categoria);
    }

    public void excluir(Long id) {
        Categoria categoria = buscarPorId(id);
        if (produtoRepository.existsByCategoriaId(id)) {
            throw new RegraDeNegocioException(
                    "Não é possível excluir a categoria '" + categoria.getNome() + "' pois há produtos vinculados a ela.");
        }
        categoriaRepository.delete(categoria);
    }

    public Categoria desativar(Long id) {
        Categoria categoria = buscarPorId(id);
        categoria.setAtivo(false);
        return categoriaRepository.save(categoria);
    }
}
