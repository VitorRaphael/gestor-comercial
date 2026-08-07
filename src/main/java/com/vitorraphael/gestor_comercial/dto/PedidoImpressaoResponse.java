package com.vitorraphael.gestor_comercial.dto;

import java.util.List;

public record PedidoImpressaoResponse(String impressora, List<ItemImpressaoResponse> itens) {
}
