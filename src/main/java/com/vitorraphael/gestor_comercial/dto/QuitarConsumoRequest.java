package com.vitorraphael.gestor_comercial.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record QuitarConsumoRequest(
        @NotNull(message = "O valor a quitar é obrigatório.") @Positive(message = "O valor a quitar deve ser positivo.") BigDecimal valor,
        @NotBlank(message = "O PIN do gerente é obrigatório.") String pin) {
}
