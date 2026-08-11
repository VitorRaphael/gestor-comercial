package com.vitorraphael.gestor_comercial.dto;

import java.time.LocalDateTime;

import com.vitorraphael.gestor_comercial.model.Comanda;

public record ComandaResponse(
        Long id,
        Long mesaId,
        Integer mesaNumero,
        String status,
        LocalDateTime dataAbertura,
        LocalDateTime dataFechamento,
        LocalDateTime dataCancelamento,
        String motivoCancelamento,
        String canceladoPorNome) {

    public static ComandaResponse de(Comanda comanda) {
        return new ComandaResponse(
                comanda.getId(),
                comanda.getMesa().getId(),
                comanda.getMesa().getNumero(),
                comanda.getStatus().name(),
                comanda.getDataAbertura(),
                comanda.getDataFechamento(),
                comanda.getDataCancelamento(),
                comanda.getMotivoCancelamento(),
                comanda.getCanceladoPor() != null ? comanda.getCanceladoPor().getNome() : null);
    }
}
