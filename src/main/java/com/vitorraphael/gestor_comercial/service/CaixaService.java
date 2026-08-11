package com.vitorraphael.gestor_comercial.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.vitorraphael.gestor_comercial.exception.RecursoNaoEncontradoException;
import com.vitorraphael.gestor_comercial.exception.RegraDeNegocioException;
import com.vitorraphael.gestor_comercial.model.Caixa;
import com.vitorraphael.gestor_comercial.model.FormaPagamento;
import com.vitorraphael.gestor_comercial.model.MovimentoCaixa;
import com.vitorraphael.gestor_comercial.model.Pagamento;
import com.vitorraphael.gestor_comercial.model.StatusCaixa;
import com.vitorraphael.gestor_comercial.model.TipoMovimento;
import com.vitorraphael.gestor_comercial.repository.CaixaRepository;
import com.vitorraphael.gestor_comercial.repository.MovimentoCaixaRepository;
import com.vitorraphael.gestor_comercial.repository.PagamentoRepository;

@Service
public class CaixaService {

    private static final List<FormaPagamento> FORMAS_MAQUININHA = List.of(
            FormaPagamento.CREDITO, FormaPagamento.DEBITO, FormaPagamento.PIX);

    private final CaixaRepository caixaRepository;
    private final MovimentoCaixaRepository movimentoCaixaRepository;
    private final PagamentoRepository pagamentoRepository;

    public CaixaService(CaixaRepository caixaRepository, MovimentoCaixaRepository movimentoCaixaRepository,
            PagamentoRepository pagamentoRepository) {
        this.caixaRepository = caixaRepository;
        this.movimentoCaixaRepository = movimentoCaixaRepository;
        this.pagamentoRepository = pagamentoRepository;
    }

    public Caixa abrir(BigDecimal valorAbertura) {
        caixaRepository.findByStatus(StatusCaixa.ABERTO).ifPresent(c -> {
            throw new RegraDeNegocioException("Já existe um caixa aberto.");
        });

        Caixa caixa = new Caixa();
        caixa.setStatus(StatusCaixa.ABERTO);
        caixa.setDataAbertura(LocalDateTime.now());
        caixa.setValorAbertura(valorAbertura);
        return caixaRepository.save(caixa);
    }

    public Caixa fechar(Long caixaId, BigDecimal valorFechamento, String observacao) {
        Caixa caixa = buscarPorId(caixaId);

        if (caixa.getStatus() == StatusCaixa.FECHADO) {
            throw new RegraDeNegocioException("Caixa " + caixaId + " já está fechado.");
        }

        caixa.setStatus(StatusCaixa.FECHADO);
        caixa.setDataFechamento(LocalDateTime.now());
        caixa.setValorFechamento(valorFechamento);
        caixa.setObservacaoFechamento(observacao);
        return caixaRepository.save(caixa);
    }

    public Caixa buscarPorId(Long id) {
        return caixaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Caixa não encontrado: " + id));
    }

    public Caixa buscarAberto() {
        return caixaRepository.findByStatus(StatusCaixa.ABERTO)
                .orElseThrow(() -> new RegraDeNegocioException("Não há nenhum caixa aberto no momento."));
    }

    public Caixa buscarUltimoFechado() {
        return caixaRepository.findFirstByStatusOrderByDataFechamentoDesc(StatusCaixa.FECHADO)
                .orElseThrow(() -> new RegraDeNegocioException("Nenhum caixa foi fechado ainda."));
    }

    public BigDecimal calcularSaldoEsperado(Long caixaId) {
        Caixa caixa = buscarPorId(caixaId);
        List<MovimentoCaixa> movimentos = movimentoCaixaRepository.findByCaixaId(caixaId);

        BigDecimal saldo = caixa.getValorAbertura();
        for (MovimentoCaixa movimento : movimentos) {
            switch (movimento.getTipo()) {
                case REFORCO -> saldo = saldo.add(movimento.getValor());
                case SANGRIA, DESPESA, CONSUMO_FUNCIONARIO -> saldo = saldo.subtract(movimento.getValor());
                default -> throw new IllegalStateException("Tipo de movimento não tratado: " + movimento.getTipo());
            }
        }

        List<Pagamento> pagamentosDinheiro = pagamentoRepository
                .findByFormaPagamentoAndDataHoraGreaterThanEqual(FormaPagamento.DINHEIRO, caixa.getDataAbertura());
        for (Pagamento pagamento : pagamentosDinheiro) {
            saldo = saldo.add(pagamento.getValor());
        }

        return saldo;
    }

    public BigDecimal calcularVendasMaquininha(Long caixaId) {
        Caixa caixa = buscarPorId(caixaId);

        List<Pagamento> pagamentosMaquininha = pagamentoRepository
                .findByFormaPagamentoInAndDataHoraGreaterThanEqual(FORMAS_MAQUININHA, caixa.getDataAbertura());

        BigDecimal total = BigDecimal.ZERO;
        for (Pagamento pagamento : pagamentosMaquininha) {
            total = total.add(pagamento.getValor());
        }
        return total;
    }
}
