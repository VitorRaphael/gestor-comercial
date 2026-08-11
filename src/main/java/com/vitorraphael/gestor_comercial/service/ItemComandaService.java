package com.vitorraphael.gestor_comercial.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.vitorraphael.gestor_comercial.exception.RecursoNaoEncontradoException;
import com.vitorraphael.gestor_comercial.exception.RegraDeNegocioException;
import com.vitorraphael.gestor_comercial.model.Comanda;
import com.vitorraphael.gestor_comercial.model.Funcionario;
import com.vitorraphael.gestor_comercial.model.ItemComanda;
import com.vitorraphael.gestor_comercial.model.Produto;
import com.vitorraphael.gestor_comercial.model.StatusComanda;
import com.vitorraphael.gestor_comercial.repository.ItemComandaRepository;

@Service
public class ItemComandaService {

    private final ItemComandaRepository itemComandaRepository;
    private final ComandaService comandaService;
    private final ProdutoService produtoService;
    private final FuncionarioService funcionarioService;

    public ItemComandaService(ItemComandaRepository itemComandaRepository, ComandaService comandaService,
            ProdutoService produtoService, FuncionarioService funcionarioService) {
        this.itemComandaRepository = itemComandaRepository;
        this.comandaService = comandaService;
        this.produtoService = produtoService;
        this.funcionarioService = funcionarioService;
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

    public ItemComanda cancelarItem(Long itemComandaId, String motivo, String pin) {
        ItemComanda item = itemComandaRepository.findById(itemComandaId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Item de comanda não encontrado: " + itemComandaId));

        if (item.isCancelado()) {
            throw new RegraDeNegocioException("Item já está cancelado.");
        }

        Funcionario gerente = funcionarioService.validarPinGerente(pin);

        item.setCancelado(true);
        item.setDataCancelamento(LocalDateTime.now());
        item.setMotivoCancelamento(motivo);
        item.setCanceladoPor(gerente);

        return itemComandaRepository.save(item);
    }
}
