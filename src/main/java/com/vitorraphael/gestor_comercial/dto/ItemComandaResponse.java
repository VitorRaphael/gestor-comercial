package com.vitorraphael.gestor_comercial.dto;

import java.math.BigDecimal;

import com.vitorraphael.gestor_comercial.model.ItemComanda;

public record ItemComandaResponse(
        Long id,
        Long comandaId,
        Long produtoId,
        String produtoNome,
        Integer quantidade,
        String observacao,
        BigDecimal precoUnitario) {

    public static ItemComandaResponse de(ItemComanda item) {
        return new ItemComandaResponse(
                item.getId(),
                item.getComanda().getId(),
                item.getProduto().getId(),
                item.getProduto().getNome(),
                item.getQuantidade(),
                item.getObservacao(),
                item.getPrecoUnitario());
    }
}
