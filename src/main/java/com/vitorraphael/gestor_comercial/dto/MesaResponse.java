package com.vitorraphael.gestor_comercial.dto;

import com.vitorraphael.gestor_comercial.model.Mesa;

public record MesaResponse(Long id, Integer numero, String status) {

    public static MesaResponse de(Mesa mesa) {
        return new MesaResponse(mesa.getId(), mesa.getNumero(), mesa.getStatus().name());
    }
}
