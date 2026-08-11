package com.vitorraphael.gestor_comercial.dto;

import java.math.BigDecimal;

public record SaldoDevedorResponse(Long funcionarioId, String nome, BigDecimal saldoDevedor) {
}
