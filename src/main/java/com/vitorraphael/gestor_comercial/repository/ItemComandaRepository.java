package com.vitorraphael.gestor_comercial.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vitorraphael.gestor_comercial.model.ItemComanda;

public interface ItemComandaRepository extends JpaRepository<ItemComanda, Long> {

    List<ItemComanda> findByComandaId(Long comandaId);

    List<ItemComanda> findByComandaIdIn(List<Long> comandaIds);

    List<ItemComanda> findByCanceladoTrueAndDataCancelamentoBetween(LocalDateTime inicio, LocalDateTime fim);

    boolean existsByProdutoId(Long produtoId);

    boolean existsByComandaId(Long comandaId);
}
