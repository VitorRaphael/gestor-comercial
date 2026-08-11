package com.vitorraphael.gestor_comercial.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.vitorraphael.gestor_comercial.exception.RecursoNaoEncontradoException;
import com.vitorraphael.gestor_comercial.exception.RegraDeNegocioException;
import com.vitorraphael.gestor_comercial.model.Caixa;
import com.vitorraphael.gestor_comercial.model.MovimentoCaixa;
import com.vitorraphael.gestor_comercial.model.StatusCaixa;
import com.vitorraphael.gestor_comercial.model.TipoMovimento;
import com.vitorraphael.gestor_comercial.repository.CaixaRepository;
import com.vitorraphael.gestor_comercial.repository.MovimentoCaixaRepository;

@Service
public class CaixaService {

    private final CaixaRepository caixaRepository;
    private final MovimentoCaixaRepository movimentoCaixaRepository;

    public CaixaService(CaixaRepository caixaRepository, MovimentoCaixaRepository movimentoCaixaRepository) {
        this.caixaRepository = caixaRepository;
        this.movimentoCaixaRepository = movimentoCaixaRepository;
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

    // Saldo esperado de dinheiro físico movimentado manualmente no caixa.
    // NÃO inclui receita de vendas das comandas — cálculo de vendas fica
    // para uma fase futura.
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
        return saldo;
    }
}
