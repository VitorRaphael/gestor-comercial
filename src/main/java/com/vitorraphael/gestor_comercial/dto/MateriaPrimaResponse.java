package com.vitorraphael.gestor_comercial.dto;

import java.math.BigDecimal;

import com.vitorraphael.gestor_comercial.model.MateriaPrima;
import com.vitorraphael.gestor_comercial.model.UnidadeMedida;

public record MateriaPrimaResponse(
        Long id,
        String nome,
        UnidadeMedida unidadeMedida,
        BigDecimal quantidadeEstoque,
        BigDecimal quantidadeMinima,
        boolean abaixoDoMinimo) {

    public static MateriaPrimaResponse de(MateriaPrima materiaPrima) {
        return new MateriaPrimaResponse(
                materiaPrima.getId(),
                materiaPrima.getNome(),
                materiaPrima.getUnidadeMedida(),
                materiaPrima.getQuantidadeEstoque(),
                materiaPrima.getQuantidadeMinima(),
                materiaPrima.isAbaixoDoMinimo());
    }
}
