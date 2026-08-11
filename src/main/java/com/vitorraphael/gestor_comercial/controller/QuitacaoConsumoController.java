package com.vitorraphael.gestor_comercial.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vitorraphael.gestor_comercial.dto.ConsumosFuncionarioResponse;
import com.vitorraphael.gestor_comercial.dto.QuitarConsumoRequest;
import com.vitorraphael.gestor_comercial.dto.SaldoDevedorResponse;
import com.vitorraphael.gestor_comercial.security.ExigeGerente;
import com.vitorraphael.gestor_comercial.service.QuitacaoConsumoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/funcionarios")
public class QuitacaoConsumoController {

    private final QuitacaoConsumoService quitacaoConsumoService;

    public QuitacaoConsumoController(QuitacaoConsumoService quitacaoConsumoService) {
        this.quitacaoConsumoService = quitacaoConsumoService;
    }

    @ExigeGerente
    @GetMapping("/saldo-devedor")
    public ResponseEntity<List<SaldoDevedorResponse>> saldoDevedor() {
        return ResponseEntity.ok(quitacaoConsumoService.listarFuncionariosComSaldoDevedor());
    }

    @ExigeGerente
    @GetMapping("/{id}/consumos")
    public ResponseEntity<ConsumosFuncionarioResponse> consumos(@PathVariable Long id) {
        return ResponseEntity.ok(quitacaoConsumoService.listarConsumos(id));
    }

    @ExigeGerente
    @PostMapping("/{id}/quitar")
    public ResponseEntity<Map<String, BigDecimal>> quitar(@PathVariable Long id,
            @Valid @RequestBody QuitarConsumoRequest request) {
        BigDecimal saldoDevedor = quitacaoConsumoService.quitar(id, request.valor(), request.pin());
        return ResponseEntity.ok(Map.of("saldoDevedor", saldoDevedor));
    }
}
