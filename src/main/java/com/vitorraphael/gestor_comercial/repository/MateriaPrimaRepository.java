package com.vitorraphael.gestor_comercial.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vitorraphael.gestor_comercial.model.MateriaPrima;

public interface MateriaPrimaRepository extends JpaRepository<MateriaPrima, Long> {

    Optional<MateriaPrima> findByNome(String nome);
}
