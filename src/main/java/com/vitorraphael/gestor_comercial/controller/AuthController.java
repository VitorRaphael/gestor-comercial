package com.vitorraphael.gestor_comercial.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vitorraphael.gestor_comercial.dto.LoginRequest;
import com.vitorraphael.gestor_comercial.dto.LoginResponse;
import com.vitorraphael.gestor_comercial.model.Funcionario;
import com.vitorraphael.gestor_comercial.service.FuncionarioService;
import com.vitorraphael.gestor_comercial.service.SessaoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final FuncionarioService funcionarioService;
    private final SessaoService sessaoService;

    public AuthController(FuncionarioService funcionarioService, SessaoService sessaoService) {
        this.funcionarioService = funcionarioService;
        this.sessaoService = sessaoService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        Funcionario funcionario = funcionarioService.autenticarPorPin(request.pin());
        String token = sessaoService.criarSessao(funcionario);
        return ResponseEntity.ok(new LoginResponse(token, funcionario.getId(), funcionario.getNome(),
                funcionario.getPerfil().name()));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String authorization) {
        sessaoService.encerrar(authorization.replaceFirst("(?i)^Bearer ", "").trim());
        return ResponseEntity.noContent().build();
    }
}
