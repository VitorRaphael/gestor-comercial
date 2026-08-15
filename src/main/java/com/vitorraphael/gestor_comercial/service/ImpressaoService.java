package com.vitorraphael.gestor_comercial.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.vitorraphael.gestor_comercial.dto.ItemImpressaoResponse;
import com.vitorraphael.gestor_comercial.dto.PedidoImpressaoResponse;
import com.vitorraphael.gestor_comercial.model.ItemComanda;

/**
 * Impressão SIMULADA (Fase 3): em vez de enviar bytes ESC/POS para uma
 * impressora térmica física, formata um recibo de texto e loga via SLF4J.
 * Quando o hardware estiver disponível, o envio real (escpos-coffee) troca
 * apenas a "borda" desta classe — o roteamento por categoria não muda.
 */
@Service
public class ImpressaoService {

    private static final Logger log = LoggerFactory.getLogger(ImpressaoService.class);

    private final RoteamentoImpressaoService roteamentoImpressaoService;

    public ImpressaoService(RoteamentoImpressaoService roteamentoImpressaoService) {
        this.roteamentoImpressaoService = roteamentoImpressaoService;
    }

    public List<PedidoImpressaoResponse> imprimirComanda(Long comandaId) {
        List<PedidoAgrupado> grupos = roteamentoImpressaoService.rotear(comandaId);

        return grupos.stream()
                .map(this::imprimirGrupo)
                .toList();
    }

    private PedidoImpressaoResponse imprimirGrupo(PedidoAgrupado grupo) {
        List<ItemImpressaoResponse> itens = grupo.itens().stream()
                .map(ItemImpressaoResponse::de)
                .toList();

        log.info(formatarRecibo(grupo.impressoraNome(), grupo.itens()));

        return new PedidoImpressaoResponse(grupo.impressoraNome(), itens);
    }

    private String formatarRecibo(String impressoraNome, List<ItemComanda> itens) {
        StringBuilder recibo = new StringBuilder();
        recibo.append("\n===== ").append(impressoraNome).append(" =====\n");
        for (ItemComanda item : itens) {
            recibo.append(item.getQuantidade())
                    .append("x ")
                    .append(item.getProduto().getNome());
            if (item.getObservacao() != null && !item.getObservacao().isBlank()) {
                recibo.append(" (").append(item.getObservacao()).append(")");
            }
            recibo.append("\n");
            String descricao = item.getProduto().getDescricao();
            if (descricao != null && !descricao.isBlank()) {
                recibo.append("   ").append(descricao).append("\n");
            }
        }
        recibo.append("========================");
        return recibo.toString();
    }
}
