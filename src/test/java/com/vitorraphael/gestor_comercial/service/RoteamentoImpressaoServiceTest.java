package com.vitorraphael.gestor_comercial.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.vitorraphael.gestor_comercial.model.Categoria;
import com.vitorraphael.gestor_comercial.model.Impressora;
import com.vitorraphael.gestor_comercial.model.ItemComanda;
import com.vitorraphael.gestor_comercial.model.Produto;

@ExtendWith(MockitoExtension.class)
class RoteamentoImpressaoServiceTest {

    @Mock
    private ItemComandaService itemComandaService;

    private RoteamentoImpressaoService roteamentoImpressaoService;

    @BeforeEach
    void setUp() {
        roteamentoImpressaoService = new RoteamentoImpressaoService(itemComandaService);
    }

    private Produto criarProduto(Long id, String nome, Impressora impressora) {
        Categoria categoria = new Categoria();
        categoria.setId(100L + id);
        categoria.setNome("Categoria-" + id);
        categoria.setImpressora(impressora);

        Produto produto = new Produto();
        produto.setId(id);
        produto.setNome(nome);
        produto.setPreco(new BigDecimal("10.00"));
        produto.setCategoria(categoria);
        return produto;
    }

    private ItemComanda criarItem(Long id, Produto produto, Integer quantidade) {
        ItemComanda item = new ItemComanda();
        item.setId(id);
        item.setProduto(produto);
        item.setQuantidade(quantidade);
        item.setPrecoUnitario(produto.getPreco());
        return item;
    }

    @Test
    void deveAgruparItensDeCategoriasDiferentesEmImpressorasDiferentes() {
        Impressora trailer1 = new Impressora();
        trailer1.setId(1L);
        trailer1.setNome("Trailer 1");

        Impressora trailer2 = new Impressora();
        trailer2.setId(2L);
        trailer2.setNome("Trailer 2");

        Produto xBurger = criarProduto(1L, "X-Burger", trailer1);
        Produto refrigerante = criarProduto(2L, "Refrigerante", trailer2);

        ItemComanda item1 = criarItem(1L, xBurger, 2);
        ItemComanda item2 = criarItem(2L, refrigerante, 1);

        when(itemComandaService.listarPorComanda(10L)).thenReturn(List.of(item1, item2));

        List<PedidoAgrupado> resultado = roteamentoImpressaoService.rotear(10L);

        assertThat(resultado).hasSize(2);
        assertThat(resultado).anySatisfy(grupo -> {
            assertThat(grupo.impressoraNome()).isEqualTo("Trailer 1");
            assertThat(grupo.itens()).containsExactly(item1);
        });
        assertThat(resultado).anySatisfy(grupo -> {
            assertThat(grupo.impressoraNome()).isEqualTo("Trailer 2");
            assertThat(grupo.itens()).containsExactly(item2);
        });
    }

    @Test
    void deveAgruparItemSemImpressoraDefinidaSemLancarExcecao() {
        Produto sobremesa = criarProduto(3L, "Pudim", null);
        ItemComanda item = criarItem(3L, sobremesa, 1);

        when(itemComandaService.listarPorComanda(20L)).thenReturn(List.of(item));

        List<PedidoAgrupado> resultado = roteamentoImpressaoService.rotear(20L);

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).impressoraNome()).isEqualTo(RoteamentoImpressaoService.SEM_IMPRESSORA_DEFINIDA);
        assertThat(resultado.get(0).itens()).containsExactly(item);
    }

    @Test
    void deveAgruparTodosOsItensNaMesmaImpressoraEmUmUnicoGrupo() {
        Impressora trailer1 = new Impressora();
        trailer1.setId(1L);
        trailer1.setNome("Trailer 1");

        Produto xBurger = criarProduto(1L, "X-Burger", trailer1);
        Produto xSalada = criarProduto(4L, "X-Salada", trailer1);

        ItemComanda item1 = criarItem(1L, xBurger, 2);
        ItemComanda item2 = criarItem(2L, xSalada, 1);

        when(itemComandaService.listarPorComanda(30L)).thenReturn(List.of(item1, item2));

        List<PedidoAgrupado> resultado = roteamentoImpressaoService.rotear(30L);

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).impressoraNome()).isEqualTo("Trailer 1");
        assertThat(resultado.get(0).itens()).containsExactly(item1, item2);
    }
}
