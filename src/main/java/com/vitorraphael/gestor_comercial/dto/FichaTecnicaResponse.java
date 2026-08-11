package com.vitorraphael.gestor_comercial.dto;

import java.math.BigDecimal;

import com.vitorraphael.gestor_comercial.model.FichaTecnica;

public record FichaTecnicaResponse(
        Long id,
        Long produtoId,
        String produtoNome,
        Long materiaPrimaId,
        String materiaPrimaNome,
        BigDecimal quantidadeUsada) {

    public static FichaTecnicaResponse de(FichaTecnica fichaTecnica) {
        return new FichaTecnicaResponse(
                fichaTecnica.getId(),
                fichaTecnica.getProduto().getId(),
                fichaTecnica.getProduto().getNome(),
                fichaTecnica.getMateriaPrima().getId(),
                fichaTecnica.getMateriaPrima().getNome(),
                fichaTecnica.getQuantidadeUsada());
    }
}
