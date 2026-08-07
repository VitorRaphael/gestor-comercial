package com.vitorraphael.gestor_comercial.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.vitorraphael.gestor_comercial.model.Impressora;
import com.vitorraphael.gestor_comercial.model.ItemComanda;

/**
 * Agrupa os itens de uma comanda por impressora, com base na categoria do
 * produto de cada item (categoria -> impressora).
 */
@Service
public class RoteamentoImpressaoService {

    // Chave usada quando a categoria do item ainda não tem impressora
    // associada. Optamos por NÃO lançar exceção aqui: travar a impressão de
    // toda a comanda por causa de uma única categoria mal configurada
    // atrapalharia o operador no meio do expediente. Em vez disso, o grupo
    // fica visível separadamente para ele perceber e corrigir a associação
    // depois (via PATCH /api/categorias/{id}/impressora).
    static final String SEM_IMPRESSORA_DEFINIDA = "SEM_IMPRESSORA_DEFINIDA";

    private final ItemComandaService itemComandaService;

    public RoteamentoImpressaoService(ItemComandaService itemComandaService) {
        this.itemComandaService = itemComandaService;
    }

    public List<PedidoAgrupado> rotear(Long comandaId) {
        List<ItemComanda> itens = itemComandaService.listarPorComanda(comandaId);

        Map<String, List<ItemComanda>> agrupado = new LinkedHashMap<>();
        for (ItemComanda item : itens) {
            String chave = nomeImpressora(item);
            agrupado.computeIfAbsent(chave, k -> new ArrayList<>()).add(item);
        }

        List<PedidoAgrupado> resultado = new ArrayList<>();
        for (Map.Entry<String, List<ItemComanda>> entrada : agrupado.entrySet()) {
            resultado.add(new PedidoAgrupado(entrada.getKey(), entrada.getValue()));
        }
        return resultado;
    }

    private String nomeImpressora(ItemComanda item) {
        Impressora impressora = item.getProduto().getCategoria().getImpressora();
        return impressora != null ? impressora.getNome() : SEM_IMPRESSORA_DEFINIDA;
    }
}
