package com.vitorraphael.gestor_comercial.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vitorraphael.gestor_comercial.model.ItemComanda;

public interface ItemComandaRepository extends JpaRepository<ItemComanda, Long> {
}
