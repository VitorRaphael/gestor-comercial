package com.vitorraphael.gestor_comercial.dto;

import jakarta.validation.constraints.NotBlank;

public record ValidarPinGerenteRequest(@NotBlank(message = "O PIN é obrigatório.") String pin) {
}
