package com.vitorraphael.gestor_comercial.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vitorraphael.gestor_comercial.exception.NaoAutorizadoException;
import com.vitorraphael.gestor_comercial.exception.RecursoNaoEncontradoException;
import com.vitorraphael.gestor_comercial.exception.RegraDeNegocioException;
import com.vitorraphael.gestor_comercial.model.Funcionario;
import com.vitorraphael.gestor_comercial.model.PerfilFuncionario;
import com.vitorraphael.gestor_comercial.repository.FuncionarioRepository;

@Service
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final PinHashService pinHashService;

    public FuncionarioService(FuncionarioRepository funcionarioRepository, PinHashService pinHashService) {
        this.funcionarioRepository = funcionarioRepository;
        this.pinHashService = pinHashService;
    }

    public Funcionario criar(String nome, String pin, PerfilFuncionario perfil) {
        boolean pinJaUsado = funcionarioRepository.findByAtivoTrue().stream()
                .anyMatch(f -> pinHashService.confere(pin, f.getPinSalt(), f.getPinHash()));
        if (pinJaUsado) {
            throw new RegraDeNegocioException("Este PIN já está em uso por outro funcionário ativo.");
        }

        String salt = pinHashService.gerarSalt();
        Funcionario funcionario = new Funcionario();
        funcionario.setNome(nome);
        funcionario.setPinSalt(salt);
        funcionario.setPinHash(pinHashService.hash(pin, salt));
        funcionario.setPerfil(perfil);
        funcionario.setAtivo(true);
        return funcionarioRepository.save(funcionario);
    }

    public List<Funcionario> listarAtivos() {
        return funcionarioRepository.findByAtivoTrue();
    }

    public Funcionario buscarPorId(Long id) {
        return funcionarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Funcionário não encontrado: " + id));
    }

    public Funcionario desativar(Long id) {
        Funcionario funcionario = buscarPorId(id);
        funcionario.setAtivo(false);
        return funcionarioRepository.save(funcionario);
    }

    public Funcionario autenticarPorPin(String pin) {
        return funcionarioRepository.findByAtivoTrue().stream()
                .filter(f -> pinHashService.confere(pin, f.getPinSalt(), f.getPinHash()))
                .findFirst()
                .orElseThrow(() -> new NaoAutorizadoException("PIN inválido."));
    }
}
