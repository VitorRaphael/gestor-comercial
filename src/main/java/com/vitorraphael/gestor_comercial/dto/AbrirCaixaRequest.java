package com.vitorraphael.gestor_comercial.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record AbrirCaixaRequest(
        @NotNull(message = "O valor de abertura é obrigatório.") @PositiveOrZero(message = "O valor de abertura não pode ser negativo.") BigDecimal valorAbertura) {
}
