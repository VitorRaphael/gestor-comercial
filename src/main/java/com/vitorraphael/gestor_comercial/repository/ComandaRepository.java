package com.vitorraphael.gestor_comercial.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vitorraphael.gestor_comercial.model.Comanda;

public interface ComandaRepository extends JpaRepository<Comanda, Long> {
}
