package com.vitorraphael.gestor_comercial.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vitorraphael.gestor_comercial.dto.AssociarImpressoraRequest;
import com.vitorraphael.gestor_comercial.dto.CategoriaRequest;
import com.vitorraphael.gestor_comercial.dto.CategoriaResponse;
import com.vitorraphael.gestor_comercial.model.Categoria;
import com.vitorraphael.gestor_comercial.security.ExigeGerente;
import com.vitorraphael.gestor_comercial.service.CategoriaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @ExigeGerente
    @PostMapping
    public ResponseEntity<CategoriaResponse> criar(@Valid @RequestBody CategoriaRequest request) {
        Categoria categoria = categoriaService.criar(request.nome());
        return ResponseEntity.status(HttpStatus.CREATED).body(CategoriaResponse.de(categoria));
    }

    @GetMapping
    public ResponseEntity<List<CategoriaResponse>> listar() {
        List<CategoriaResponse> categorias = categoriaService.listarTodas().stream()
                .map(CategoriaResponse::de)
                .toList();
        return ResponseEntity.ok(categorias);
    }

    @ExigeGerente
    @PatchMapping("/{id}/impressora")
    public ResponseEntity<CategoriaResponse> associarImpressora(@PathVariable Long id,
            @Valid @RequestBody AssociarImpressoraRequest request) {
        Categoria categoria = categoriaService.associarImpressora(id, request.impressoraId());
        return ResponseEntity.ok(CategoriaResponse.de(categoria));
    }
}
