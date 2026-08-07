package com.vitorraphael.gestor_comercial.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vitorraphael.gestor_comercial.model.Impressora;

public interface ImpressoraRepository extends JpaRepository<Impressora, Long> {

    Optional<Impressora> findByNome(String nome);
}
