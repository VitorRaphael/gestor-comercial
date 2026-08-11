package com.vitorraphael.gestor_comercial.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vitorraphael.gestor_comercial.model.MovimentoEstoque;

public interface MovimentoEstoqueRepository extends JpaRepository<MovimentoEstoque, Long> {

    List<MovimentoEstoque> findByMateriaPrimaIdOrderByDataDesc(Long materiaPrimaId);
}
