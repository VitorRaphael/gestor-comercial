package com.vitorraphael.gestor_comercial.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vitorraphael.gestor_comercial.model.Caixa;
import com.vitorraphael.gestor_comercial.model.StatusCaixa;

public interface CaixaRepository extends JpaRepository<Caixa, Long> {

    Optional<Caixa> findByStatus(StatusCaixa status);

    Optional<Caixa> findFirstByStatusOrderByDataFechamentoDesc(StatusCaixa status);
}
