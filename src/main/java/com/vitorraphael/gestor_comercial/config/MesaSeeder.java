package com.vitorraphael.gestor_comercial.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.vitorraphael.gestor_comercial.repository.MesaRepository;
import com.vitorraphael.gestor_comercial.service.MesaService;

/**
 * Garante que as 60 mesas do food truck já existam ao iniciar o sistema,
 * para o atendente não precisar cadastrar mesa por mesa manualmente.
 */
@Component
public class MesaSeeder implements CommandLineRunner {

    private static final int TOTAL_MESAS = 60;

    private final MesaRepository mesaRepository;
    private final MesaService mesaService;

    public MesaSeeder(MesaRepository mesaRepository, MesaService mesaService) {
        this.mesaRepository = mesaRepository;
        this.mesaService = mesaService;
    }

    @Override
    public void run(String... args) {
        for (int numero = 1; numero <= TOTAL_MESAS; numero++) {
            if (mesaRepository.findByNumero(numero).isEmpty()) {
                mesaService.criar(numero);
            }
        }
    }
}
