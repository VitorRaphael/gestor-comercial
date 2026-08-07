package com.vitorraphael.gestor_comercial.controller;

import java.math.BigDecimal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vitorraphael.gestor_comercial.dto.AbrirCaixaRequest;
import com.vitorraphael.gestor_comercial.dto.CaixaResponse;
import com.vitorraphael.gestor_comercial.dto.FecharCaixaRequest;
import com.vitorraphael.gestor_comercial.model.Caixa;
import com.vitorraphael.gestor_comercial.security.ExigeGerente;
import com.vitorraphael.gestor_comercial.service.CaixaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/caixa")
public class CaixaController {

    private final CaixaService caixaService;

    public CaixaController(CaixaService caixaService) {
        this.caixaService = caixaService;
    }

    @ExigeGerente
    @PostMapping("/abrir")
    public ResponseEntity<CaixaResponse> abrir(@Valid @RequestBody AbrirCaixaRequest request) {
        Caixa caixa = caixaService.abrir(request.valorAbertura());
        return ResponseEntity.status(HttpStatus.CREATED).body(CaixaResponse.de(caixa));
    }

    @ExigeGerente
    @PostMapping("/{id}/fechar")
    public ResponseEntity<CaixaResponse> fechar(@PathVariable Long id, @Valid @RequestBody FecharCaixaRequest request) {
        Caixa caixa = caixaService.fechar(id, request.valorFechamento(), request.observacao());
        return ResponseEntity.ok(CaixaResponse.de(caixa));
    }

    @GetMapping("/aberto")
    public ResponseEntity<CaixaResponse> buscarAberto() {
        Caixa caixa = caixaService.buscarAberto();
        return ResponseEntity.ok(CaixaResponse.de(caixa));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CaixaResponse> buscarPorId(@PathVariable Long id) {
        Caixa caixa = caixaService.buscarPorId(id);
        return ResponseEntity.ok(CaixaResponse.de(caixa));
    }

    @GetMapping("/{id}/saldo")
    public ResponseEntity<BigDecimal> saldo(@PathVariable Long id) {
        return ResponseEntity.ok(caixaService.calcularSaldoEsperado(id));
    }
}
