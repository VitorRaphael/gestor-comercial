package com.vitorraphael.gestor_comercial.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.vitorraphael.gestor_comercial.dto.ConsumoRegistroResponse;
import com.vitorraphael.gestor_comercial.dto.ConsumosFuncionarioResponse;
import com.vitorraphael.gestor_comercial.dto.QuitacaoRegistroResponse;
import com.vitorraphael.gestor_comercial.dto.SaldoDevedorResponse;
import com.vitorraphael.gestor_comercial.exception.RegraDeNegocioException;
import com.vitorraphael.gestor_comercial.model.FormaPagamento;
import com.vitorraphael.gestor_comercial.model.Funcionario;
import com.vitorraphael.gestor_comercial.model.Pagamento;
import com.vitorraphael.gestor_comercial.model.QuitacaoConsumo;
import com.vitorraphael.gestor_comercial.repository.PagamentoRepository;
import com.vitorraphael.gestor_comercial.repository.QuitacaoConsumoRepository;

@Service
public class QuitacaoConsumoService {

    private final PagamentoRepository pagamentoRepository;
    private final QuitacaoConsumoRepository quitacaoConsumoRepository;
    private final FuncionarioService funcionarioService;

    public QuitacaoConsumoService(PagamentoRepository pagamentoRepository,
            QuitacaoConsumoRepository quitacaoConsumoRepository, FuncionarioService funcionarioService) {
        this.pagamentoRepository = pagamentoRepository;
        this.quitacaoConsumoRepository = quitacaoConsumoRepository;
        this.funcionarioService = funcionarioService;
    }

    public BigDecimal calcularSaldoDevedor(Long funcionarioId) {
        List<Pagamento> consumos = pagamentoRepository
                .findByFormaPagamentoAndFuncionarioConsumidorIdOrderByDataHoraAsc(FormaPagamento.CONSUMO_INTERNO,
                        funcionarioId);
        BigDecimal saldo = BigDecimal.ZERO;
        for (Pagamento pagamento : consumos) {
            saldo = saldo.add(pagamento.getValor().subtract(pagamento.getValorQuitado()));
        }
        return saldo;
    }

    public List<SaldoDevedorResponse> listarFuncionariosComSaldoDevedor() {
        List<SaldoDevedorResponse> resultado = new ArrayList<>();
        for (Funcionario funcionario : funcionarioService.listarAtivos()) {
            BigDecimal saldo = calcularSaldoDevedor(funcionario.getId());
            if (saldo.signum() > 0) {
                resultado.add(new SaldoDevedorResponse(funcionario.getId(), funcionario.getNome(), saldo));
            }
        }
        resultado.sort(Comparator.comparing(SaldoDevedorResponse::nome));
        return resultado;
    }

    public ConsumosFuncionarioResponse listarConsumos(Long funcionarioId) {
        Funcionario funcionario = funcionarioService.buscarPorId(funcionarioId);

        List<Pagamento> consumos = pagamentoRepository
                .findByFormaPagamentoAndFuncionarioConsumidorIdOrderByDataHoraAsc(FormaPagamento.CONSUMO_INTERNO,
                        funcionarioId);
        BigDecimal saldo = BigDecimal.ZERO;
        List<ConsumoRegistroResponse> consumoResponses = new ArrayList<>();
        for (Pagamento pagamento : consumos) {
            saldo = saldo.add(pagamento.getValor().subtract(pagamento.getValorQuitado()));
            consumoResponses.add(new ConsumoRegistroResponse(
                    pagamento.getId(),
                    pagamento.getDataHora(),
                    pagamento.getComanda().getId(),
                    "Mesa " + pagamento.getComanda().getMesa().getNumero(),
                    pagamento.getValor(),
                    pagamento.getValorQuitado()));
        }

        List<QuitacaoRegistroResponse> quitacaoResponses = quitacaoConsumoRepository
                .findByFuncionarioIdOrderByDataHoraDesc(funcionarioId).stream()
                .map(q -> new QuitacaoRegistroResponse(q.getId(), q.getDataHora(), q.getValor(),
                        q.getAutorizadoPor().getNome()))
                .toList();

        return new ConsumosFuncionarioResponse(funcionario.getId(), funcionario.getNome(), saldo, consumoResponses,
                quitacaoResponses);
    }

    public BigDecimal quitar(Long funcionarioId, BigDecimal valor, String pinGerente) {
        Funcionario funcionario = funcionarioService.buscarPorId(funcionarioId);
        Funcionario gerente = funcionarioService.validarPinGerente(pinGerente);

        if (valor == null || valor.signum() <= 0) {
            throw new RegraDeNegocioException("O valor a quitar deve ser maior que zero.");
        }

        List<Pagamento> consumosPendentes = pagamentoRepository
                .findByFormaPagamentoAndFuncionarioConsumidorIdOrderByDataHoraAsc(FormaPagamento.CONSUMO_INTERNO,
                        funcionarioId);

        BigDecimal saldoDevedor = BigDecimal.ZERO;
        for (Pagamento pagamento : consumosPendentes) {
            saldoDevedor = saldoDevedor.add(pagamento.getValor().subtract(pagamento.getValorQuitado()));
        }

        if (valor.compareTo(saldoDevedor) > 0) {
            throw new RegraDeNegocioException(
                    "O valor a quitar não pode exceder o saldo devedor de " + saldoDevedor + ".");
        }

        BigDecimal restante = valor;
        for (Pagamento pagamento : consumosPendentes) {
            if (restante.signum() == 0) {
                break;
            }
            BigDecimal pendenteNoRegistro = pagamento.getValor().subtract(pagamento.getValorQuitado());
            if (pendenteNoRegistro.signum() <= 0) {
                continue;
            }
            BigDecimal aplicado = pendenteNoRegistro.min(restante);
            pagamento.setValorQuitado(pagamento.getValorQuitado().add(aplicado));
            pagamentoRepository.save(pagamento);
            restante = restante.subtract(aplicado);
        }

        QuitacaoConsumo quitacao = new QuitacaoConsumo();
        quitacao.setFuncionario(funcionario);
        quitacao.setValor(valor);
        quitacao.setDataHora(LocalDateTime.now());
        quitacao.setAutorizadoPor(gerente);
        quitacaoConsumoRepository.save(quitacao);

        return calcularSaldoDevedor(funcionarioId);
    }
}
