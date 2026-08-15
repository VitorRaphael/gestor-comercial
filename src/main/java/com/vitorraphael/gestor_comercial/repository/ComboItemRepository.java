package com.vitorraphael.gestor_comercial.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vitorraphael.gestor_comercial.model.ComboItem;

public interface ComboItemRepository extends JpaRepository<ComboItem, Long> {

    List<ComboItem> findByProdutoComboId(Long produtoComboId);

    boolean existsByProdutoComboId(Long produtoComboId);
}
