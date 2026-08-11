package com.vitorraphael.gestor_comercial.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record MovimentoEstoqueRequest(
        @NotNull(message = "A quantidade é obrigatória.") @Positive(message = "A quantidade deve ser positiva.") BigDecimal quantidade) {
}
