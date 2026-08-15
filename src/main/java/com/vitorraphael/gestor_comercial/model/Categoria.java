package com.vitorraphael.gestor_comercial.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

/**
 * Categoria de produtos do cardápio (ex: Lanches, Bebidas, Sobremesas).
 * Entidade própria (não enum) porque na Fase 3 ela será a chave do
 * roteamento de impressão por categoria -> impressora.
 */
@Entity
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String nome;

    // Opcional: categorias da Fase 1 já existem sem impressora associada.
    // A associação é feita depois via endpoint dedicado (associarImpressora).
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "impressora_id", nullable = true)
    private Impressora impressora;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean ativo = true;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Impressora getImpressora() {
        return impressora;
    }

    public void setImpressora(Impressora impressora) {
        this.impressora = impressora;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Categoria that = (Categoria) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
