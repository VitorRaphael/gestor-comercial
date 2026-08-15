package com.vitorraphael.gestor_comercial.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.vitorraphael.gestor_comercial.exception.RecursoNaoEncontradoException;
import com.vitorraphael.gestor_comercial.exception.RegraDeNegocioException;
import com.vitorraphael.gestor_comercial.model.Comanda;
import com.vitorraphael.gestor_comercial.model.Funcionario;
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
    private final FuncionarioService funcionarioService;

    public ComandaService(ComandaRepository comandaRepository, MesaService mesaService,
            ItemComandaRepository itemComandaRepository, MovimentoEstoqueService movimentoEstoqueService,
            FuncionarioService funcionarioService) {
        this.comandaRepository = comandaRepository;
        this.mesaService = mesaService;
        this.itemComandaRepository = itemComandaRepository;
        this.movimentoEstoqueService = movimentoEstoqueService;
        this.funcionarioService = funcionarioService;
    }

    public Comanda abrir(Long mesaId) {
        Mesa mesa = mesaService.buscarPorId(mesaId);

        Comanda comandaAberta = comandaRepository.findByMesaIdAndStatus(mesaId, StatusComanda.ABERTA).orElse(null);
        if (comandaAberta != null) {
            return comandaAberta;
        }

        Comanda comanda = new Comanda();
        comanda.setMesa(mesa);
        comanda.setStatus(StatusComanda.ABERTA);
        comanda.setDataAbertura(LocalDateTime.now());
        return comandaRepository.save(comanda);
    }

    public Comanda abrirBalcao() {
        Comanda balcaoVazio = comandaRepository.findByStatus(StatusComanda.ABERTA).stream()
                .filter(c -> c.getMesa() == null && !itemComandaRepository.existsByComandaId(c.getId()))
                .findFirst()
                .orElse(null);
        if (balcaoVazio != null) {
            return balcaoVazio;
        }

        Comanda comanda = new Comanda();
        comanda.setMesa(null);
        comanda.setStatus(StatusComanda.ABERTA);
        comanda.setDataAbertura(LocalDateTime.now());
        return comandaRepository.save(comanda);
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
        if (mesa != null) {
            mesa.setStatus(StatusMesa.LIVRE);
            mesaService.salvar(mesa);
        }

        return comanda;
    }

    public Comanda cancelar(Long comandaId, String motivo, String pin) {
        Comanda comanda = buscarPorId(comandaId);

        if (comanda.getStatus() != StatusComanda.ABERTA) {
            throw new RegraDeNegocioException("Só é possível cancelar uma comanda que está aberta.");
        }

        Funcionario gerente = funcionarioService.validarPinGerente(pin);
        LocalDateTime agora = LocalDateTime.now();

        List<ItemComanda> itens = itemComandaRepository.findByComandaId(comanda.getId());
        for (ItemComanda item : itens) {
            if (!item.isCancelado()) {
                item.setCancelado(true);
                item.setDataCancelamento(agora);
                item.setMotivoCancelamento(motivo);
                item.setCanceladoPor(gerente);
            }
        }
        itemComandaRepository.saveAll(itens);

        comanda.setStatus(StatusComanda.CANCELADA);
        comanda.setDataCancelamento(agora);
        comanda.setMotivoCancelamento(motivo);
        comanda.setCanceladoPor(gerente);
        comanda = comandaRepository.save(comanda);

        Mesa mesa = comanda.getMesa();
        if (mesa != null) {
            mesa.setStatus(StatusMesa.LIVRE);
            mesaService.salvar(mesa);
        }

        return comanda;
    }

    public Comanda buscarPorId(Long id) {
        return comandaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Comanda não encontrada: " + id));
    }

    public List<Comanda> listarAbertas() {
        return comandaRepository.findByStatus(StatusComanda.ABERTA).stream()
                .filter(c -> itemComandaRepository.existsByComandaId(c.getId()))
                .toList();
    }
}
