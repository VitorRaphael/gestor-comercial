package com.vitorraphael.gestor_comercial.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.vitorraphael.gestor_comercial.exception.RecursoNaoEncontradoException;
import com.vitorraphael.gestor_comercial.exception.RegraDeNegocioException;
import com.vitorraphael.gestor_comercial.model.Caixa;
import com.vitorraphael.gestor_comercial.model.MovimentoCaixa;
import com.vitorraphael.gestor_comercial.model.StatusCaixa;
import com.vitorraphael.gestor_comercial.model.TipoMovimento;
import com.vitorraphael.gestor_comercial.repository.CaixaRepository;
import com.vitorraphael.gestor_comercial.repository.MovimentoCaixaRepository;

@ExtendWith(MockitoExtension.class)
class CaixaServiceTest {

    @Mock
    private CaixaRepository caixaRepository;

    @Mock
    private MovimentoCaixaRepository movimentoCaixaRepository;

    private CaixaService caixaService;

    @BeforeEach
    void setUp() {
        caixaService = new CaixaService(caixaRepository, movimentoCaixaRepository);
    }

    private Caixa criarCaixa(Long id, StatusCaixa status, BigDecimal valorAbertura) {
        Caixa caixa = new Caixa();
        caixa.setId(id);
        caixa.setStatus(status);
        caixa.setDataAbertura(LocalDateTime.now());
        caixa.setValorAbertura(valorAbertura);
        return caixa;
    }

    private MovimentoCaixa criarMovimento(Caixa caixa, TipoMovimento tipo, BigDecimal valor) {
        MovimentoCaixa movimento = new MovimentoCaixa();
        movimento.setCaixa(caixa);
        movimento.setTipo(tipo);
        movimento.setValor(valor);
        movimento.setDescricao("teste");
        movimento.setDataHora(LocalDateTime.now());
        return movimento;
    }

    @Test
    void deveAbrirCaixaQuandoNaoHaNenhumAberto() {
        when(caixaRepository.findByStatus(StatusCaixa.ABERTO)).thenReturn(Optional.empty());
        when(caixaRepository.save(any(Caixa.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Caixa caixa = caixaService.abrir(new BigDecimal("100"));

        assertThat(caixa.getStatus()).isEqualTo(StatusCaixa.ABERTO);
        assertThat(caixa.getValorAbertura()).isEqualByComparingTo("100");
        assertThat(caixa.getDataAbertura()).isNotNull();
    }

    @Test
    void deveLancarExcecaoAoAbrirCaixaQuandoJaExisteUmAberto() {
        when(caixaRepository.findByStatus(StatusCaixa.ABERTO))
                .thenReturn(Optional.of(criarCaixa(1L, StatusCaixa.ABERTO, BigDecimal.TEN)));

        assertThrows(RegraDeNegocioException.class, () -> caixaService.abrir(BigDecimal.TEN));
    }

    @Test
    void deveFecharCaixaAbertoESetarCamposCertos() {
        Caixa caixa = criarCaixa(1L, StatusCaixa.ABERTO, new BigDecimal("100"));
        when(caixaRepository.findById(1L)).thenReturn(Optional.of(caixa));
        when(caixaRepository.save(any(Caixa.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Caixa fechado = caixaService.fechar(1L, new BigDecimal("95"), "faltou troco");

        assertThat(fechado.getStatus()).isEqualTo(StatusCaixa.FECHADO);
        assertThat(fechado.getDataFechamento()).isNotNull();
        assertThat(fechado.getValorFechamento()).isEqualByComparingTo("95");
        assertThat(fechado.getObservacaoFechamento()).isEqualTo("faltou troco");
    }

    @Test
    void deveLancarExcecaoAoFecharCaixaJaFechado() {
        Caixa caixa = criarCaixa(1L, StatusCaixa.FECHADO, new BigDecimal("100"));
        when(caixaRepository.findById(1L)).thenReturn(Optional.of(caixa));

        assertThrows(RegraDeNegocioException.class,
                () -> caixaService.fechar(1L, new BigDecimal("95"), null));
    }

    @Test
    void deveLancarExcecaoAoBuscarCaixaComIdInexistente() {
        when(caixaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class, () -> caixaService.buscarPorId(99L));
    }

    @Test
    void deveLancarExcecaoAoBuscarAbertoQuandoNaoHaCaixaAberto() {
        when(caixaRepository.findByStatus(StatusCaixa.ABERTO)).thenReturn(Optional.empty());

        assertThrows(RegraDeNegocioException.class, () -> caixaService.buscarAberto());
    }

    @Test
    void deveCalcularSaldoEsperadoComMisturaDeMovimentos() {
        Caixa caixa = criarCaixa(1L, StatusCaixa.ABERTO, new BigDecimal("100"));
        when(caixaRepository.findById(1L)).thenReturn(Optional.of(caixa));

        List<MovimentoCaixa> movimentos = List.of(
                criarMovimento(caixa, TipoMovimento.REFORCO, new BigDecimal("50")),
                criarMovimento(caixa, TipoMovimento.SANGRIA, new BigDecimal("30")),
                criarMovimento(caixa, TipoMovimento.DESPESA, new BigDecimal("20")),
                criarMovimento(caixa, TipoMovimento.CONSUMO_FUNCIONARIO, new BigDecimal("10")));
        when(movimentoCaixaRepository.findByCaixaId(1L)).thenReturn(movimentos);

        BigDecimal saldo = caixaService.calcularSaldoEsperado(1L);

        // 100 + 50 - 30 - 20 - 10 = 90
        assertThat(saldo).isEqualByComparingTo("90");
    }
}
