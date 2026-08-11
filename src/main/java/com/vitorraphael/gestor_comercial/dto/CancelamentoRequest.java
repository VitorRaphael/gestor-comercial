package com.vitorraphael.gestor_comercial.dto;

import jakarta.validation.constraints.NotBlank;

public record CancelamentoRequest(
        @NotBlank(message = "O motivo é obrigatório.") String motivo,
        @NotBlank(message = "O PIN é obrigatório.") String pin) {
}
