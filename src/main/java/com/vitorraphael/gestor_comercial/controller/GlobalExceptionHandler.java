package com.vitorraphael.gestor_comercial.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.vitorraphael.gestor_comercial.dto.ErroResponse;
import com.vitorraphael.gestor_comercial.exception.AcessoNegadoException;
import com.vitorraphael.gestor_comercial.exception.NaoAutorizadoException;
import com.vitorraphael.gestor_comercial.exception.RecursoNaoEncontradoException;
import com.vitorraphael.gestor_comercial.exception.RegraDeNegocioException;

/**
 * Traduz as exceções de domínio/validação em respostas HTTP consistentes,
 * evitando vazar stacktraces para o cliente.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ErroResponse> tratarRecursoNaoEncontrado(RecursoNaoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErroResponse(ex.getMessage()));
    }

    // 422: a requisição está bem formada, mas viola uma regra de negócio do
    // domínio (ex: abrir comanda em mesa já ocupada, lançar item em comanda
    // fechada) — semanticamente mais preciso que 409 para esse tipo de erro.
    @ExceptionHandler(RegraDeNegocioException.class)
    public ResponseEntity<ErroResponse> tratarRegraDeNegocio(RegraDeNegocioException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new ErroResponse(ex.getMessage()));
    }

    @ExceptionHandler(NaoAutorizadoException.class)
    public ResponseEntity<ErroResponse> tratarNaoAutorizado(NaoAutorizadoException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErroResponse(ex.getMessage()));
    }

    @ExceptionHandler(AcessoNegadoException.class)
    public ResponseEntity<ErroResponse> tratarAcessoNegado(AcessoNegadoException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErroResponse(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> tratarValidacao(MethodArgumentNotValidException ex) {
        Map<String, String> erros = new LinkedHashMap<>();
        for (FieldError erro : ex.getBindingResult().getFieldErrors()) {
            erros.put(erro.getField(), erro.getDefaultMessage());
        }

        Map<String, Object> corpo = new LinkedHashMap<>();
        corpo.put("mensagem", "Erro de validação.");
        corpo.put("erros", erros);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(corpo);
    }
}
