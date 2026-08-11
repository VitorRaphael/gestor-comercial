package com.vitorraphael.gestor_comercial.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.vitorraphael.gestor_comercial.model.PerfilFuncionario;
import com.vitorraphael.gestor_comercial.repository.FuncionarioRepository;
import com.vitorraphael.gestor_comercial.service.FuncionarioService;

/**
 * Garante que sempre exista pelo menos um gerente: como criar funcionário
 * exige estar logado como gerente, sem isso a primeira execução nunca
 * conseguiria cadastrar ninguém. PIN padrão "264072" — trocar antes de usar
 * em produção (ainda não há endpoint de troca de PIN, ver PLANTA_PROJETO.md).
 */
@Component
public class FuncionarioSeeder implements CommandLineRunner {

    private final FuncionarioRepository funcionarioRepository;
    private final FuncionarioService funcionarioService;

    public FuncionarioSeeder(FuncionarioRepository funcionarioRepository, FuncionarioService funcionarioService) {
        this.funcionarioRepository = funcionarioRepository;
        this.funcionarioService = funcionarioService;
    }

    @Override
    public void run(String... args) {
        if (funcionarioRepository.count() == 0) {
            funcionarioService.criar("Gerente", "264072", PerfilFuncionario.GERENTE);
        }
    }
}
