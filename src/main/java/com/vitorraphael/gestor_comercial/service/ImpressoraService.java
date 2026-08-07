package com.vitorraphael.gestor_comercial.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vitorraphael.gestor_comercial.exception.RecursoNaoEncontradoException;
import com.vitorraphael.gestor_comercial.exception.RegraDeNegocioException;
import com.vitorraphael.gestor_comercial.model.Impressora;
import com.vitorraphael.gestor_comercial.repository.ImpressoraRepository;

@Service
public class ImpressoraService {

    private final ImpressoraRepository impressoraRepository;

    public ImpressoraService(ImpressoraRepository impressoraRepository) {
        this.impressoraRepository = impressoraRepository;
    }

    public Impressora criar(String nome) {
        impressoraRepository.findByNome(nome).ifPresent(i -> {
            throw new RegraDeNegocioException("Já existe uma impressora com o nome '" + nome + "'.");
        });

        Impressora impressora = new Impressora();
        impressora.setNome(nome);
        return impressoraRepository.save(impressora);
    }

    public List<Impressora> listarTodas() {
        return impressoraRepository.findAll();
    }

    public Impressora buscarPorId(Long id) {
        return impressoraRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Impressora não encontrada: " + id));
    }
}
