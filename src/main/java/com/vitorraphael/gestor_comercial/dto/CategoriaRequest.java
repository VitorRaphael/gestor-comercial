package com.vitorraphael.gestor_comercial.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoriaRequest(
        @NotBlank(message = "O nome da categoria é obrigatório.") String nome) {
}
