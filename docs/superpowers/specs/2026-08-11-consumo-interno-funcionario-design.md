# Consumo Interno de funcionário

## Contexto
Funcionários às vezes consomem produtos do próprio food truck (ex: um
lanche durante o turno). Hoje isso não tem rastreamento nenhum. Este spec
trata o consumo interno como uma forma de pagamento a mais (débito contra o
funcionário, não entrada de caixa), com um dashboard gerencial pra
consolidar e quitar o saldo devedor na folha.

Depende do Spec 1 (Pagamento no fechamento de comanda) e do Spec 2
(re-autenticação com PIN do gerente).

## Consumo Interno como forma de pagamento
- `FormaPagamento` ganha um 5º valor: `CONSUMO_INTERNO` (além de
  `CREDITO`, `DEBITO`, `DINHEIRO`, `PIX`).
- `Pagamento` ganha `funcionarioConsumidor` (`@ManyToOne` para
  `Funcionario`, nullable — só preenchido quando
  `formaPagamento == CONSUMO_INTERNO`).
- Fluxo no modal de pagamento (Spec 1): ao escolher "Consumo Interno" →
  pede **PIN do gerente** (mesmo mecanismo de re-autenticação do Spec 2) →
  após validar, pede pra selecionar **qual funcionário** está consumindo →
  valor não pode exceder o restante da conta (sem troco, mesma regra de
  crédito/débito/pix).
- Consumo interno **não afeta o saldo do caixa** (só `DINHEIRO` afeta,
  regra já definida no Spec 1).
- A baixa de estoque acontece normalmente — a comanda ainda passa pelo
  `ComandaService.fechar()` padrão quando o restante chega a zero.

## Saldo devedor (suporta quitação parcial)
- `Pagamento` ganha `valorQuitado` (`BigDecimal`, default `0`) — só
  relevante quando `formaPagamento == CONSUMO_INTERNO`.
- Saldo devedor de um funcionário = soma de `(valor - valorQuitado)` de
  todos os `Pagamento` dele com forma `CONSUMO_INTERNO`.
- Nova entidade `QuitacaoConsumo`: `id`, `funcionario` (FK), `valor`
  (`BigDecimal`, valor quitado nessa ação), `dataHora`, `autorizadoPor`
  (FK `Funcionario`, o gerente que autorizou) — um registro por ação de
  quitação, imutável, para auditoria permanente.

## Fluxo de quitação
1. Gerente abre o dashboard de saldo devedor, vê a lista de funcionários
   com saldo > 0.
2. Seleciona um funcionário, digita o **valor a quitar** — pode ser igual
   ao total pendente (quita tudo) ou menor (parcial).
3. Sistema aplica esse valor em ordem **FIFO** sobre os `Pagamento`
   pendentes mais antigos daquele funcionário, incrementando
   `valorQuitado` de cada um até esgotar o valor informado (o último
   registro tocado pode ficar parcialmente quitado).
4. Grava um `QuitacaoConsumo` com o valor total da ação.
5. Nada é apagado nem fisicamente "zerado" — o saldo exibido é sempre
   `valor - valorQuitado` calculado on-the-fly, então o histórico completo
   de consumos e quitações permanece no banco para sempre.

## Módulo gerencial (dashboard)
- Nova aba/seção dentro da tela **Funcionários** já existente: "Saldo
  devedor".
- Lista: nome do funcionário, saldo devedor atual, botão "Quitar" (abre
  modal com o valor sugerido = saldo total, editável para parcial).
- Detalhe expandido por funcionário: lista de consumos individuais (data,
  produto(s)/mesa de origem, valor, quanto já foi quitado daquele
  registro) e histórico de quitações.

## Fora de escopo
- Desconto automático do saldo devedor na folha de pagamento (é o gerente
  quem aciona a quitação manualmente).
- Limite de crédito por funcionário (bloquear consumo se saldo devedor
  passar de X).
