package com.vitorraphael.gestor_comercial.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vitorraphael.gestor_comercial.model.QuitacaoConsumo;

public interface QuitacaoConsumoRepository extends JpaRepository<QuitacaoConsumo, Long> {

    List<QuitacaoConsumo> findByFuncionarioIdOrderByDataHoraDesc(Long funcionarioId);
}
