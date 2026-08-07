package com.vitorraphael.gestor_comercial.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.vitorraphael.gestor_comercial.model.Caixa;
import com.vitorraphael.gestor_comercial.model.MovimentoCaixa;
import com.vitorraphael.gestor_comercial.model.TipoMovimento;
import com.vitorraphael.gestor_comercial.repository.MovimentoCaixaRepository;

@Service
public class MovimentoCaixaService {

    private final MovimentoCaixaRepository movimentoCaixaRepository;
    private final CaixaService caixaService;

    public MovimentoCaixaService(MovimentoCaixaRepository movimentoCaixaRepository, CaixaService caixaService) {
        this.movimentoCaixaRepository = movimentoCaixaRepository;
        this.caixaService = caixaService;
    }

    public MovimentoCaixa registrar(TipoMovimento tipo, BigDecimal valor, String descricao) {
        Caixa caixa = caixaService.buscarAberto();

        MovimentoCaixa movimento = new MovimentoCaixa();
        movimento.setCaixa(caixa);
        movimento.setTipo(tipo);
        movimento.setValor(valor);
        movimento.setDescricao(descricao);
        movimento.setDataHora(LocalDateTime.now());

        return movimentoCaixaRepository.save(movimento);
    }

    public List<MovimentoCaixa> listarPorCaixa(Long caixaId) {
        caixaService.buscarPorId(caixaId);
        return movimentoCaixaRepository.findByCaixaId(caixaId);
    }
}
