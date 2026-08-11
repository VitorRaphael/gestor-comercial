package com.vitorraphael.gestor_comercial.model;

/**
 * Forma de pagamento usada em um {@link Pagamento} de comanda.
 */
public enum FormaPagamento {
    CREDITO,
    DEBITO,
    DINHEIRO,
    PIX,
    CONSUMO_INTERNO
}
