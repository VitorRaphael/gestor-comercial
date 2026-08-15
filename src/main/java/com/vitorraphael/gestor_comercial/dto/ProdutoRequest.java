package com.vitorraphael.gestor_comercial.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record ProdutoRequest(
        @NotBlank(message = "O nome do produto é obrigatório.") String nome,
        @NotNull(message = "O preço é obrigatório.") @Positive(message = "O preço deve ser positivo.") BigDecimal preco,
        @PositiveOrZero(message = "O custo não pode ser negativo.") BigDecimal custo,
        @NotNull(message = "A categoria é obrigatória.") Long categoriaId,
        String descricao) {
}
