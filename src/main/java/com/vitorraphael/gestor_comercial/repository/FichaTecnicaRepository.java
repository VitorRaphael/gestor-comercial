package com.vitorraphael.gestor_comercial.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vitorraphael.gestor_comercial.model.FichaTecnica;

public interface FichaTecnicaRepository extends JpaRepository<FichaTecnica, Long> {

    List<FichaTecnica> findByProdutoId(Long produtoId);
}
