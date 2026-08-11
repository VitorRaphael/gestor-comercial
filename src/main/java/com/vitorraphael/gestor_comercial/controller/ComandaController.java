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

import com.vitorraphael.gestor_comercial.dto.CancelamentoRequest;
import com.vitorraphael.gestor_comercial.dto.ComandaResponse;
import com.vitorraphael.gestor_comercial.model.Comanda;
import com.vitorraphael.gestor_comercial.service.ComandaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class ComandaController {

    private final ComandaService comandaService;

    public ComandaController(ComandaService comandaService) {
        this.comandaService = comandaService;
    }

    @PostMapping("/mesas/{mesaId}/comandas")
    public ResponseEntity<ComandaResponse> abrir(@PathVariable Long mesaId) {
        Comanda comanda = comandaService.abrir(mesaId);
        return ResponseEntity.status(HttpStatus.CREATED).body(ComandaResponse.de(comanda));
    }

    @PostMapping("/comandas/{id}/fechar")
    public ResponseEntity<ComandaResponse> fechar(@PathVariable Long id) {
        Comanda comanda = comandaService.fechar(id);
        return ResponseEntity.ok(ComandaResponse.de(comanda));
    }

    @PostMapping("/comandas/{id}/cancelar")
    public ResponseEntity<ComandaResponse> cancelar(@PathVariable Long id, @Valid @RequestBody CancelamentoRequest request) {
        Comanda comanda = comandaService.cancelar(id, request.motivo(), request.pin());
        return ResponseEntity.ok(ComandaResponse.de(comanda));
    }

    @GetMapping("/comandas/abertas")
    public ResponseEntity<List<ComandaResponse>> listarAbertas() {
        List<ComandaResponse> comandas = comandaService.listarAbertas().stream()
                .map(ComandaResponse::de)
                .toList();
        return ResponseEntity.ok(comandas);
    }

    @GetMapping("/comandas/{id}")
    public ResponseEntity<ComandaResponse> buscarPorId(@PathVariable Long id) {
        Comanda comanda = comandaService.buscarPorId(id);
        return ResponseEntity.ok(ComandaResponse.de(comanda));
    }
}
