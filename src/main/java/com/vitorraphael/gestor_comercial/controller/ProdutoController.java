package com.vitorraphael.gestor_comercial.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vitorraphael.gestor_comercial.dto.ProdutoRequest;
import com.vitorraphael.gestor_comercial.dto.ProdutoResponse;
import com.vitorraphael.gestor_comercial.model.Produto;
import com.vitorraphael.gestor_comercial.repository.ComboItemRepository;
import com.vitorraphael.gestor_comercial.security.ExigeGerente;
import com.vitorraphael.gestor_comercial.service.ProdutoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;
    private final ComboItemRepository comboItemRepository;

    public ProdutoController(ProdutoService produtoService, ComboItemRepository comboItemRepository) {
        this.produtoService = produtoService;
        this.comboItemRepository = comboItemRepository;
    }

    @ExigeGerente
    @PostMapping
    public ResponseEntity<ProdutoResponse> criar(@Valid @RequestBody ProdutoRequest request) {
        Produto produto = produtoService.criar(request.nome(), request.preco(), request.custo(), request.categoriaId());
        return ResponseEntity.status(HttpStatus.CREATED).body(ProdutoResponse.de(produto));
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponse>> listarAtivos() {
        List<ProdutoResponse> produtos = produtoService.listarAtivos().stream()
                .map(produto -> ProdutoResponse.de(produto, comboItemRepository.existsByProdutoComboId(produto.getId())))
                .toList();
        return ResponseEntity.ok(produtos);
    }

    /**
     * Usado só pela tela de gerenciamento do cardápio, que precisa mostrar
     * também os produtos desativados (com feedback visual). As telas de
     * pedido (atendente) usam {@link #listarAtivos()}, que já filtra.
     */
    @ExigeGerente
    @GetMapping("/todos")
    public ResponseEntity<List<ProdutoResponse>> listarTodos() {
        List<ProdutoResponse> produtos = produtoService.listarTodos().stream()
                .map(produto -> ProdutoResponse.de(produto, comboItemRepository.existsByProdutoComboId(produto.getId())))
                .toList();
        return ResponseEntity.ok(produtos);
    }

    @ExigeGerente
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponse> atualizar(@PathVariable Long id, @Valid @RequestBody ProdutoRequest request) {
        Produto produto = produtoService.atualizar(id, request.nome(), request.preco(), request.custo(), request.categoriaId());
        return ResponseEntity.ok(ProdutoResponse.de(produto, comboItemRepository.existsByProdutoComboId(produto.getId())));
    }

    /**
     * Não deleta o produto de fato — apenas o marca como inativo, preservando
     * o histórico de comandas que já o referenciam. Por isso PATCH, não DELETE.
     */
    @ExigeGerente
    @PatchMapping("/{id}/desativar")
    public ResponseEntity<ProdutoResponse> desativar(@PathVariable Long id) {
        Produto produto = produtoService.desativar(id);
        return ResponseEntity.ok(ProdutoResponse.de(produto, comboItemRepository.existsByProdutoComboId(produto.getId())));
    }

    @ExigeGerente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        produtoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
