package com.vitorraphael.gestor_comercial.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.vitorraphael.gestor_comercial.dto.AnalyticsDashboardResponse;
import com.vitorraphael.gestor_comercial.dto.CancelamentoResponse;
import com.vitorraphael.gestor_comercial.model.Comanda;
import com.vitorraphael.gestor_comercial.model.ItemComanda;
import com.vitorraphael.gestor_comercial.model.Produto;
import com.vitorraphael.gestor_comercial.model.StatusComanda;
import com.vitorraphael.gestor_comercial.repository.ComandaRepository;
import com.vitorraphael.gestor_comercial.repository.ItemComandaRepository;

/**
 * Agrega dados de {@link Comanda}/{@link ItemComanda} fechadas em métricas de
 * venda. Não persiste nada — cálculo é feito em memória a cada chamada, dado
 * o volume esperado (um food truck, não uma rede).
 */
@Service
public class AnalyticsService {

    private static final DateTimeFormatter FORMATO_DIA = DateTimeFormatter.ISO_LOCAL_DATE;
    private static final BigDecimal LIMIAR_QUEDA_FATURAMENTO = new BigDecimal("0.20");
    private static final BigDecimal LIMIAR_QUEDA_TICKET = new BigDecimal("0.15");

    private final ComandaRepository comandaRepository;
    private final ItemComandaRepository itemComandaRepository;

    public AnalyticsService(ComandaRepository comandaRepository, ItemComandaRepository itemComandaRepository) {
        this.comandaRepository = comandaRepository;
        this.itemComandaRepository = itemComandaRepository;
    }

