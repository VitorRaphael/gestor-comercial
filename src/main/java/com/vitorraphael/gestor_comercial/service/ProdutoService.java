package com.vitorraphael.gestor_comercial.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.vitorraphael.gestor_comercial.exception.RecursoNaoEncontradoException;
import com.vitorraphael.gestor_comercial.model.Categoria;
import com.vitorraphael.gestor_comercial.model.Produto;
import com.vitorraphael.gestor_comercial.repository.ProdutoRepository;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaService categoriaService;

    public ProdutoService(ProdutoRepository produtoRepository, CategoriaService categoriaService) {
        this.produtoRepository = produtoRepository;
        this.categoriaService = categoriaService;
    }

    public Produto criar(String nome, BigDecimal preco, Long categoriaId) {
        Categoria categoria = categoriaService.buscarPorId(categoriaId);

        Produto produto = new Produto();
        produto.setNome(nome);
        produto.setPreco(preco);
        produto.setCategoria(categoria);
        produto.setAtivo(true);
        return produtoRepository.save(produto);
    }

    public List<Produto> listarAtivos() {
        return produtoRepository.findAll().stream()
                .filter(Produto::isAtivo)
                .toList();
    }

    public Produto atualizar(Long produtoId, String nome, BigDecimal preco, Long categoriaId) {
        Produto produto = buscarPorId(produtoId);
        Categoria categoria = categoriaService.buscarPorId(categoriaId);

        produto.setNome(nome);
        produto.setPreco(preco);
        produto.setCategoria(categoria);
        return produtoRepository.save(produto);
    }

    public Produto desativar(Long produtoId) {
        Produto produto = buscarPorId(produtoId);
        produto.setAtivo(false);
        return produtoRepository.save(produto);
    }

    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado: " + id));
    }
}
