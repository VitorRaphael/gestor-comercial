package com.vitorraphael.gestor_comercial.dto;

import com.vitorraphael.gestor_comercial.model.PerfilFuncionario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record FuncionarioRequest(
        @NotBlank(message = "O nome é obrigatório.") String nome,
        @NotBlank(message = "O PIN é obrigatório.") @Pattern(regexp = "\\d{4,6}", message = "O PIN deve ter de 4 a 6 dígitos.") String pin,
        @NotNull(message = "O perfil é obrigatório.") PerfilFuncionario perfil) {
}
