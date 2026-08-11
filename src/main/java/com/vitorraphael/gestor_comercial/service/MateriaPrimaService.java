package com.vitorraphael.gestor_comercial.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.vitorraphael.gestor_comercial.exception.RecursoNaoEncontradoException;
import com.vitorraphael.gestor_comercial.exception.RegraDeNegocioException;
import com.vitorraphael.gestor_comercial.model.MateriaPrima;
import com.vitorraphael.gestor_comercial.model.UnidadeMedida;
import com.vitorraphael.gestor_comercial.repository.MateriaPrimaRepository;

@Service
public class MateriaPrimaService {

    private final MateriaPrimaRepository materiaPrimaRepository;

    public MateriaPrimaService(MateriaPrimaRepository materiaPrimaRepository) {
        this.materiaPrimaRepository = materiaPrimaRepository;
    }

    public MateriaPrima criar(String nome, UnidadeMedida unidadeMedida, BigDecimal quantidadeMinima) {
        materiaPrimaRepository.findByNome(nome).ifPresent(m -> {
            throw new RegraDeNegocioException("Já existe uma matéria-prima com o nome '" + nome + "'.");
        });

        MateriaPrima materiaPrima = new MateriaPrima();
        materiaPrima.setNome(nome);
        materiaPrima.setUnidadeMedida(unidadeMedida);
        materiaPrima.setQuantidadeMinima(quantidadeMinima != null ? quantidadeMinima : BigDecimal.ZERO);
        materiaPrima.setQuantidadeEstoque(BigDecimal.ZERO);
        return materiaPrimaRepository.save(materiaPrima);
    }

    public MateriaPrima atualizar(Long id, String nome, UnidadeMedida unidadeMedida, BigDecimal quantidadeMinima) {
        MateriaPrima materiaPrima = buscarPorId(id);
        materiaPrima.setNome(nome);
        materiaPrima.setUnidadeMedida(unidadeMedida);
        materiaPrima.setQuantidadeMinima(quantidadeMinima != null ? quantidadeMinima : BigDecimal.ZERO);
        return materiaPrimaRepository.save(materiaPrima);
    }

    public List<MateriaPrima> listarTodas() {
        return materiaPrimaRepository.findAll();
    }

    public MateriaPrima buscarPorId(Long id) {
        return materiaPrimaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Matéria-prima não encontrada: " + id));
    }

    public MateriaPrima salvar(MateriaPrima materiaPrima) {
        return materiaPrimaRepository.save(materiaPrima);
    }
}
