package com.vitorraphael.gestor_comercial.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vitorraphael.gestor_comercial.exception.RecursoNaoEncontradoException;
import com.vitorraphael.gestor_comercial.exception.RegraDeNegocioException;
import com.vitorraphael.gestor_comercial.model.Mesa;
import com.vitorraphael.gestor_comercial.model.StatusMesa;
import com.vitorraphael.gestor_comercial.repository.MesaRepository;

@Service
public class MesaService {

    private final MesaRepository mesaRepository;

    public MesaService(MesaRepository mesaRepository) {
        this.mesaRepository = mesaRepository;
    }

    public Mesa criar(Integer numero) {
        mesaRepository.findByNumero(numero).ifPresent(m -> {
            throw new RegraDeNegocioException("Já existe uma mesa com o número " + numero + ".");
        });

        Mesa mesa = new Mesa();
        mesa.setNumero(numero);
        mesa.setStatus(StatusMesa.LIVRE);
        return mesaRepository.save(mesa);
    }

    public List<Mesa> listarTodas() {
        return mesaRepository.findAll();
    }

    public Mesa buscarPorId(Long id) {
        return mesaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Mesa não encontrada: " + id));
    }

    public Mesa salvar(Mesa mesa) {
        return mesaRepository.save(mesa);
    }
}
