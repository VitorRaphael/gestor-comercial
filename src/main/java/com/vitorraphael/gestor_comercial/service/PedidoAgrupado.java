package com.vitorraphael.gestor_comercial.service;

import java.util.List;

import com.vitorraphael.gestor_comercial.model.ItemComanda;

/**
 * Resultado interno do roteamento: um grupo de itens que deve ir para a
 * mesma impressora. Uso interno entre services — nunca deve vazar
 * {@link ItemComanda} cru para o controller (isso é papel do DTO de resposta
 * em {@code dto.PedidoImpressaoResponse}).
 */
public record PedidoAgrupado(String impressoraNome, List<ItemComanda> itens) {
}
