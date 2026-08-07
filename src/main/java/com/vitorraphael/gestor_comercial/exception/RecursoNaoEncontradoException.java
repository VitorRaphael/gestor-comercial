package com.vitorraphael.gestor_comercial.exception;

/**
 * Lançada quando um recurso buscado por id (ou outra chave) não existe.
 */
public class RecursoNaoEncontradoException extends RuntimeException {

    public RecursoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
