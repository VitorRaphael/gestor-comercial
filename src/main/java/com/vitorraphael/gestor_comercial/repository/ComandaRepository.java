package com.vitorraphael.gestor_comercial.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vitorraphael.gestor_comercial.model.Comanda;
import com.vitorraphael.gestor_comercial.model.StatusComanda;

public interface ComandaRepository extends JpaRepository<Comanda, Long> {

    Optional<Comanda> findByMesaIdAndStatus(Long mesaId, StatusComanda status);

    List<Comanda> findByStatus(StatusComanda status);

    List<Comanda> findByStatusAndDataFechamentoBetween(StatusComanda status, LocalDateTime inicio, LocalDateTime fim);
}
