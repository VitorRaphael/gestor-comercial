package com.vitorraphael.gestor_comercial.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vitorraphael.gestor_comercial.model.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    List<Funcionario> findByAtivoTrue();
}
