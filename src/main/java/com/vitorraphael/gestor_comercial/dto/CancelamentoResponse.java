package com.vitorraphael.gestor_comercial.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CancelamentoResponse(
        LocalDateTime dataHora,
        String tipo,
        String descricao,
        BigDecimal valor,
        String motivo,
        String canceladoPorNome) {
}
