package com.vitorraphael.gestor_comercial.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vitorraphael.gestor_comercial.exception.RecursoNaoEncontradoException;
import com.vitorraphael.gestor_comercial.exception.RegraDeNegocioException;
import com.vitorraphael.gestor_comercial.model.Comanda;
import com.vitorraphael.gestor_comercial.model.ItemComanda;
import com.vitorraphael.gestor_comercial.model.Produto;
import com.vitorraphael.gestor_comercial.model.StatusComanda;
import com.vitorraphael.gestor_comercial.repository.ItemComandaRepository;

@Service
public class ItemComandaService {

    private final ItemComandaRepository itemComandaRepository;
    private final ComandaService comandaService;
    private final ProdutoService produtoService;

    public ItemComandaService(ItemComandaRepository itemComandaRepository, ComandaService comandaService,
            ProdutoService produtoService) {
        this.itemComandaRepository = itemComandaRepository;
        this.comandaService = comandaService;
        this.produtoService = produtoService;
    }

    public ItemComanda adicionarItem(Long comandaId, Long produtoId, Integer quantidade, String observacao) {
        Comanda comanda = comandaService.buscarPorId(comandaId);
        if (comanda.getStatus() != StatusComanda.ABERTA) {
            throw new RegraDeNegocioException("Não é possível lançar itens em uma comanda que não está aberta.");
        }

        Produto produto = produtoService.buscarPorId(produtoId);
        if (!produto.isAtivo()) {
            throw new RegraDeNegocioException("Não é possível lançar um produto inativo.");
        }

        ItemComanda item = new ItemComanda();
        item.setComanda(comanda);
        item.setProduto(produto);
        item.setQuantidade(quantidade);
        item.setObservacao(observacao);
        item.setPrecoUnitario(produto.getPreco());

        return itemComandaRepository.save(item);
    }

    public void removerItem(Long itemComandaId) {
        ItemComanda item = itemComandaRepository.findById(itemComandaId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Item de comanda não encontrado: " + itemComandaId));

        if (item.getComanda().getStatus() != StatusComanda.ABERTA) {
            throw new RegraDeNegocioException("Não é possível remover itens de uma comanda que não está aberta.");
        }

        itemComandaRepository.delete(item);
    }

    public List<ItemComanda> listarPorComanda(Long comandaId) {
        comandaService.buscarPorId(comandaId);
        return itemComandaRepository.findByComandaId(comandaId);
    }
}
