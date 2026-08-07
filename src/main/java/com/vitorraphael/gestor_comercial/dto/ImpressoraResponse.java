package com.vitorraphael.gestor_comercial.dto;

import com.vitorraphael.gestor_comercial.model.Impressora;

public record ImpressoraResponse(Long id, String nome) {

    public static ImpressoraResponse de(Impressora impressora) {
        return new ImpressoraResponse(impressora.getId(), impressora.getNome());
    }
}
