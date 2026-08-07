package com.vitorraphael.gestor_comercial.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(@NotBlank(message = "O PIN é obrigatório.") String pin) {
}
