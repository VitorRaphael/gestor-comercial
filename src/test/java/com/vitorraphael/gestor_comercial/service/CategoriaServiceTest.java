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
import com.vitorraphael.gestor_comercial.repository.CategoriaRepository;

@ExtendWith(MockitoExtension.class)
class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    private CategoriaService categoriaService;

    @BeforeEach
    void setUp() {
        categoriaService = new CategoriaService(categoriaRepository);
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
}
