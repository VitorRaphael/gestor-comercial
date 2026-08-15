package com.vitorraphael.gestor_comercial.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vitorraphael.gestor_comercial.exception.RecursoNaoEncontradoException;
import com.vitorraphael.gestor_comercial.exception.RegraDeNegocioException;
import com.vitorraphael.gestor_comercial.model.ComboItem;
import com.vitorraphael.gestor_comercial.model.Produto;
import com.vitorraphael.gestor_comercial.repository.ComboItemRepository;

/**
 * Composição de combos: liga um {@link Produto} vendido como combo aos
 * {@link Produto}s que ele contém, para que a venda do combo baixe estoque
 * (via {@link FichaTecnicaService} de cada componente) e seja contabilizada
 * como venda dos itens que o compõem — em vez de um produto solto e opaco
 * pro sistema, que é o problema que motivou esta entidade.
 */
@Service
public class ComboItemService {

    private final ComboItemRepository comboItemRepository;
    private final ProdutoService produtoService;

    public ComboItemService(ComboItemRepository comboItemRepository, ProdutoService produtoService) {
        this.comboItemRepository = comboItemRepository;
        this.produtoService = produtoService;
    }

    public ComboItem associar(Long produtoComboId, Long produtoComponenteId, Integer quantidade) {
        if (produtoComboId.equals(produtoComponenteId)) {
            throw new RegraDeNegocioException("Um combo não pode ter a si mesmo como item.");
        }
        Produto produtoCombo = produtoService.buscarPorId(produtoComboId);
        Produto produtoComponente = produtoService.buscarPorId(produtoComponenteId);

        boolean componenteJaEhCombo = !comboItemRepository.findByProdutoComboId(produtoComponenteId).isEmpty();
        if (componenteJaEhCombo) {
            throw new RegraDeNegocioException("Um combo não pode conter outro combo como item.");
        }

        ComboItem comboItem = new ComboItem();
        comboItem.setProdutoCombo(produtoCombo);
        comboItem.setProdutoComponente(produtoComponente);
        comboItem.setQuantidade(quantidade);
        return comboItemRepository.save(comboItem);
    }

    public void remover(Long comboItemId) {
        buscarPorId(comboItemId);
        comboItemRepository.deleteById(comboItemId);
    }

    public List<ComboItem> listarPorCombo(Long produtoComboId) {
        return comboItemRepository.findByProdutoComboId(produtoComboId);
    }

    public ComboItem buscarPorId(Long id) {
        return comboItemRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Item de combo não encontrado: " + id));
    }
}
