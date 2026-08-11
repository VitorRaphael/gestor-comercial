package com.vitorraphael.gestor_comercial.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record QuitacaoRegistroResponse(
        Long id,
        LocalDateTime dataHora,
        BigDecimal valor,
        String autorizadoPor) {
}
