package com.vitorraphael.gestor_comercial.exception;

/**
 * Lançada quando a requisição não traz um token de sessão válido (401).
 */
public class NaoAutorizadoException extends RuntimeException {

    public NaoAutorizadoException(String mensagem) {
        super(mensagem);
    }
}
