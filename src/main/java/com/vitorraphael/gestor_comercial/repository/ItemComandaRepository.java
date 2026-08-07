package com.vitorraphael.gestor_comercial.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vitorraphael.gestor_comercial.model.ItemComanda;

public interface ItemComandaRepository extends JpaRepository<ItemComanda, Long> {

    List<ItemComanda> findByComandaId(Long comandaId);
}
