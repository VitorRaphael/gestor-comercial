package com.vitorraphael.gestor_comercial.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.vitorraphael.gestor_comercial.exception.RecursoNaoEncontradoException;
import com.vitorraphael.gestor_comercial.model.Categoria;
import com.vitorraphael.gestor_comercial.model.Produto;
import com.vitorraphael.gestor_comercial.repository.CategoriaRepository;
import com.vitorraphael.gestor_comercial.repository.ProdutoRepository;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    private ProdutoService produtoService;

    @BeforeEach
    void setUp() {
        ImpressoraService impressoraService = new ImpressoraService(null);
        CategoriaService categoriaService = new CategoriaService(categoriaRepository, impressoraService);
        produtoService = new ProdutoService(produtoRepository, categoriaService);
    }

    @Test
    void deveLancarExcecaoAoCriarProdutoComCategoriaInexistente() {
        when(categoriaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class,
                () -> produtoService.criar("X-Burger", new BigDecimal("25.90"), null, 1L));

        verify(produtoRepository, never()).save(any(Produto.class));
    }

    @Test
    void deveCriarProdutoComCategoriaExistente() {
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNome("Lanches");

        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        when(produtoRepository.save(any(Produto.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Produto produto = produtoService.criar("X-Burger", new BigDecimal("25.90"), null, 1L);

        assertThat(produto.getNome()).isEqualTo("X-Burger");
        assertThat(produto.getPreco()).isEqualByComparingTo(new BigDecimal("25.90"));
        assertThat(produto.getCategoria()).isEqualTo(categoria);
        assertThat(produto.isAtivo()).isTrue();
    }

    @Test
    void deveDesativarProdutoSemRemoverDoRepositorio() {
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setNome("X-Burger");
        produto.setPreco(new BigDecimal("25.90"));
        produto.setAtivo(true);

        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        when(produtoRepository.save(any(Produto.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Produto desativado = produtoService.desativar(1L);

        assertThat(desativado.isAtivo()).isFalse();

        ArgumentCaptor<Produto> captor = ArgumentCaptor.forClass(Produto.class);
        verify(produtoRepository).save(captor.capture());
        assertThat(captor.getValue().isAtivo()).isFalse();
        verify(produtoRepository, never()).delete(any(Produto.class));
        verify(produtoRepository, never()).deleteById(any());
    }

    @Test
    void deveLancarExcecaoAoBuscarProdutoComIdInexistente() {
        when(produtoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class, () -> produtoService.buscarPorId(99L));
    }
}
