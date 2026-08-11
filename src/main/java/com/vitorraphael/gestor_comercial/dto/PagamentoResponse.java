package com.vitorraphael.gestor_comercial.dto;

import java.math.BigDecimal;

public record PagamentoResponse(
        Long comandaId,
        BigDecimal totalConta,
        BigDecimal totalPago,
        BigDecimal restante,
        BigDecimal troco,
        boolean comandaFechada) {
}
