package com.vitorraphael.gestor_comercial.dto;

public record LoginResponse(String token, Long funcionarioId, String nome, String perfil) {
}
