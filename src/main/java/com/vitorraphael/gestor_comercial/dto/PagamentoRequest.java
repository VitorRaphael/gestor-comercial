package com.vitorraphael.gestor_comercial.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PagamentoRequest(
        @NotBlank(message = "A forma de pagamento é obrigatória.") String formaPagamento,
        @NotNull(message = "O valor do pagamento é obrigatório.") @Positive(message = "O valor do pagamento deve ser positivo.") BigDecimal valor,
        String pin,
        Long funcionarioConsumidorId) {
}
