package com.vitorraphael.gestor_comercial.dto;

import jakarta.validation.constraints.NotBlank;

public record ImpressoraRequest(
        @NotBlank(message = "O nome da impressora é obrigatório.") String nome) {
}
