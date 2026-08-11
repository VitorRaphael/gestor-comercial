package com.vitorraphael.gestor_comercial.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ConsumoRegistroResponse(
        Long pagamentoId,
        LocalDateTime dataHora,
        Long comandaId,
        String mesa,
        BigDecimal valor,
        BigDecimal valorQuitado) {
}
