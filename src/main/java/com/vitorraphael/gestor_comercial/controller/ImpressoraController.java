package com.vitorraphael.gestor_comercial.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vitorraphael.gestor_comercial.dto.ImpressoraRequest;
import com.vitorraphael.gestor_comercial.dto.ImpressoraResponse;
import com.vitorraphael.gestor_comercial.model.Impressora;
import com.vitorraphael.gestor_comercial.security.ExigeGerente;
import com.vitorraphael.gestor_comercial.service.ImpressoraService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/impressoras")
public class ImpressoraController {

    private final ImpressoraService impressoraService;

    public ImpressoraController(ImpressoraService impressoraService) {
        this.impressoraService = impressoraService;
    }

    @ExigeGerente
    @PostMapping
    public ResponseEntity<ImpressoraResponse> criar(@Valid @RequestBody ImpressoraRequest request) {
        Impressora impressora = impressoraService.criar(request.nome());
        return ResponseEntity.status(HttpStatus.CREATED).body(ImpressoraResponse.de(impressora));
    }

    @GetMapping
    public ResponseEntity<List<ImpressoraResponse>> listar() {
        List<ImpressoraResponse> impressoras = impressoraService.listarTodas().stream()
                .map(ImpressoraResponse::de)
                .toList();
        return ResponseEntity.ok(impressoras);
    }
}
