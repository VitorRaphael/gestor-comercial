package com.vitorraphael.gestor_comercial.security;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.vitorraphael.gestor_comercial.exception.AcessoNegadoException;
import com.vitorraphael.gestor_comercial.exception.NaoAutorizadoException;
import com.vitorraphael.gestor_comercial.model.Funcionario;
import com.vitorraphael.gestor_comercial.model.PerfilFuncionario;
import com.vitorraphael.gestor_comercial.service.SessaoService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Exige um token de sessão válido (header "Authorization: Bearer <token>")
 * em toda rota /api/** exceto /api/auth/login, e bloqueia endpoints
 * marcados com {@link ExigeGerente} para quem não é GERENTE.
 */
@Component
public class AutenticacaoInterceptor implements HandlerInterceptor {

    public static final String ATRIBUTO_FUNCIONARIO = "funcionarioLogado";

    private final SessaoService sessaoService;

    public AutenticacaoInterceptor(SessaoService sessaoService) {
        this.sessaoService = sessaoService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }

        String cabecalho = request.getHeader("Authorization");
        if (cabecalho == null || !cabecalho.regionMatches(true, 0, "Bearer ", 0, 7)) {
            throw new NaoAutorizadoException("Faça login para continuar.");
        }

        String token = cabecalho.substring(7).trim();
        Funcionario funcionario = sessaoService.autenticar(token);

        if (handlerMethod.hasMethodAnnotation(ExigeGerente.class) && funcionario.getPerfil() != PerfilFuncionario.GERENTE) {
            throw new AcessoNegadoException("Apenas gerentes podem realizar esta ação.");
        }

        request.setAttribute(ATRIBUTO_FUNCIONARIO, funcionario);
        return true;
    }
}
