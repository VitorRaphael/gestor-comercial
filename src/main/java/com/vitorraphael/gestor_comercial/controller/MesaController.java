package com.vitorraphael.gestor_comercial.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vitorraphael.gestor_comercial.dto.MesaRequest;
import com.vitorraphael.gestor_comercial.dto.MesaResponse;
import com.vitorraphael.gestor_comercial.model.Mesa;
import com.vitorraphael.gestor_comercial.service.MesaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/mesas")
public class MesaController {

    private final MesaService mesaService;

    public MesaController(MesaService mesaService) {
        this.mesaService = mesaService;
    }

    @PostMapping
    public ResponseEntity<MesaResponse> criar(@Valid @RequestBody MesaRequest request) {
        Mesa mesa = mesaService.criar(request.numero());
        return ResponseEntity.status(HttpStatus.CREATED).body(MesaResponse.de(mesa));
    }

    @GetMapping
    public ResponseEntity<List<MesaResponse>> listar() {
        List<MesaResponse> mesas = mesaService.listarTodas().stream()
                .map(MesaResponse::de)
                .toList();
        return ResponseEntity.ok(mesas);
    }
}
