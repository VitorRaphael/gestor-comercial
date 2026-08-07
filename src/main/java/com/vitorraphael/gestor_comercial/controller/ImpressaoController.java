package com.vitorraphael.gestor_comercial.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vitorraphael.gestor_comercial.dto.PedidoImpressaoResponse;
import com.vitorraphael.gestor_comercial.service.ImpressaoService;

@RestController
@RequestMapping("/api/comandas")
public class ImpressaoController {

    private final ImpressaoService impressaoService;

    public ImpressaoController(ImpressaoService impressaoService) {
        this.impressaoService = impressaoService;
    }

    @PostMapping("/{comandaId}/imprimir")
    public ResponseEntity<List<PedidoImpressaoResponse>> imprimir(@PathVariable Long comandaId) {
        return ResponseEntity.ok(impressaoService.imprimirComanda(comandaId));
    }
}
