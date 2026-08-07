package com.vitorraphael.gestor_comercial.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record MesaRequest(
        @NotNull(message = "O número da mesa é obrigatório.") @Positive(message = "O número da mesa deve ser positivo.") Integer numero) {
}
