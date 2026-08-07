package com.vitorraphael.gestor_comercial.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record FecharCaixaRequest(
        @NotNull(message = "O valor de fechamento é obrigatório.") @PositiveOrZero(message = "O valor de fechamento não pode ser negativo.") BigDecimal valorFechamento,
        String observacao) {
}
