package com.vitorraphael.gestor_comercial.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vitorraphael.gestor_comercial.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
