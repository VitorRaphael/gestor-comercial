package com.vitorraphael.gestor_comercial.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.vitorraphael.gestor_comercial.dto.PagamentoResponse;
import com.vitorraphael.gestor_comercial.exception.RegraDeNegocioException;
import com.vitorraphael.gestor_comercial.model.Comanda;
import com.vitorraphael.gestor_comercial.model.FormaPagamento;
import com.vitorraphael.gestor_comercial.model.Funcionario;
import com.vitorraphael.gestor_comercial.model.ItemComanda;
import com.vitorraphael.gestor_comercial.model.Pagamento;
import com.vitorraphael.gestor_comercial.model.StatusComanda;
import com.vitorraphael.gestor_comercial.repository.ItemComandaRepository;
import com.vitorraphael.gestor_comercial.repository.PagamentoRepository;

@Service
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;
    private final ItemComandaRepository itemComandaRepository;
    private final ComandaService comandaService;
    private final FuncionarioService funcionarioService;

    public PagamentoService(PagamentoRepository pagamentoRepository, ItemComandaRepository itemComandaRepository,
            ComandaService comandaService, FuncionarioService funcionarioService) {
        this.pagamentoRepository = pagamentoRepository;
        this.itemComandaRepository = itemComandaRepository;
        this.comandaService = comandaService;
        this.funcionarioService = funcionarioService;
    }

    public PagamentoResponse registrar(Long comandaId, FormaPagamento formaPagamento, BigDecimal valorRecebido,
            String pin, Long funcionarioConsumidorId) {
        Comanda comanda = comandaService.buscarPorId(comandaId);
        if (comanda.getStatus() != StatusComanda.ABERTA) {
            throw new RegraDeNegocioException("Não é possível registrar pagamento em uma comanda que não está aberta.");
        }
        if (valorRecebido == null || valorRecebido.signum() <= 0) {
            throw new RegraDeNegocioException("O valor do pagamento deve ser maior que zero.");
        }

        Funcionario funcionarioConsumidor = null;
        if (formaPagamento == FormaPagamento.CONSUMO_INTERNO) {
            if (pin == null || pin.isBlank()) {
                throw new RegraDeNegocioException("O PIN do gerente é obrigatório para consumo interno.");
            }
            if (funcionarioConsumidorId == null) {
                throw new RegraDeNegocioException("O funcionário consumidor é obrigatório para consumo interno.");
            }
            funcionarioService.validarPinGerente(pin);
            funcionarioConsumidor = funcionarioService.buscarPorId(funcionarioConsumidorId);
        }

        BigDecimal totalConta = calcularTotalConta(comandaId);
        BigDecimal restante = totalConta.subtract(calcularTotalPago(comandaId));

        BigDecimal valorLancado;
        BigDecimal troco = null;
        if (formaPagamento == FormaPagamento.DINHEIRO) {
            if (valorRecebido.compareTo(restante) > 0) {
                valorLancado = restante;
                troco = valorRecebido.subtract(restante);
            } else {
                valorLancado = valorRecebido;
            }
        } else {
            if (valorRecebido.compareTo(restante) > 0) {
                throw new RegraDeNegocioException(
                        "O valor do pagamento não pode exceder o restante de " + restante + ".");
            }
            valorLancado = valorRecebido;
        }

        Pagamento pagamento = new Pagamento();
        pagamento.setComanda(comanda);
        pagamento.setFormaPagamento(formaPagamento);
        pagamento.setValor(valorLancado);
        pagamento.setTroco(troco);
        pagamento.setFuncionarioConsumidor(funcionarioConsumidor);
        pagamento.setValorQuitado(BigDecimal.ZERO);
        pagamento.setDataHora(LocalDateTime.now());
        pagamentoRepository.save(pagamento);

        BigDecimal totalPago = calcularTotalPago(comandaId);
        restante = totalConta.subtract(totalPago);

        boolean fechada = false;
        if (restante.signum() == 0) {
            comandaService.fechar(comandaId);
            fechada = true;
        }

        return new PagamentoResponse(comandaId, totalConta, totalPago, restante, troco, fechada);
    }

    public BigDecimal calcularTotalConta(Long comandaId) {
        List<ItemComanda> itens = itemComandaRepository.findByComandaId(comandaId);
        BigDecimal total = BigDecimal.ZERO;
        for (ItemComanda item : itens) {
            if (item.isCancelado()) {
                continue;
            }
            total = total.add(item.getPrecoUnitario().multiply(BigDecimal.valueOf(item.getQuantidade())));
        }
        return total;
    }

    public BigDecimal calcularTotalPago(Long comandaId) {
        List<Pagamento> pagamentos = pagamentoRepository.findByComandaId(comandaId);
        BigDecimal total = BigDecimal.ZERO;
        for (Pagamento pagamento : pagamentos) {
            total = total.add(pagamento.getValor());
        }
        return total;
    }
}
