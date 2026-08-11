package com.vitorraphael.gestor_comercial.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.vitorraphael.gestor_comercial.model.Comanda;
import com.vitorraphael.gestor_comercial.model.FichaTecnica;
import com.vitorraphael.gestor_comercial.model.ItemComanda;
import com.vitorraphael.gestor_comercial.model.MateriaPrima;
import com.vitorraphael.gestor_comercial.model.MovimentoEstoque;
import com.vitorraphael.gestor_comercial.model.TipoMovimentoEstoque;
import com.vitorraphael.gestor_comercial.repository.MovimentoEstoqueRepository;

/**
 * Registra entradas e saídas de {@link MateriaPrima}. A baixa por venda
 * roda no fechamento da {@link Comanda} (não a cada {@link ItemComanda}
 * lançado), para não descontar estoque de itens que ainda podem ser
 * removidos antes de fechar a conta — decisão da seção 7 do
 * SALES_ANALYTICS_PLANO.md. Saldo pode ficar negativo (venda sem
 * matéria-prima suficiente não é bloqueada), sinalizando furo de estoque
 * para o gestor corrigir depois.
 */
@Service
public class MovimentoEstoqueService {

    private final MovimentoEstoqueRepository movimentoEstoqueRepository;
    private final MateriaPrimaService materiaPrimaService;
    private final FichaTecnicaService fichaTecnicaService;

    public MovimentoEstoqueService(MovimentoEstoqueRepository movimentoEstoqueRepository,
            MateriaPrimaService materiaPrimaService, FichaTecnicaService fichaTecnicaService) {
        this.movimentoEstoqueRepository = movimentoEstoqueRepository;
        this.materiaPrimaService = materiaPrimaService;
        this.fichaTecnicaService = fichaTecnicaService;
    }

    public MovimentoEstoque registrarCompra(Long materiaPrimaId, BigDecimal quantidade) {
        MateriaPrima materiaPrima = materiaPrimaService.buscarPorId(materiaPrimaId);
        return registrar(materiaPrima, TipoMovimentoEstoque.ENTRADA_COMPRA, quantidade, null);
    }

    public MovimentoEstoque registrarAjuste(Long materiaPrimaId, BigDecimal quantidade) {
        MateriaPrima materiaPrima = materiaPrimaService.buscarPorId(materiaPrimaId);
        return registrar(materiaPrima, TipoMovimentoEstoque.AJUSTE_MANUAL, quantidade, null);
    }

    public void darBaixaPorFechamentoDeComanda(Comanda comanda, List<ItemComanda> itens) {
        for (ItemComanda item : itens) {
            List<FichaTecnica> ficha = fichaTecnicaService.listarPorProduto(item.getProduto().getId());
            for (FichaTecnica vinculo : ficha) {
                BigDecimal quantidadeConsumida = vinculo.getQuantidadeUsada()
                        .multiply(BigDecimal.valueOf(item.getQuantidade()));
                registrar(vinculo.getMateriaPrima(), TipoMovimentoEstoque.SAIDA_VENDA,
                        quantidadeConsumida.negate(), comanda);
            }
        }
    }

    private MovimentoEstoque registrar(MateriaPrima materiaPrima, TipoMovimentoEstoque tipo, BigDecimal quantidade,
            Comanda comanda) {
        materiaPrima.setQuantidadeEstoque(materiaPrima.getQuantidadeEstoque().add(quantidade));
        materiaPrimaService.salvar(materiaPrima);

        MovimentoEstoque movimento = new MovimentoEstoque();
        movimento.setMateriaPrima(materiaPrima);
        movimento.setTipo(tipo);
        movimento.setQuantidade(quantidade);
        movimento.setData(LocalDateTime.now());
        movimento.setComanda(comanda);
        return movimentoEstoqueRepository.save(movimento);
    }

    public List<MovimentoEstoque> listarPorMateriaPrima(Long materiaPrimaId) {
        return movimentoEstoqueRepository.findByMateriaPrimaIdOrderByDataDesc(materiaPrimaId);
    }
}
