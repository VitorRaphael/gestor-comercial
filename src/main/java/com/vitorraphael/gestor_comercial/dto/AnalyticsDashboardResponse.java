package com.vitorraphael.gestor_comercial.dto;

import java.math.BigDecimal;
import java.util.List;

public record AnalyticsDashboardResponse(
        BigDecimal faturamentoTotal,
        BigDecimal ticketMedio,
        long numeroComandas,
        Double cmvPercentual,
        List<PontoFaturamento> faturamentoPorDia,
        List<ItemCurvaAbc> curvaAbc,
        List<PontoMapaCalor> mapaCalor,
        List<ItemMixCategoria> mixPorCategoria,
        List<String> alertas) {

    public record PontoFaturamento(String data, BigDecimal valor) {
    }

    public record ItemCurvaAbc(String produtoNome, BigDecimal receita) {
    }

    public record PontoMapaCalor(int diaSemana, int hora, long quantidade) {
    }

    public record ItemMixCategoria(String categoriaNome, BigDecimal receita) {
    }
}
