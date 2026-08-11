package com.vitorraphael.gestor_comercial.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.vitorraphael.gestor_comercial.exception.RegraDeNegocioException;
import com.vitorraphael.gestor_comercial.model.Caixa;
import com.vitorraphael.gestor_comercial.model.MovimentoCaixa;
import com.vitorraphael.gestor_comercial.model.StatusCaixa;
import com.vitorraphael.gestor_comercial.model.TipoMovimento;
import com.vitorraphael.gestor_comercial.repository.CaixaRepository;
import com.vitorraphael.gestor_comercial.repository.MovimentoCaixaRepository;

@ExtendWith(MockitoExtension.class)
class MovimentoCaixaServiceTest {

    @Mock
    private MovimentoCaixaRepository movimentoCaixaRepository;

    @Mock
    private CaixaRepository caixaRepository;

    private MovimentoCaixaService movimentoCaixaService;

    @BeforeEach
    void setUp() {
        CaixaService caixaService = new CaixaService(caixaRepository, movimentoCaixaRepository, null);
        movimentoCaixaService = new MovimentoCaixaService(movimentoCaixaRepository, caixaService);
    }

    private Caixa criarCaixa(Long id, StatusCaixa status) {
        Caixa caixa = new Caixa();
        caixa.setId(id);
        caixa.setStatus(status);
        caixa.setDataAbertura(LocalDateTime.now());
        caixa.setValorAbertura(new BigDecimal("100"));
        return caixa;
    }

    @Test
    void deveLancarExcecaoAoRegistrarMovimentoQuandoNaoHaCaixaAberto() {
        when(caixaRepository.findByStatus(StatusCaixa.ABERTO)).thenReturn(Optional.empty());

        assertThrows(RegraDeNegocioException.class,
                () -> movimentoCaixaService.registrar(TipoMovimento.SANGRIA, BigDecimal.TEN, "vale"));

        verify(movimentoCaixaRepository, never()).save(any(MovimentoCaixa.class));
    }

    @Test
    void deveRegistrarMovimentoComCaixaAberto() {
        Caixa caixa = criarCaixa(1L, StatusCaixa.ABERTO);
        when(caixaRepository.findByStatus(StatusCaixa.ABERTO)).thenReturn(Optional.of(caixa));
        when(movimentoCaixaRepository.save(any(MovimentoCaixa.class))).thenAnswer(invocation -> invocation.getArgument(0));

        MovimentoCaixa movimento = movimentoCaixaService.registrar(TipoMovimento.DESPESA, new BigDecimal("20"),
                "compra de gelo");

        ArgumentCaptor<MovimentoCaixa> captor = ArgumentCaptor.forClass(MovimentoCaixa.class);
        verify(movimentoCaixaRepository).save(captor.capture());

        MovimentoCaixa salvo = captor.getValue();
        assertThat(salvo.getCaixa()).isEqualTo(caixa);
        assertThat(salvo.getTipo()).isEqualTo(TipoMovimento.DESPESA);
        assertThat(salvo.getValor()).isEqualByComparingTo("20");
        assertThat(salvo.getDescricao()).isEqualTo("compra de gelo");
        assertThat(salvo.getDataHora()).isNotNull();

        assertThat(movimento.getCaixa()).isEqualTo(caixa);
    }
}
