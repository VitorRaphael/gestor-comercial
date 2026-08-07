package com.vitorraphael.gestor_comercial.dto;

import jakarta.validation.constraints.NotNull;

public record AssociarImpressoraRequest(
        @NotNull(message = "A impressora é obrigatória.") Long impressoraId) {
}
