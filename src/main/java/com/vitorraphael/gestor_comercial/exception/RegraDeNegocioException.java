package com.vitorraphael.gestor_comercial.exception;

/**
 * Lançada quando uma operação viola uma regra de negócio
 * (ex: abrir uma comanda em mesa já ocupada, lançar item em comanda fechada).
 */
public class RegraDeNegocioException extends RuntimeException {

    public RegraDeNegocioException(String mensagem) {
        super(mensagem);
    }
}
