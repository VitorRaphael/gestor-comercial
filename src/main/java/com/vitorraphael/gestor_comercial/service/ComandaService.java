package com.vitorraphael.gestor_comercial.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.vitorraphael.gestor_comercial.exception.RecursoNaoEncontradoException;
import com.vitorraphael.gestor_comercial.exception.RegraDeNegocioException;
import com.vitorraphael.gestor_comercial.model.Comanda;
import com.vitorraphael.gestor_comercial.model.ItemComanda;
import com.vitorraphael.gestor_comercial.model.Mesa;
import com.vitorraphael.gestor_comercial.model.StatusComanda;
import com.vitorraphael.gestor_comercial.model.StatusMesa;
import com.vitorraphael.gestor_comercial.repository.ComandaRepository;
import com.vitorraphael.gestor_comercial.repository.ItemComandaRepository;

@Service
public class ComandaService {

    private final ComandaRepository comandaRepository;
    private final MesaService mesaService;
    private final ItemComandaRepository itemComandaRepository;
    private final MovimentoEstoqueService movimentoEstoqueService;

    public ComandaService(ComandaRepository comandaRepository, MesaService mesaService,
            ItemComandaRepository itemComandaRepository, MovimentoEstoqueService movimentoEstoqueService) {
        this.comandaRepository = comandaRepository;
        this.mesaService = mesaService;
        this.itemComandaRepository = itemComandaRepository;
        this.movimentoEstoqueService = movimentoEstoqueService;
    }

    public Comanda abrir(Long mesaId) {
        Mesa mesa = mesaService.buscarPorId(mesaId);

        comandaRepository.findByMesaIdAndStatus(mesaId, StatusComanda.ABERTA).ifPresent(c -> {
            throw new RegraDeNegocioException("Já existe uma comanda aberta para a mesa " + mesaId + ".");
        });

        Comanda comanda = new Comanda();
        comanda.setMesa(mesa);
        comanda.setStatus(StatusComanda.ABERTA);
        comanda.setDataAbertura(LocalDateTime.now());
        comanda = comandaRepository.save(comanda);

        mesa.setStatus(StatusMesa.OCUPADA);
        mesaService.salvar(mesa);

        return comanda;
    }

    public Comanda fechar(Long comandaId) {
        Comanda comanda = buscarPorId(comandaId);

        if (comanda.getStatus() == StatusComanda.FECHADA) {
            throw new RegraDeNegocioException("Comanda " + comandaId + " já está fechada.");
        }

        comanda.setStatus(StatusComanda.FECHADA);
        comanda.setDataFechamento(LocalDateTime.now());
        comanda = comandaRepository.save(comanda);

        List<ItemComanda> itens = itemComandaRepository.findByComandaId(comanda.getId());
        movimentoEstoqueService.darBaixaPorFechamentoDeComanda(comanda, itens);

        Mesa mesa = comanda.getMesa();
        mesa.setStatus(StatusMesa.LIVRE);
        mesaService.salvar(mesa);

        return comanda;
    }

    public Comanda buscarPorId(Long id) {
        return comandaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Comanda não encontrada: " + id));
    }

    public List<Comanda> listarAbertas() {
        return comandaRepository.findByStatus(StatusComanda.ABERTA);
    }
}