    public AnalyticsDashboardResponse gerarDashboard(int dias) {
        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime inicioPeriodoAtual = agora.minusDays(dias);
        LocalDateTime inicioPeriodoAnterior = inicioPeriodoAtual.minusDays(dias);

        List<Comanda> comandasAtuais = buscarComandasFechadas(inicioPeriodoAtual, agora);
        List<ItemComanda> itensAtuais = buscarItens(comandasAtuais);

        List<Comanda> comandasAnteriores = buscarComandasFechadas(inicioPeriodoAnterior, inicioPeriodoAtual);
        List<ItemComanda> itensAnteriores = buscarItens(comandasAnteriores);

        BigDecimal faturamentoTotal = somarReceita(itensAtuais);
        long numeroComandas = comandasAtuais.size();
        BigDecimal ticketMedio = numeroComandas > 0
                ? faturamentoTotal.divide(BigDecimal.valueOf(numeroComandas), 2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO;

        Double cmvPercentual = calcularCmvPercentual(itensAtuais, faturamentoTotal);

        List<AnalyticsDashboardResponse.PontoFaturamento> faturamentoPorDia = agruparFaturamentoPorDia(comandasAtuais,
                itensAtuais);
        List<AnalyticsDashboardResponse.ItemCurvaAbc> curvaAbc = calcularCurvaAbc(itensAtuais);
        List<AnalyticsDashboardResponse.PontoMapaCalor> mapaCalor = calcularMapaCalor(comandasAtuais, itensAtuais);
        List<AnalyticsDashboardResponse.ItemMixCategoria> mixPorCategoria = calcularMixPorCategoria(itensAtuais);

        List<String> alertas = gerarAlertas(faturamentoTotal, somarReceita(itensAnteriores), ticketMedio,
                comandasAnteriores.isEmpty() ? BigDecimal.ZERO
                        : somarReceita(itensAnteriores).divide(BigDecimal.valueOf(comandasAnteriores.size()), 2,
                                RoundingMode.HALF_UP));

        return new AnalyticsDashboardResponse(faturamentoTotal, ticketMedio, numeroComandas, cmvPercentual,
                faturamentoPorDia, curvaAbc, mapaCalor, mixPorCategoria, alertas);
    }

    public List<CancelamentoResponse> gerarRelatorioCancelamentos(int dias) {
        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime inicio = agora.minusDays(dias);

        List<CancelamentoResponse> relatorio = new ArrayList<>();

        List<ItemComanda> itensCancelados = itemComandaRepository
                .findByCanceladoTrueAndDataCancelamentoBetween(inicio, agora);
        for (ItemComanda item : itensCancelados) {
            relatorio.add(new CancelamentoResponse(
                    item.getDataCancelamento(),
                    "ITEM",
                    item.getProduto().getNome(),
                    receitaDoItem(item),
                    item.getMotivoCancelamento(),
                    item.getCanceladoPor() != null ? item.getCanceladoPor().getNome() : null));
        }

        List<Comanda> comandasCanceladas = comandaRepository
                .findByStatusAndDataCancelamentoBetween(StatusComanda.CANCELADA, inicio, agora);
        for (Comanda comanda : comandasCanceladas) {
            BigDecimal valorComanda = itemComandaRepository.findByComandaId(comanda.getId()).stream()
                    .map(this::receitaDoItem)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            relatorio.add(new CancelamentoResponse(
                    comanda.getDataCancelamento(),
                    "MESA",
                    "Mesa " + comanda.getMesa().getNumero(),
                    valorComanda,
                    comanda.getMotivoCancelamento(),
                    comanda.getCanceladoPor() != null ? comanda.getCanceladoPor().getNome() : null));
        }

        relatorio.sort(Comparator.comparing(CancelamentoResponse::dataHora).reversed());
        return relatorio;
    }

    private List<Comanda> buscarComandasFechadas(LocalDateTime inicio, LocalDateTime fim) {
        return comandaRepository.findByStatusAndDataFechamentoBetween(StatusComanda.FECHADA, inicio, fim);
    }

    private List<ItemComanda> buscarItens(List<Comanda> comandas) {
        if (comandas.isEmpty()) {
            return List.of();
        }
        List<Long> comandaIds = comandas.stream().map(Comanda::getId).toList();
        return itemComandaRepository.findByComandaIdIn(comandaIds);
    }

    private BigDecimal somarReceita(List<ItemComanda> itens) {
        return itens.stream()
                .map(this::receitaDoItem)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal receitaDoItem(ItemComanda item) {
        return item.getPrecoUnitario().multiply(BigDecimal.valueOf(item.getQuantidade()));
    }

    private Double calcularCmvPercentual(List<ItemComanda> itens, BigDecimal faturamentoTotal) {
        if (faturamentoTotal.compareTo(BigDecimal.ZERO) == 0) {
            return null;
        }
        BigDecimal custoTotal = BigDecimal.ZERO;
        boolean algumCustoDisponivel = false;
        for (ItemComanda item : itens) {
            BigDecimal custo = item.getProduto().getCusto();
            if (custo == null) {
                continue;
            }
            algumCustoDisponivel = true;
            custoTotal = custoTotal.add(custo.multiply(BigDecimal.valueOf(item.getQuantidade())));
        }
        if (!algumCustoDisponivel) {
            return null;
        }
        return custoTotal.divide(faturamentoTotal, 4, RoundingMode.HALF_UP).doubleValue() * 100;
    }

    private List<AnalyticsDashboardResponse.PontoFaturamento> agruparFaturamentoPorDia(List<Comanda> comandas,
            List<ItemComanda> itens) {
        Map<Long, LocalDate> diaPorComanda = new LinkedHashMap<>();
        for (Comanda comanda : comandas) {
            diaPorComanda.put(comanda.getId(), comanda.getDataFechamento().toLocalDate());
        }

        Map<LocalDate, BigDecimal> totalPorDia = new LinkedHashMap<>();
        for (ItemComanda item : itens) {
            LocalDate dia = diaPorComanda.get(item.getComanda().getId());
            if (dia == null) {
                continue;
            }
            totalPorDia.merge(dia, receitaDoItem(item), BigDecimal::add);
        }

        return totalPorDia.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> new AnalyticsDashboardResponse.PontoFaturamento(e.getKey().format(FORMATO_DIA), e.getValue()))
                .toList();
    }

    private List<AnalyticsDashboardResponse.ItemCurvaAbc> calcularCurvaAbc(List<ItemComanda> itens) {
        Map<String, BigDecimal> receitaPorProduto = new LinkedHashMap<>();
        for (ItemComanda item : itens) {
            receitaPorProduto.merge(item.getProduto().getNome(), receitaDoItem(item), BigDecimal::add);
        }
        return receitaPorProduto.entrySet().stream()
                .sorted(Map.Entry.<String, BigDecimal>comparingByValue().reversed())
                .limit(10)
                .map(e -> new AnalyticsDashboardResponse.ItemCurvaAbc(e.getKey(), e.getValue()))
                .toList();
    }

    private List<AnalyticsDashboardResponse.PontoMapaCalor> calcularMapaCalor(List<Comanda> comandas,
            List<ItemComanda> itens) {
        Map<Long, Comanda> comandaPorId = new LinkedHashMap<>();
        for (Comanda comanda : comandas) {
            comandaPorId.put(comanda.getId(), comanda);
        }

        Map<String, Long> contagem = new LinkedHashMap<>();
        for (ItemComanda item : itens) {
            Comanda comanda = comandaPorId.get(item.getComanda().getId());
            if (comanda == null) {
                continue;
            }
            LocalDateTime abertura = comanda.getDataAbertura();
            int diaSemanaJs = diaSemanaEstiloJs(abertura.getDayOfWeek());
            int hora = abertura.getHour();
            contagem.merge(diaSemanaJs + ":" + hora, (long) item.getQuantidade(), Long::sum);
        }

        List<AnalyticsDashboardResponse.PontoMapaCalor> pontos = new ArrayList<>();
        for (Map.Entry<String, Long> e : contagem.entrySet()) {
            String[] partes = e.getKey().split(":");
            pontos.add(new AnalyticsDashboardResponse.PontoMapaCalor(Integer.parseInt(partes[0]),
                    Integer.parseInt(partes[1]), e.getValue()));
        }
        return pontos;
    }

    private int diaSemanaEstiloJs(DayOfWeek diaSemana) {
        return diaSemana.getValue() % 7;
    }

    private List<AnalyticsDashboardResponse.ItemMixCategoria> calcularMixPorCategoria(List<ItemComanda> itens) {
        Map<String, BigDecimal> receitaPorCategoria = new LinkedHashMap<>();
        for (ItemComanda item : itens) {
            Produto produto = item.getProduto();
            String categoriaNome = produto.getCategoria().getNome();
            receitaPorCategoria.merge(categoriaNome, receitaDoItem(item), BigDecimal::add);
        }
        return receitaPorCategoria.entrySet().stream()
                .sorted(Map.Entry.<String, BigDecimal>comparingByValue().reversed())
                .map(e -> new AnalyticsDashboardResponse.ItemMixCategoria(e.getKey(), e.getValue()))
                .toList();
    }

    private List<String> gerarAlertas(BigDecimal faturamentoAtual, BigDecimal faturamentoAnterior,
            BigDecimal ticketMedioAtual, BigDecimal ticketMedioAnterior) {
        List<String> alertas = new ArrayList<>();

        if (faturamentoAnterior.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal variacao = faturamentoAtual.subtract(faturamentoAnterior)
                    .divide(faturamentoAnterior, 4, RoundingMode.HALF_UP);
            if (variacao.compareTo(LIMIAR_QUEDA_FATURAMENTO.negate()) < 0) {
                alertas.add(String.format("Faturamento caiu %.0f%% em relação ao período anterior.",
                        variacao.abs().doubleValue() * 100));
            }
        }

        if (ticketMedioAnterior.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal variacao = ticketMedioAtual.subtract(ticketMedioAnterior)
                    .divide(ticketMedioAnterior, 4, RoundingMode.HALF_UP);
            if (variacao.compareTo(LIMIAR_QUEDA_TICKET.negate()) < 0) {
                alertas.add(String.format("Ticket médio caiu %.0f%% em relação ao período anterior.",
                        variacao.abs().doubleValue() * 100));
            }
        }

        return alertas;
    }
}
