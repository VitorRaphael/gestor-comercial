package com.vitorraphael.gestor_comercial.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.vitorraphael.gestor_comercial.exception.NaoAutorizadoException;
import com.vitorraphael.gestor_comercial.exception.RecursoNaoEncontradoException;
import com.vitorraphael.gestor_comercial.exception.RegraDeNegocioException;
import com.vitorraphael.gestor_comercial.model.Funcionario;
import com.vitorraphael.gestor_comercial.model.PerfilFuncionario;
import com.vitorraphael.gestor_comercial.repository.FuncionarioRepository;

@ExtendWith(MockitoExtension.class)
class FuncionarioServiceTest {

    @Mock
    private FuncionarioRepository funcionarioRepository;

    // instância real: é lógica de hash pura, mais simples do que mockar hash/salt
    private final PinHashService pinHashService = new PinHashService();

    private FuncionarioService funcionarioService;

    @BeforeEach
    void setUp() {
        funcionarioService = new FuncionarioService(funcionarioRepository, pinHashService);
    }

    private Funcionario criarFuncionarioComPin(Long id, String pin, PerfilFuncionario perfil) {
        String salt = pinHashService.gerarSalt();
        Funcionario funcionario = new Funcionario();
        funcionario.setId(id);
        funcionario.setNome("Funcionário " + id);
        funcionario.setPinSalt(salt);
        funcionario.setPinHash(pinHashService.hash(pin, salt));
        funcionario.setPerfil(perfil);
        funcionario.setAtivo(true);
        return funcionario;
    }

    @Test
    void deveCriarFuncionarioQuandoPinNaoEstaEmUso() {
        when(funcionarioRepository.findByAtivoTrue()).thenReturn(List.of());
        when(funcionarioRepository.save(any(Funcionario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Funcionario funcionario = funcionarioService.criar("Ana", "1234", PerfilFuncionario.ATENDENTE);

        assertThat(funcionario.getNome()).isEqualTo("Ana");
        assertThat(funcionario.getPerfil()).isEqualTo(PerfilFuncionario.ATENDENTE);
        assertThat(funcionario.isAtivo()).isTrue();
        assertThat(pinHashService.confere("1234", funcionario.getPinSalt(), funcionario.getPinHash())).isTrue();
    }

    @Test
    void deveLancarExcecaoAoCriarFuncionarioComPinJaUsadoPorOutroAtivo() {
        Funcionario existente = criarFuncionarioComPin(1L, "1234", PerfilFuncionario.ATENDENTE);
        when(funcionarioRepository.findByAtivoTrue()).thenReturn(List.of(existente));

        assertThrows(RegraDeNegocioException.class,
                () -> funcionarioService.criar("Bruno", "1234", PerfilFuncionario.ATENDENTE));
    }

    @Test
    void deveAutenticarPorPinCorreto() {
        Funcionario existente = criarFuncionarioComPin(1L, "5678", PerfilFuncionario.GERENTE);
        when(funcionarioRepository.findByAtivoTrue()).thenReturn(List.of(existente));

        Funcionario autenticado = funcionarioService.autenticarPorPin("5678");

        assertThat(autenticado).isEqualTo(existente);
    }

    @Test
    void deveLancarExcecaoAoAutenticarComPinIncorreto() {
        Funcionario existente = criarFuncionarioComPin(1L, "5678", PerfilFuncionario.GERENTE);
        when(funcionarioRepository.findByAtivoTrue()).thenReturn(List.of(existente));

        assertThrows(NaoAutorizadoException.class, () -> funcionarioService.autenticarPorPin("0000"));
    }

    @Test
    void deveLancarExcecaoAoBuscarFuncionarioComIdInexistente() {
        when(funcionarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class, () -> funcionarioService.buscarPorId(99L));
    }

    @Test
    void deveDesativarFuncionario() {
        Funcionario existente = criarFuncionarioComPin(1L, "1234", PerfilFuncionario.ATENDENTE);
        when(funcionarioRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(funcionarioRepository.save(any(Funcionario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Funcionario desativado = funcionarioService.desativar(1L);

        assertThat(desativado.isAtivo()).isFalse();
    }
}
