package com.vitorraphael.gestor_comercial.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vitorraphael.gestor_comercial.dto.FichaTecnicaRequest;
import com.vitorraphael.gestor_comercial.dto.FichaTecnicaResponse;
import com.vitorraphael.gestor_comercial.model.FichaTecnica;
import com.vitorraphael.gestor_comercial.security.ExigeGerente;
import com.vitorraphael.gestor_comercial.service.FichaTecnicaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/fichas-tecnicas")
public class FichaTecnicaController {

    private final FichaTecnicaService fichaTecnicaService;

    public FichaTecnicaController(FichaTecnicaService fichaTecnicaService) {
        this.fichaTecnicaService = fichaTecnicaService;
    }

    @ExigeGerente
    @PostMapping
    public ResponseEntity<FichaTecnicaResponse> associar(@Valid @RequestBody FichaTecnicaRequest request) {
        FichaTecnica fichaTecnica = fichaTecnicaService.associar(request.produtoId(), request.materiaPrimaId(),
                request.quantidadeUsada());
        return ResponseEntity.status(HttpStatus.CREATED).body(FichaTecnicaResponse.de(fichaTecnica));
    }

    @GetMapping
    public ResponseEntity<List<FichaTecnicaResponse>> listarPorProduto(@RequestParam Long produtoId) {
        List<FichaTecnicaResponse> fichaTecnica = fichaTecnicaService.listarPorProduto(produtoId).stream()
                .map(FichaTecnicaResponse::de)
                .toList();
        return ResponseEntity.ok(fichaTecnica);
    }

    @ExigeGerente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        fichaTecnicaService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
