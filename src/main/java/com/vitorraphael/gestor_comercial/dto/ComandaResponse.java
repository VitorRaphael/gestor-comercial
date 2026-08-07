package com.vitorraphael.gestor_comercial.dto;

import java.time.LocalDateTime;

import com.vitorraphael.gestor_comercial.model.Comanda;

public record ComandaResponse(
        Long id,
        Long mesaId,
        Integer mesaNumero,
        String status,
        LocalDateTime dataAbertura,
        LocalDateTime dataFechamento) {

    public static ComandaResponse de(Comanda comanda) {
        return new ComandaResponse(
                comanda.getId(),
                comanda.getMesa().getId(),
                comanda.getMesa().getNumero(),
                comanda.getStatus().name(),
                comanda.getDataAbertura(),
                comanda.getDataFechamento());
    }
}
