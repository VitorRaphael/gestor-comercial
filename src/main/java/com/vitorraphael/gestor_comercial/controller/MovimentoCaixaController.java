package com.vitorraphael.gestor_comercial.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vitorraphael.gestor_comercial.dto.MovimentoCaixaRequest;
import com.vitorraphael.gestor_comercial.dto.MovimentoCaixaResponse;
import com.vitorraphael.gestor_comercial.exception.RegraDeNegocioException;
import com.vitorraphael.gestor_comercial.model.MovimentoCaixa;
import com.vitorraphael.gestor_comercial.model.TipoMovimento;
import com.vitorraphael.gestor_comercial.service.MovimentoCaixaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/caixa")
public class MovimentoCaixaController {

    private final MovimentoCaixaService movimentoCaixaService;

    public MovimentoCaixaController(MovimentoCaixaService movimentoCaixaService) {
        this.movimentoCaixaService = movimentoCaixaService;
    }

    @PostMapping("/movimentos")
    public ResponseEntity<MovimentoCaixaResponse> registrar(@Valid @RequestBody MovimentoCaixaRequest request) {
        TipoMovimento tipo = converterTipo(request.tipo());
        MovimentoCaixa movimento = movimentoCaixaService.registrar(tipo, request.valor(), request.descricao());
        return ResponseEntity.status(HttpStatus.CREATED).body(MovimentoCaixaResponse.de(movimento));
    }

    @GetMapping("/{caixaId}/movimentos")
    public ResponseEntity<List<MovimentoCaixaResponse>> listarPorCaixa(@PathVariable Long caixaId) {
        List<MovimentoCaixaResponse> movimentos = movimentoCaixaService.listarPorCaixa(caixaId).stream()
                .map(MovimentoCaixaResponse::de)
                .toList();
        return ResponseEntity.ok(movimentos);
    }

    private TipoMovimento converterTipo(String tipo) {
        try {
            return TipoMovimento.valueOf(tipo.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RegraDeNegocioException("Tipo de movimento inválido: " + tipo);
        }
    }
}
