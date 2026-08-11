package com.vitorraphael.gestor_comercial.dto;

import java.math.BigDecimal;

import com.vitorraphael.gestor_comercial.model.UnidadeMedida;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record MateriaPrimaRequest(
        @NotBlank(message = "O nome é obrigatório.") String nome,
        @NotNull(message = "A unidade de medida é obrigatória.") UnidadeMedida unidadeMedida,
        @PositiveOrZero(message = "A quantidade mínima não pode ser negativa.") BigDecimal quantidadeMinima) {
}
