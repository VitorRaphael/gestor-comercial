package com.vitorraphael.gestor_comercial.dto;

import com.vitorraphael.gestor_comercial.model.ItemComanda;

public record ItemImpressaoResponse(String produtoNome, Integer quantidade, String observacao) {

    public static ItemImpressaoResponse de(ItemComanda item) {
        return new ItemImpressaoResponse(item.getProduto().getNome(), item.getQuantidade(), item.getObservacao());
    }
}
