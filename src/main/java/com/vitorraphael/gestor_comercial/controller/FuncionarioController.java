package com.vitorraphael.gestor_comercial.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vitorraphael.gestor_comercial.dto.FuncionarioRequest;
import com.vitorraphael.gestor_comercial.dto.FuncionarioResponse;
import com.vitorraphael.gestor_comercial.model.Funcionario;
import com.vitorraphael.gestor_comercial.security.ExigeGerente;
import com.vitorraphael.gestor_comercial.service.FuncionarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/funcionarios")
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @ExigeGerente
    @PostMapping
    public ResponseEntity<FuncionarioResponse> criar(@Valid @RequestBody FuncionarioRequest request) {
        Funcionario funcionario = funcionarioService.criar(request.nome(), request.pin(), request.perfil());
        return ResponseEntity.status(HttpStatus.CREATED).body(FuncionarioResponse.de(funcionario));
    }

    @ExigeGerente
    @GetMapping
    public ResponseEntity<List<FuncionarioResponse>> listar() {
        List<FuncionarioResponse> funcionarios = funcionarioService.listarAtivos().stream()
                .map(FuncionarioResponse::de)
                .toList();
        return ResponseEntity.ok(funcionarios);
    }

    @ExigeGerente
    @PatchMapping("/{id}/desativar")
    public ResponseEntity<FuncionarioResponse> desativar(@PathVariable Long id) {
        Funcionario funcionario = funcionarioService.desativar(id);
        return ResponseEntity.ok(FuncionarioResponse.de(funcionario));
    }
}
