package com.vitorraphael.gestor_comercial.exception;

/**
 * Lançada quando o funcionário autenticado não tem perfil suficiente
 * para a ação (ex: atendente tentando abrir o caixa) (403).
 */
public class AcessoNegadoException extends RuntimeException {

    public AcessoNegadoException(String mensagem) {
        super(mensagem);
    }
}
