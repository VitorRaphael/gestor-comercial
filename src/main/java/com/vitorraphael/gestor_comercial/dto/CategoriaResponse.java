package com.vitorraphael.gestor_comercial.dto;

import com.vitorraphael.gestor_comercial.model.Categoria;

public record CategoriaResponse(Long id, String nome) {

    public static CategoriaResponse de(Categoria categoria) {
        return new CategoriaResponse(categoria.getId(), categoria.getNome());
    }
}
