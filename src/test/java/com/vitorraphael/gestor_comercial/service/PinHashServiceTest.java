package com.vitorraphael.gestor_comercial.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class PinHashServiceTest {

    private final PinHashService pinHashService = new PinHashService();

    @Test
    void deveConferirQuandoPinEstaCorreto() {
        String salt = pinHashService.gerarSalt();
        String hash = pinHashService.hash("1234", salt);

        assertThat(pinHashService.confere("1234", salt, hash)).isTrue();
    }

    @Test
    void naoDeveConferirQuandoPinEstaErrado() {
        String salt = pinHashService.gerarSalt();
        String hash = pinHashService.hash("1234", salt);

        assertThat(pinHashService.confere("9999", salt, hash)).isFalse();
    }

    @Test
    void deveGerarSaltsDiferentesACadaChamada() {
        assertThat(pinHashService.gerarSalt()).isNotEqualTo(pinHashService.gerarSalt());
    }

    @Test
    void mesmoPinComSaltsDiferentesDeveGerarHashesDiferentes() {
        String saltA = pinHashService.gerarSalt();
        String saltB = pinHashService.gerarSalt();

        assertThat(pinHashService.hash("1234", saltA)).isNotEqualTo(pinHashService.hash("1234", saltB));
    }
}
