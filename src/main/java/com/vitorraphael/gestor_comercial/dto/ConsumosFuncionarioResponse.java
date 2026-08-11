package com.vitorraphael.gestor_comercial.dto;

import java.math.BigDecimal;
import java.util.List;

public record ConsumosFuncionarioResponse(
        Long funcionarioId,
        String nome,
        BigDecimal saldoDevedor,
        List<ConsumoRegistroResponse> consumos,
        List<QuitacaoRegistroResponse> quitacoes) {
}
