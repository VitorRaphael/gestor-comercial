package com.vitorraphael.gestor_comercial.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record MovimentoCaixaRequest(
        @NotBlank(message = "O tipo do movimento é obrigatório.") String tipo,
        @NotNull(message = "O valor do movimento é obrigatório.") @Positive(message = "O valor do movimento deve ser positivo.") BigDecimal valor,
        @NotBlank(message = "A descrição do movimento é obrigatória.") String descricao) {
}
