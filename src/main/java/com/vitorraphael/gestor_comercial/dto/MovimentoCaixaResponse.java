package com.vitorraphael.gestor_comercial.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.vitorraphael.gestor_comercial.model.MovimentoCaixa;

public record MovimentoCaixaResponse(
        Long id,
        Long caixaId,
        String tipo,
        BigDecimal valor,
        String descricao,
        LocalDateTime dataHora) {

    public static MovimentoCaixaResponse de(MovimentoCaixa movimento) {
        return new MovimentoCaixaResponse(
                movimento.getId(),
                movimento.getCaixa().getId(),
                movimento.getTipo().name(),
                movimento.getValor(),
                movimento.getDescricao(),
                movimento.getDataHora());
    }
}
