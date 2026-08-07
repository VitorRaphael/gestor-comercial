package com.vitorraphael.gestor_comercial.dto;

import java.math.BigDecimal;

import com.vitorraphael.gestor_comercial.model.Produto;

public record ProdutoResponse(
        Long id,
        String nome,
        BigDecimal preco,
        Long categoriaId,
        String categoriaNome,
        boolean ativo) {

    public static ProdutoResponse de(Produto produto) {
        return new ProdutoResponse(
                produto.getId(),
                produto.getNome(),
                produto.getPreco(),
                produto.getCategoria().getId(),
                produto.getCategoria().getNome(),
                produto.isAtivo());
    }
}
