package com.vitorraphael.gestor_comercial.dto;

import com.vitorraphael.gestor_comercial.model.ComboItem;

public record ComboItemResponse(
        Long id,
        Long produtoComboId,
        String produtoComboNome,
        Long produtoComponenteId,
        String produtoComponenteNome,
        Integer quantidade) {

    public static ComboItemResponse de(ComboItem comboItem) {
        return new ComboItemResponse(
                comboItem.getId(),
                comboItem.getProdutoCombo().getId(),
                comboItem.getProdutoCombo().getNome(),
                comboItem.getProdutoComponente().getId(),
                comboItem.getProdutoComponente().getNome(),
                comboItem.getQuantidade());
    }
}
