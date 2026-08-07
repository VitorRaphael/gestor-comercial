package com.vitorraphael.gestor_comercial.service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.vitorraphael.gestor_comercial.exception.NaoAutorizadoException;
import com.vitorraphael.gestor_comercial.model.Funcionario;

/**
 * Guarda as sessões de login em memória (token -> funcionário), sem
 * persistir em banco. Escolha deliberada para o v1 local-first: reiniciar o
 * servidor derruba os logins e os funcionários simplesmente logam de novo —
 * tradeoff aceitável para um único estabelecimento físico.
 */
@Service
public class SessaoService {

    private final Map<String, Funcionario> sessoes = new ConcurrentHashMap<>();

    public String criarSessao(Funcionario funcionario) {
        String token = UUID.randomUUID().toString();
        sessoes.put(token, funcionario);
        return token;
    }

    public Funcionario autenticar(String token) {
        Funcionario funcionario = sessoes.get(token);
        if (funcionario == null) {
            throw new NaoAutorizadoException("Sessão inválida ou expirada. Faça login novamente.");
        }
        return funcionario;
    }

    public void encerrar(String token) {
        sessoes.remove(token);
    }
}
