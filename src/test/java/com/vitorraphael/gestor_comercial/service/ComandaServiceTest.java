package com.vitorraphael.gestor_comercial.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.vitorraphael.gestor_comercial.exception.RecursoNaoEncontradoException;
import com.vitorraphael.gestor_comercial.exception.RegraDeNegocioException;
import com.vitorraphael.gestor_comercial.model.Comanda;
import com.vitorraphael.gestor_comercial.model.Mesa;
import com.vitorraphael.gestor_comercial.model.StatusComanda;
import com.vitorraphael.gestor_comercial.model.StatusMesa;
import com.vitorraphael.gestor_comercial.repository.ComandaRepository;
import com.vitorraphael.gestor_comercial.repository.ItemComandaRepository;
import com.vitorraphael.gestor_comercial.repository.MesaRepository;

@ExtendWith(MockitoExtension.class)
class ComandaServiceTest {

    @Mock
    private ComandaRepository comandaRepository;

    @Mock
    private MesaRepository mesaRepository;

    @Mock
    private ItemComandaRepository itemComandaRepository;

    @Mock
    private MovimentoEstoqueService movimentoEstoqueService;

    @Mock
    private FuncionarioService funcionarioService;

    private ComandaService comandaService;

    @BeforeEach
    void setUp() {
        MesaService mesaService = new MesaService(mesaRepository);
        comandaService = new ComandaService(comandaRepository, mesaService, itemComandaRepository, movimentoEstoqueService,
                funcionarioService);
        org.mockito.Mockito.lenient().when(itemComandaRepository.findByComandaId(org.mockito.ArgumentMatchers.any()))
                .thenReturn(java.util.List.of());
    }

    private Mesa criarMesa(Long id, StatusMesa status) {
        Mesa mesa = new Mesa();
        mesa.setId(id);
        mesa.setNumero(1);
        mesa.setStatus(status);
        return mesa;
    }

    @Test
    void deveAbrirComandaEmMesaLivre() {
        Mesa mesa = criarMesa(1L, StatusMesa.LIVRE);
        when(mesaRepository.findById(1L)).thenReturn(Optional.of(mesa));
        when(comandaRepository.findByMesaIdAndStatus(1L, StatusComanda.ABERTA)).thenReturn(Optional.empty());
        when(comandaRepository.save(any(Comanda.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(mesaRepository.save(any(Mesa.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Comanda comanda = comandaService.abrir(1L);

        assertThat(comanda.getStatus()).isEqualTo(StatusComanda.ABERTA);
        assertThat(comanda.getMesa()).isEqualTo(mesa);
        assertThat(comanda.getDataAbertura()).isNotNull();

        ArgumentCaptor<Mesa> mesaCaptor = ArgumentCaptor.forClass(Mesa.class);
        verify(mesaRepository).save(mesaCaptor.capture());
        assertThat(mesaCaptor.getValue().getStatus()).isEqualTo(StatusMesa.OCUPADA);
    }

    @Test
    void deveLancarExcecaoAoAbrirComandaEmMesaJaOcupada() {
        Mesa mesa = criarMesa(1L, StatusMesa.OCUPADA);
        when(mesaRepository.findById(1L)).thenReturn(Optional.of(mesa));
        when(comandaRepository.findByMesaIdAndStatus(1L, StatusComanda.ABERTA))
                .thenReturn(Optional.of(new Comanda()));

        assertThrows(RegraDeNegocioException.class, () -> comandaService.abrir(1L));

        verify(comandaRepository, never()).save(any(Comanda.class));
        verify(mesaRepository, never()).save(any(Mesa.class));
    }

    @Test
    void deveFecharComandaAbertaELiberarMesa() {
        Mesa mesa = criarMesa(1L, StatusMesa.OCUPADA);
        Comanda comanda = new Comanda();
        comanda.setId(10L);
        comanda.setMesa(mesa);
        comanda.setStatus(StatusComanda.ABERTA);

        when(comandaRepository.findById(10L)).thenReturn(Optional.of(comanda));
        when(comandaRepository.save(any(Comanda.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(mesaRepository.save(any(Mesa.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Comanda fechada = comandaService.fechar(10L);

        assertThat(fechada.getStatus()).isEqualTo(StatusComanda.FECHADA);
        assertThat(fechada.getDataFechamento()).isNotNull();

        ArgumentCaptor<Mesa> mesaCaptor = ArgumentCaptor.forClass(Mesa.class);
        verify(mesaRepository).save(mesaCaptor.capture());
        assertThat(mesaCaptor.getValue().getStatus()).isEqualTo(StatusMesa.LIVRE);
    }

    @Test
    void deveLancarExcecaoAoFecharComandaJaFechada() {
        Mesa mesa = criarMesa(1L, StatusMesa.OCUPADA);
        Comanda comanda = new Comanda();
        comanda.setId(10L);
        comanda.setMesa(mesa);
        comanda.setStatus(StatusComanda.FECHADA);

        when(comandaRepository.findById(10L)).thenReturn(Optional.of(comanda));

        assertThrows(RegraDeNegocioException.class, () -> comandaService.fechar(10L));

        verify(comandaRepository, times(1)).findById(10L);
        verify(comandaRepository, never()).save(any(Comanda.class));
        verify(mesaRepository, never()).save(any(Mesa.class));
    }

    @Test
    void deveLancarExcecaoAoBuscarComandaComIdInexistente() {
        when(comandaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class, () -> comandaService.buscarPorId(99L));
    }
}
