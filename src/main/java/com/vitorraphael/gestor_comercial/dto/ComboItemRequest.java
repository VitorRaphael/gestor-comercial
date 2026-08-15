package com.vitorraphael.gestor_comercial.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ComboItemRequest(
        @NotNull(message = "O produto combo é obrigatório.") Long produtoComboId,
        @NotNull(message = "O produto componente é obrigatório.") Long produtoComponenteId,
        @NotNull(message = "A quantidade é obrigatória.") @Positive(message = "A quantidade deve ser positiva.") Integer quantidade) {
}
