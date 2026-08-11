package com.vitorraphael.gestor_comercial.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record FichaTecnicaRequest(
        @NotNull(message = "O produto é obrigatório.") Long produtoId,
        @NotNull(message = "A matéria-prima é obrigatória.") Long materiaPrimaId,
        @NotNull(message = "A quantidade usada é obrigatória.") @Positive(message = "A quantidade usada deve ser positiva.") BigDecimal quantidadeUsada) {
}
