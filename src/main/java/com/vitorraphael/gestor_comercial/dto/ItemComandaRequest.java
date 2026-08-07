package com.vitorraphael.gestor_comercial.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ItemComandaRequest(
        @NotNull(message = "O produto é obrigatório.") Long produtoId,
        @NotNull(message = "A quantidade é obrigatória.") @Positive(message = "A quantidade deve ser positiva.") Integer quantidade,
        String observacao) {
}
