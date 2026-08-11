package com.vitorraphael.gestor_comercial.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vitorraphael.gestor_comercial.dto.MateriaPrimaRequest;
import com.vitorraphael.gestor_comercial.dto.MateriaPrimaResponse;
import com.vitorraphael.gestor_comercial.model.MateriaPrima;
import com.vitorraphael.gestor_comercial.security.ExigeGerente;
import com.vitorraphael.gestor_comercial.service.MateriaPrimaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/materias-primas")
public class MateriaPrimaController {

    private final MateriaPrimaService materiaPrimaService;

    public MateriaPrimaController(MateriaPrimaService materiaPrimaService) {
        this.materiaPrimaService = materiaPrimaService;
    }

    @ExigeGerente
    @PostMapping
    public ResponseEntity<MateriaPrimaResponse> criar(@Valid @RequestBody MateriaPrimaRequest request) {
        MateriaPrima materiaPrima = materiaPrimaService.criar(request.nome(), request.unidadeMedida(),
                request.quantidadeMinima());
        return ResponseEntity.status(HttpStatus.CREATED).body(MateriaPrimaResponse.de(materiaPrima));
    }

    @GetMapping
    public ResponseEntity<List<MateriaPrimaResponse>> listar() {
        List<MateriaPrimaResponse> materiasPrimas = materiaPrimaService.listarTodas().stream()
                .map(MateriaPrimaResponse::de)
                .toList();
        return ResponseEntity.ok(materiasPrimas);
    }

    @ExigeGerente
    @PutMapping("/{id}")
    public ResponseEntity<MateriaPrimaResponse> atualizar(@PathVariable Long id,
            @Valid @RequestBody MateriaPrimaRequest request) {
        MateriaPrima materiaPrima = materiaPrimaService.atualizar(id, request.nome(), request.unidadeMedida(),
                request.quantidadeMinima());
        return ResponseEntity.ok(MateriaPrimaResponse.de(materiaPrima));
    }
}
