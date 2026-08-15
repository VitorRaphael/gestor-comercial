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

import com.vitorraphael.gestor_comercial.dto.ComboItemRequest;
import com.vitorraphael.gestor_comercial.dto.ComboItemResponse;
import com.vitorraphael.gestor_comercial.model.ComboItem;
import com.vitorraphael.gestor_comercial.security.ExigeGerente;
import com.vitorraphael.gestor_comercial.service.ComboItemService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/itens-combo")
public class ComboItemController {

    private final ComboItemService comboItemService;

    public ComboItemController(ComboItemService comboItemService) {
        this.comboItemService = comboItemService;
    }

    @ExigeGerente
    @PostMapping
    public ResponseEntity<ComboItemResponse> associar(@Valid @RequestBody ComboItemRequest request) {
        ComboItem comboItem = comboItemService.associar(request.produtoComboId(), request.produtoComponenteId(),
                request.quantidade());
        return ResponseEntity.status(HttpStatus.CREATED).body(ComboItemResponse.de(comboItem));
    }

    @GetMapping
    public ResponseEntity<List<ComboItemResponse>> listarPorCombo(@RequestParam Long produtoComboId) {
        List<ComboItemResponse> itens = comboItemService.listarPorCombo(produtoComboId).stream()
                .map(ComboItemResponse::de)
                .toList();
        return ResponseEntity.ok(itens);
    }

    @ExigeGerente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        comboItemService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
