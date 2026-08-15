package com.vitorraphael.gestor_comercial.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.vitorraphael.gestor_comercial.exception.RecursoNaoEncontradoException;
import com.vitorraphael.gestor_comercial.exception.RegraDeNegocioException;
import com.vitorraphael.gestor_comercial.model.Categoria;
import com.vitorraphael.gestor_comercial.model.Impressora;
import com.vitorraphael.gestor_comercial.repository.CategoriaRepository;
import com.vitorraphael.gestor_comercial.repository.ImpressoraRepository;
import com.vitorraphael.gestor_comercial.repository.ProdutoRepository;

@ExtendWith(MockitoExtension.class)
class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private ImpressoraRepository impressoraRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    private CategoriaService categoriaService;

    @BeforeEach
    void setUp() {
        ImpressoraService impressoraService = new ImpressoraService(impressoraRepository);
        categoriaService = new CategoriaService(categoriaRepository, impressoraService, produtoRepository);
    }

    @Test
    void deveAssociarImpressoraAUmaCategoria() {
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNome("Lanches");

        Impressora impressora = new Impressora();
        impressora.setId(2L);
        impressora.setNome("Trailer 1");

        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        when(impressoraRepository.findById(2L)).thenReturn(Optional.of(impressora));
        when(categoriaRepository.save(any(Categoria.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Categoria associada = categoriaService.associarImpressora(1L, 2L);

        assertThat(associada.getImpressora()).isEqualTo(impressora);
    }

    @Test
    void deveLancarExcecaoAoCriarCategoriaComNomeJaExistente() {
        Categoria existente = new Categoria();
        existente.setId(1L);
        existente.setNome("Lanches");

        when(categoriaRepository.findByNome("Lanches")).thenReturn(Optional.of(existente));

        assertThrows(RegraDeNegocioException.class, () -> categoriaService.criar("Lanches"));

        verify(categoriaRepository, never()).save(any(Categoria.class));
    }

    @Test
    void deveCriarCategoriaComNomeInexistente() {
        when(categoriaRepository.findByNome("Bebidas")).thenReturn(Optional.empty());
        when(categoriaRepository.save(any(Categoria.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Categoria categoria = categoriaService.criar("Bebidas");

        assertThat(categoria.getNome()).isEqualTo("Bebidas");
    }

    @Test
    void deveLancarExcecaoAoBuscarCategoriaComIdInexistente() {
        when(categoriaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class, () -> categoriaService.buscarPorId(99L));
    }

    @Test
    void deveEditarNomeDaCategoria() {
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNome("Lanches");

        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        when(categoriaRepository.findByNome("Bebidas")).thenReturn(Optional.empty());
        when(categoriaRepository.save(any(Categoria.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Categoria editada = categoriaService.editar(1L, "Bebidas");

        assertThat(editada.getNome()).isEqualTo("Bebidas");
    }

    @Test
    void deveExcluirCategoriaSemProdutosVinculados() {
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNome("Lanches");

        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        when(produtoRepository.existsByCategoriaId(1L)).thenReturn(false);

        categoriaService.excluir(1L);

        verify(categoriaRepository).delete(categoria);
    }

    @Test
    void deveLancarExcecaoAoExcluirCategoriaComProdutosVinculados() {
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNome("Lanches");

        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        when(produtoRepository.existsByCategoriaId(1L)).thenReturn(true);

        assertThrows(RegraDeNegocioException.class, () -> categoriaService.excluir(1L));

        verify(categoriaRepository, never()).delete(any(Categoria.class));
    }
}
