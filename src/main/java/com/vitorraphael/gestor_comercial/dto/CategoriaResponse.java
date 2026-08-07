package com.vitorraphael.gestor_comercial.dto;

import com.vitorraphael.gestor_comercial.model.Categoria;

public record CategoriaResponse(Long id, String nome, Long impressoraId, String impressoraNome) {

    public static CategoriaResponse de(Categoria categoria) {
        Long impressoraId = categoria.getImpressora() != null ? categoria.getImpressora().getId() : null;
        String impressoraNome = categoria.getImpressora() != null ? categoria.getImpressora().getNome() : null;
        return new CategoriaResponse(categoria.getId(), categoria.getNome(), impressoraId, impressoraNome);
    }
}
