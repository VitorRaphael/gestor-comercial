package com.vitorraphael.gestor_comercial.dto;

import java.math.BigDecimal;

import com.vitorraphael.gestor_comercial.model.Produto;

public record ProdutoResponse(
        Long id,
        String nome,
        BigDecimal preco,
        BigDecimal custo,
        BigDecimal margem,
        Long categoriaId,
        String categoriaNome,
        boolean ativo,
        boolean temItensCombo,
        String descricao,
        String fotoUrl) {

    public static ProdutoResponse de(Produto produto) {
        return de(produto, false);
    }

    public static ProdutoResponse de(Produto produto, boolean temItensCombo) {
        BigDecimal margem = produto.getCusto() != null ? produto.getPreco().subtract(produto.getCusto()) : null;
        return new ProdutoResponse(
                produto.getId(),
                produto.getNome(),
                produto.getPreco(),
                produto.getCusto(),
                margem,
                produto.getCategoria().getId(),
                produto.getCategoria().getNome(),
                produto.isAtivo(),
                temItensCombo,
                produto.getDescricao(),
                produto.getFotoUrl());
    }
}
