# Plano — Módulo Sales Analytics + Estoque

Status: **planejamento, nada implementado ainda**. Combinado em 2026-08-10 para servir de referência quando a implementação começar.

## 1. Objetivo

Sair de "tabela crua de pedidos passados" para um dashboard analítico que ajuda o
gestor (pai do Vitor) a decidir: o que vender mais, quando escalar equipe, quando
repor insumo, e qual é a margem real (não só faturamento).

Decisão explícita: **retenção/fidelização de cliente fica fora de escopo.**
Implementar isso exigiria capturar identidade do cliente em toda venda
(telefone/CPF/cadastro), o que — mal feito — geraria dado de cliente incompleto,
inconsistente ou incorreto, que é pior que não ter o dado. Não revisitar sem
decisão explícita do Vitor.

## 2. O que já é viável com o schema atual (sem migração)

- Faturamento por período, ticket médio, nº de comandas — via `Comanda` +
  `ItemComanda` (já existem).
- Curva ABC de produtos (ranking por receita) — via `ItemComanda` + `Produto`.
- Mapa de calor dia×hora (pico de vendas) — via `Comanda.dataAbertura`
  (já existe, `LocalDateTime`, obrigatório).
- Alertas por regra simples (queda de venda de produto/categoria vs período
  anterior, ticket médio caindo) — comparação de janelas de tempo, sem dado novo.

## 3. Novidades combinadas com o Vitor (2026-08-10)

### 3.1 Custo de produto (CMV / margem)

- Campo novo `Produto.custo` (BigDecimal), ao lado do `preco` (venda) já existente.
- O sistema calcula a margem automaticamente (`preco - custo`), o Vitor **não**
  digita margem manualmente — só custo e preço de venda.

### 3.2 Módulo de Estoque (matéria-prima)

Item novo no menu lateral do console desktop: **Estoque**.

- Controla **matéria-prima**, não produto de venda (ex.: controla "pão",
  "carne 150g", "queijo fatia" — não controla "X-Burger" diretamente).
- Baixa automática por venda, via **ficha técnica** (decisão tomada: opção
  recomendada, não baixa manual).

**Modelagem (rascunho, a refinar na implementação):**

| Entidade | Campos principais |
|---|---|
| `MateriaPrima` | nome, unidade de medida (kg/g/L/un), quantidade em estoque, quantidade mínima (para alerta) |
| `FichaTecnica` | vínculo N:N `Produto` ↔ `MateriaPrima`, com `quantidadeUsada` por unidade vendida do produto |
| `MovimentoEstoque` | histórico de entrada (compra manual) e saída (venda automática / ajuste manual), com data e tipo |

**Fluxo de baixa**: ao fechar uma `Comanda` (ou ao lançar cada `ItemComanda` —
decidir na implementação qual ponto é mais correto/seguro), o sistema percorre
a ficha técnica de cada produto vendido e desconta a quantidade correspondente
de cada matéria-prima envolvida.

**Tela de Estoque deve ter:**
- Listagem de matérias-primas com saldo atual e alerta visual quando abaixo do
  mínimo.
- Ação de entrada de compra (soma ao saldo).
- Cadastro/edição da ficha técnica de cada produto (associar matérias-primas +
  quantidade).

### 3.3 Previsão de consumo por dia da semana

- Ideia: usar o histórico de `Comanda.dataAbertura` (mesma base do mapa de calor)
  para estimar consumo médio de cada matéria-prima por dia da semana (ex.:
  sábado consome historicamente mais carne que terça) e sinalizar isso na tela
  de Estoque como apoio à decisão de compra.
- Depende de acúmulo de dado real de vendas + ficha técnica funcionando antes
  de fazer sentido — **fica para depois do estoque básico estar rodando em
  produção**, não é da primeira leva.

## 4. Ordem de implementação combinada (quando começar)

1. `Produto.custo` (campo simples, baixo risco, alto retorno analítico — CMV
   passa a ser calculável).
2. `MateriaPrima`, `FichaTecnica`, `MovimentoEstoque` + baixa automática na venda.
3. Tela de Estoque no menu lateral (listagem, entrada de compra, cadastro de
   ficha técnica, alerta de mínimo).
4. Dashboard Sales Analytics propriamente dito (KPIs, curva ABC, mapa de calor,
   alertas) — consome os dados de 1–3 mas pode ser construído em paralelo com
   o que já existe hoje (itens da seção 2 não dependem de 1–3).
5. Previsão de consumo por dia da semana — depois de 2–3 estarem em produção
   com dado real acumulando.

Cada etapa acima é um "leva" separada — não implementar tudo de uma vez.

## 5. Visualização — referência rápida por tipo de dado

| Informação | Gráfico |
|---|---|
| Faturamento no tempo | Linha |
| Curva ABC / ranking de produtos | Barras horizontais |
| Mix de vendas por categoria | Pizza/donut (poucas categorias, ok aqui) |
| Pico dia×hora | Heatmap 7×24 |
| Ticket médio no tempo | Linha (separada do faturamento) |
| Comparação com período anterior | Cards com % ou barras pareadas |

## 6. Layout do dashboard (rascunho)

```
[Filtro de período]  [Comparar com período anterior]
[Cards: Faturamento | Ticket Médio | Nº Comandas | CMV %]
[Alertas inteligentes]
[Faturamento no tempo (linha)] [Curva ABC (barras)]
[Mapa de calor dia×hora]
[Mix por categoria (donut)] [Tabela detalhada, colapsada]
```

## 7. Perguntas em aberto

- Baixa de estoque no fechamento da `Comanda` ou no lançamento de cada
  `ItemComanda`? (afeta o que acontece se um item for removido antes do
  fechamento — decidir na implementação).
- O que fazer quando a baixa de estoque resultaria em saldo negativo (venda
  sem matéria-prima suficiente)? Bloquear a venda, só alertar, ou permitir e
  deixar saldo negativo como sinal de furo de estoque?
