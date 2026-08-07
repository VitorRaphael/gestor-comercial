package com.vitorraphael.gestor_comercial.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.vitorraphael.gestor_comercial.exception.NaoAutorizadoException;
import com.vitorraphael.gestor_comercial.model.Funcionario;
import com.vitorraphael.gestor_comercial.model.PerfilFuncionario;

class SessaoServiceTest {

    private final SessaoService sessaoService = new SessaoService();

    @Test
    void deveAutenticarComTokenDeSessaoValido() {
        Funcionario funcionario = new Funcionario();
        funcionario.setId(1L);
        funcionario.setPerfil(PerfilFuncionario.ATENDENTE);

        String token = sessaoService.criarSessao(funcionario);

        assertThat(sessaoService.autenticar(token)).isEqualTo(funcionario);
    }

    @Test
    void deveLancarExcecaoParaTokenInexistente() {
        assertThrows(NaoAutorizadoException.class, () -> sessaoService.autenticar("token-invalido"));
    }

    @Test
    void naoDeveAutenticarAposEncerrarSessao() {
        Funcionario funcionario = new Funcionario();
        funcionario.setId(1L);
        String token = sessaoService.criarSessao(funcionario);

        sessaoService.encerrar(token);

        assertThrows(NaoAutorizadoException.class, () -> sessaoService.autenticar(token));
    }
}
