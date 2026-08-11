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
import org.springframework.web.bind.annotation.RestController;

import com.vitorraphael.gestor_comercial.dto.CancelamentoRequest;
import com.vitorraphael.gestor_comercial.dto.ItemComandaRequest;
import com.vitorraphael.gestor_comercial.dto.ItemComandaResponse;
import com.vitorraphael.gestor_comercial.model.ItemComanda;
import com.vitorraphael.gestor_comercial.service.ItemComandaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/comandas/{comandaId}/itens")
public class ItemComandaController {

    private final ItemComandaService itemComandaService;

    public ItemComandaController(ItemComandaService itemComandaService) {
        this.itemComandaService = itemComandaService;
    }

    @PostMapping
    public ResponseEntity<ItemComandaResponse> adicionar(@PathVariable Long comandaId,
            @Valid @RequestBody ItemComandaRequest request) {
        ItemComanda item = itemComandaService.adicionarItem(
                comandaId, request.produtoId(), request.quantidade(), request.observacao());
        return ResponseEntity.status(HttpStatus.CREATED).body(ItemComandaResponse.de(item));
    }

    @GetMapping
    public ResponseEntity<List<ItemComandaResponse>> listar(@PathVariable Long comandaId) {
        List<ItemComandaResponse> itens = itemComandaService.listarPorComanda(comandaId).stream()
                .map(ItemComandaResponse::de)
                .toList();
        return ResponseEntity.ok(itens);
    }

    // O service remove por id de item apenas (não valida comandaId contra o
    // item), mas mantemos o path aninhado por ser RESTful/idiomático.
    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> remover(@PathVariable Long comandaId, @PathVariable Long itemId) {
        itemComandaService.removerItem(itemId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{itemId}/cancelar")
    public ResponseEntity<ItemComandaResponse> cancelar(@PathVariable Long comandaId, @PathVariable Long itemId,
            @Valid @RequestBody CancelamentoRequest request) {
        ItemComanda item = itemComandaService.cancelarItem(itemId, request.motivo(), request.pin());
        return ResponseEntity.ok(ItemComandaResponse.de(item));
    }
}
