package com.vitorraphael.gestor_comercial.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vitorraphael.gestor_comercial.dto.PagamentoRequest;
import com.vitorraphael.gestor_comercial.dto.PagamentoResponse;
import com.vitorraphael.gestor_comercial.exception.RegraDeNegocioException;
import com.vitorraphael.gestor_comercial.model.FormaPagamento;
import com.vitorraphael.gestor_comercial.service.PagamentoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/comandas/{comandaId}/pagamentos")
public class PagamentoController {

    private final PagamentoService pagamentoService;

    public PagamentoController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @PostMapping
    public ResponseEntity<PagamentoResponse> registrar(@PathVariable Long comandaId,
            @Valid @RequestBody PagamentoRequest request) {
        FormaPagamento formaPagamento = converterFormaPagamento(request.formaPagamento());
        PagamentoResponse resposta = pagamentoService.registrar(comandaId, formaPagamento, request.valor(),
                request.pin(), request.funcionarioConsumidorId());
        return ResponseEntity.ok(resposta);
    }

    private FormaPagamento converterFormaPagamento(String formaPagamento) {
        try {
            return FormaPagamento.valueOf(formaPagamento.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RegraDeNegocioException("Forma de pagamento inválida: " + formaPagamento);
        }
    }
}
