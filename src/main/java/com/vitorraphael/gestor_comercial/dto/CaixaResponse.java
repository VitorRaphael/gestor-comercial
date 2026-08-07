package com.vitorraphael.gestor_comercial.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.vitorraphael.gestor_comercial.model.Caixa;

public record CaixaResponse(
        Long id,
        String status,
        LocalDateTime dataAbertura,
        LocalDateTime dataFechamento,
        BigDecimal valorAbertura,
        BigDecimal valorFechamento,
        String observacaoFechamento) {

    public static CaixaResponse de(Caixa caixa) {
        return new CaixaResponse(
                caixa.getId(),
                caixa.getStatus().name(),
                caixa.getDataAbertura(),
                caixa.getDataFechamento(),
                caixa.getValorAbertura(),
                caixa.getValorFechamento(),
                caixa.getObservacaoFechamento());
    }
}
