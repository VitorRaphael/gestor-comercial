package com.vitorraphael.gestor_comercial.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.vitorraphael.gestor_comercial.model.MovimentoEstoque;
import com.vitorraphael.gestor_comercial.model.TipoMovimentoEstoque;

public record MovimentoEstoqueResponse(
        Long id,
        Long materiaPrimaId,
        String materiaPrimaNome,
        TipoMovimentoEstoque tipo,
        BigDecimal quantidade,
        LocalDateTime data,
        Long comandaId) {

    public static MovimentoEstoqueResponse de(MovimentoEstoque movimento) {
        return new MovimentoEstoqueResponse(
                movimento.getId(),
                movimento.getMateriaPrima().getId(),
                movimento.getMateriaPrima().getNome(),
                movimento.getTipo(),
                movimento.getQuantidade(),
                movimento.getData(),
                movimento.getComanda() != null ? movimento.getComanda().getId() : null);
    }
}
