package com.vitorraphael.gestor_comercial.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vitorraphael.gestor_comercial.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
