package com.vitorraphael.gestor_comercial.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vitorraphael.gestor_comercial.dto.MesaResponse;
import com.vitorraphael.gestor_comercial.service.MesaService;

/**
 * As 60 mesas do food truck são uma ferramenta fixa do ambiente
 * (criadas pelo {@code MesaSeeder} na inicialização) — não há
 * cadastro/exclusão de mesa pela API.
 */
@RestController
@RequestMapping("/api/mesas")
public class MesaController {

    private final MesaService mesaService;

    public MesaController(MesaService mesaService) {
        this.mesaService = mesaService;
    }

    @GetMapping
    public ResponseEntity<List<MesaResponse>> listar() {
        List<MesaResponse> mesas = mesaService.listarTodas().stream()
                .map(MesaResponse::de)
                .toList();
        return ResponseEntity.ok(mesas);
    }
}
