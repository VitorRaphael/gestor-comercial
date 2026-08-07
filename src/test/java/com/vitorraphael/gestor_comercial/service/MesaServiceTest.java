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
import com.vitorraphael.gestor_comercial.model.Mesa;
import com.vitorraphael.gestor_comercial.model.StatusMesa;
import com.vitorraphael.gestor_comercial.repository.MesaRepository;

@ExtendWith(MockitoExtension.class)
class MesaServiceTest {

    @Mock
    private MesaRepository mesaRepository;

    private MesaService mesaService;

    @BeforeEach
    void setUp() {
        mesaService = new MesaService(mesaRepository);
    }

    @Test
    void deveLancarExcecaoAoCriarMesaComNumeroJaExistente() {
        Mesa existente = new Mesa();
        existente.setId(1L);
        existente.setNumero(5);
        existente.setStatus(StatusMesa.LIVRE);

        when(mesaRepository.findByNumero(5)).thenReturn(Optional.of(existente));

        assertThrows(RegraDeNegocioException.class, () -> mesaService.criar(5));

        verify(mesaRepository, never()).save(any(Mesa.class));
    }

    @Test
    void deveCriarMesaComNumeroInexistenteEStatusLivre() {
        when(mesaRepository.findByNumero(5)).thenReturn(Optional.empty());
        when(mesaRepository.save(any(Mesa.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Mesa mesa = mesaService.criar(5);

        assertThat(mesa.getNumero()).isEqualTo(5);
        assertThat(mesa.getStatus()).isEqualTo(StatusMesa.LIVRE);
    }

    @Test
    void deveLancarExcecaoAoBuscarMesaComIdInexistente() {
        when(mesaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class, () -> mesaService.buscarPorId(99L));
    }
}
