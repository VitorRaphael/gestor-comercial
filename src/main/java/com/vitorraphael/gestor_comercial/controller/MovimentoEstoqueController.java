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

import com.vitorraphael.gestor_comercial.dto.MovimentoEstoqueRequest;
import com.vitorraphael.gestor_comercial.dto.MovimentoEstoqueResponse;
import com.vitorraphael.gestor_comercial.model.MovimentoEstoque;
import com.vitorraphael.gestor_comercial.security.ExigeGerente;
import com.vitorraphael.gestor_comercial.service.MovimentoEstoqueService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/materias-primas/{materiaPrimaId}/movimentos")
public class MovimentoEstoqueController {

    private final MovimentoEstoqueService movimentoEstoqueService;

    public MovimentoEstoqueController(MovimentoEstoqueService movimentoEstoqueService) {
        this.movimentoEstoqueService = movimentoEstoqueService;
    }

    @ExigeGerente
    @PostMapping("/compra")
    public ResponseEntity<MovimentoEstoqueResponse> registrarCompra(@PathVariable Long materiaPrimaId,
            @Valid @RequestBody MovimentoEstoqueRequest request) {
        MovimentoEstoque movimento = movimentoEstoqueService.registrarCompra(materiaPrimaId, request.quantidade());
        return ResponseEntity.status(HttpStatus.CREATED).body(MovimentoEstoqueResponse.de(movimento));
    }

    @GetMapping
    public ResponseEntity<List<MovimentoEstoqueResponse>> listar(@PathVariable Long materiaPrimaId) {
        List<MovimentoEstoqueResponse> movimentos = movimentoEstoqueService.listarPorMateriaPrima(materiaPrimaId)
                .stream()
                .map(MovimentoEstoqueResponse::de)
                .toList();
        return ResponseEntity.ok(movimentos);
    }
}
