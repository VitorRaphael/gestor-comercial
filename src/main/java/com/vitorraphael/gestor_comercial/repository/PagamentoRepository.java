package com.vitorraphael.gestor_comercial.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vitorraphael.gestor_comercial.model.FormaPagamento;
import com.vitorraphael.gestor_comercial.model.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

    List<Pagamento> findByComandaId(Long comandaId);

    List<Pagamento> findByFormaPagamentoAndDataHoraGreaterThanEqual(FormaPagamento formaPagamento, LocalDateTime desde);

    List<Pagamento> findByFormaPagamentoInAndDataHoraGreaterThanEqual(List<FormaPagamento> formasPagamento, LocalDateTime desde);

    List<Pagamento> findByFormaPagamento(FormaPagamento formaPagamento);

    List<Pagamento> findByFormaPagamentoAndFuncionarioConsumidorIdOrderByDataHoraAsc(FormaPagamento formaPagamento,
            Long funcionarioConsumidorId);
}
