package com.vitorraphael.gestor_comercial.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.Objects;

/**
 * Pagamento parcial de uma {@link Comanda}. Uma comanda pode ter N
 * {@link Pagamento} (split entre formas), fechando quando a soma bate
 * exatamente com o total da conta.
 */
@Entity
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "comanda_id", nullable = false)
    private Comanda comanda;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FormaPagamento formaPagamento;

    @NotNull
    @Positive
    @Column(nullable = false)
    private BigDecimal valor;

    @Column
    private BigDecimal troco;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "funcionario_consumidor_id", nullable = true)
    private Funcionario funcionarioConsumidor;

    @Column(nullable = false, columnDefinition = "numeric not null default 0")
    private BigDecimal valorQuitado = BigDecimal.ZERO;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime dataHora;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Comanda getComanda() {
        return comanda;
    }

    public void setComanda(Comanda comanda) {
        this.comanda = comanda;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getTroco() {
        return troco;
    }

    public void setTroco(BigDecimal troco) {
        this.troco = troco;
    }

    public Funcionario getFuncionarioConsumidor() {
        return funcionarioConsumidor;
    }

    public void setFuncionarioConsumidor(Funcionario funcionarioConsumidor) {
        this.funcionarioConsumidor = funcionarioConsumidor;
    }

    public BigDecimal getValorQuitado() {
        return valorQuitado;
    }

    public void setValorQuitado(BigDecimal valorQuitado) {
        this.valorQuitado = valorQuitado;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pagamento that = (Pagamento) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
