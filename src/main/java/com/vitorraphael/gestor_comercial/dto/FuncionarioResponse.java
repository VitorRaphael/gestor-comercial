package com.vitorraphael.gestor_comercial.dto;

import com.vitorraphael.gestor_comercial.model.Funcionario;

public record FuncionarioResponse(Long id, String nome, String perfil, boolean ativo) {

    public static FuncionarioResponse de(Funcionario funcionario) {
        return new FuncionarioResponse(
                funcionario.getId(),
                funcionario.getNome(),
                funcionario.getPerfil().name(),
                funcionario.isAtivo());
    }
}
