package com.vitorraphael.gestor_comercial.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.vitorraphael.gestor_comercial.exception.RecursoNaoEncontradoException;
import com.vitorraphael.gestor_comercial.exception.RegraDeNegocioException;
import com.vitorraphael.gestor_comercial.model.Categoria;
import com.vitorraphael.gestor_comercial.model.Comanda;
import com.vitorraphael.gestor_comercial.model.ItemComanda;
import com.vitorraphael.gestor_comercial.model.Mesa;
import com.vitorraphael.gestor_comercial.model.Produto;
import com.vitorraphael.gestor_comercial.model.StatusComanda;
import com.vitorraphael.gestor_comercial.model.StatusMesa;
import com.vitorraphael.gestor_comercial.repository.CategoriaRepository;
import com.vitorraphael.gestor_comercial.repository.ComandaRepository;
import com.vitorraphael.gestor_comercial.repository.ItemComandaRepository;
import com.vitorraphael.gestor_comercial.repository.MesaRepository;
import com.vitorraphael.gestor_comercial.repository.ProdutoRepository;

@ExtendWith(MockitoExtension.class)
class ItemComandaServiceTest {

    @Mock
    private ItemComandaRepository itemComandaRepository;

    @Mock
    private ComandaRepository comandaRepository;

    @Mock
    private MesaRepository mesaRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    private ItemComandaService itemComandaService;

    @BeforeEach
    void setUp() {
        MesaService mesaService = new MesaService(mesaRepository);
        MovimentoEstoqueService movimentoEstoqueService = new MovimentoEstoqueService(null, null, null, null);
        ComandaService comandaService = new ComandaService(comandaRepository, mesaService, itemComandaRepository,
                movimentoEstoqueService, null);
        ImpressoraService impressoraService = new ImpressoraService(null);
        CategoriaService categoriaService = new CategoriaService(categoriaRepository, impressoraService, produtoRepository);
        ProdutoService produtoService = new ProdutoService(produtoRepository, categoriaService);
        itemComandaService = new ItemComandaService(itemComandaRepository, comandaService, produtoService, null);
    }

    private Comanda criarComanda(Long id, StatusComanda status) {
        Mesa mesa = new Mesa();
        mesa.setId(1L);
        mesa.setNumero(1);
        mesa.setStatus(StatusMesa.OCUPADA);

        Comanda comanda = new Comanda();
        comanda.setId(id);
        comanda.setMesa(mesa);
        comanda.setStatus(status);
        return comanda;
    }

    private Produto criarProduto(Long id, boolean ativo, BigDecimal preco) {
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNome("Lanches");

        Produto produto = new Produto();
        produto.setId(id);
        produto.setNome("X-Burger");
        produto.setPreco(preco);
        produto.setCategoria(categoria);
        produto.setAtivo(ativo);
        return produto;
    }

    @Test
    void deveAdicionarItemComPrecoUnitarioIgualAoPrecoAtualDoProduto() {
        Comanda comanda = criarComanda(1L, StatusComanda.ABERTA);
        Produto produto = criarProduto(2L, true, new BigDecimal("25.90"));

        when(comandaRepository.findById(1L)).thenReturn(Optional.of(comanda));
        when(produtoRepository.findById(2L)).thenReturn(Optional.of(produto));
        when(itemComandaRepository.save(any(ItemComanda.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ItemComanda item = itemComandaService.adicionarItem(1L, 2L, 3, "sem cebola");

        assertThat(item.getPrecoUnitario()).isEqualByComparingTo(new BigDecimal("25.90"));
        assertThat(item.getComanda()).isEqualTo(comanda);
        assertThat(item.getProduto()).isEqualTo(produto);
        assertThat(item.getQuantidade()).isEqualTo(3);
        assertThat(item.getObservacao()).isEqualTo("sem cebola");
    }

    @Test
    void deveLancarExcecaoAoAdicionarItemEmComandaFechada() {
        Comanda comanda = criarComanda(1L, StatusComanda.FECHADA);
        when(comandaRepository.findById(1L)).thenReturn(Optional.of(comanda));

        assertThrows(RegraDeNegocioException.class,
                () -> itemComandaService.adicionarItem(1L, 2L, 1, null));

        verify(itemComandaRepository, never()).save(any(ItemComanda.class));
    }

    @Test
    void deveLancarExcecaoAoAdicionarProdutoInativo() {
        Comanda comanda = criarComanda(1L, StatusComanda.ABERTA);
        Produto produto = criarProduto(2L, false, new BigDecimal("10.00"));

        when(comandaRepository.findById(1L)).thenReturn(Optional.of(comanda));
        when(produtoRepository.findById(2L)).thenReturn(Optional.of(produto));

        assertThrows(RegraDeNegocioException.class,
                () -> itemComandaService.adicionarItem(1L, 2L, 1, null));

        verify(itemComandaRepository, never()).save(any(ItemComanda.class));
    }

    @Test
    void deveLancarExcecaoAoRemoverItemDeComandaFechada() {
        Comanda comanda = criarComanda(1L, StatusComanda.FECHADA);
        Produto produto = criarProduto(2L, true, new BigDecimal("10.00"));

        ItemComanda item = new ItemComanda();
        item.setId(5L);
        item.setComanda(comanda);
        item.setProduto(produto);
        item.setQuantidade(1);
        item.setPrecoUnitario(new BigDecimal("10.00"));

        when(itemComandaRepository.findById(5L)).thenReturn(Optional.of(item));

        assertThrows(RegraDeNegocioException.class, () -> itemComandaService.removerItem(5L));

        verify(itemComandaRepository, never()).delete(any(ItemComanda.class));
    }

    @Test
    void deveLancarExcecaoAoRemoverItemInexistente() {
        when(itemComandaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class, () -> itemComandaService.removerItem(99L));
    }
}
