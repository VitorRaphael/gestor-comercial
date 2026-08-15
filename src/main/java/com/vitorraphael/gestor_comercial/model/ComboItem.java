package com.vitorraphael.gestor_comercial.model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Associa um {@link Produto} que é vendido como combo a um {@link Produto}
 * que o compõe, para que a venda do combo seja contabilizada (estoque via
 * {@link FichaTecnica} do componente, curva ABC/mix) como venda dos itens
 * que o compõem, em vez de um produto solto sem relação com o cardápio.
 */
@Entity
public class ComboItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "produto_combo_id", nullable = false)
    private Produto produtoCombo;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "produto_componente_id", nullable = false)
    private Produto produtoComponente;

    @NotNull
    @Positive
    @Column(nullable = false)
    private Integer quantidade;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Produto getProdutoCombo() {
        return produtoCombo;
    }

    public void setProdutoCombo(Produto produtoCombo) {
        this.produtoCombo = produtoCombo;
    }

    public Produto getProdutoComponente() {
        return produtoComponente;
    }

    public void setProdutoComponente(Produto produtoComponente) {
        this.produtoComponente = produtoComponente;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ComboItem that = (ComboItem) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
