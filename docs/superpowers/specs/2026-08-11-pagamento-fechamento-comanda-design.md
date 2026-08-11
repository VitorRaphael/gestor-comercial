# Pagamento no fechamento de comanda

## Contexto
Hoje `ComandaService.fechar()` marca a comanda como `FECHADA` sem registrar
como ela foi paga. `CaixaService.calcularSaldoEsperado` também não considera
receita de vendas (comentário explícito no código: "fica para uma fase
futura"). Este spec implementa essa fase: pagamento por múltiplas formas no
fechamento, e a integração dessa receita com o saldo do caixa.

Impressão de notinha fica fora de escopo (sem impressora ESC/POS real
plugada ainda — Fase 3 do projeto é log-simulado).

## Entidade nova: `Pagamento`
- `id` (PK)
- `comanda` (`@ManyToOne` para `Comanda`)
- `formaPagamento` (enum `FormaPagamento`: `CREDITO`, `DEBITO`, `DINHEIRO`, `PIX`)
- `valor` (`BigDecimal`, valor efetivamente lançado, sem contar troco)
- `troco` (`BigDecimal`, nullable — só preenchido quando `formaPagamento == DINHEIRO` e o valor recebido excede o restante)
- `dataHora` (`LocalDateTime`)

Uma comanda pode ter N `Pagamento` (suporta split entre formas).

## Regras de negócio
1. `Comanda.fechar()` deixa de ser chamado diretamente pelo botão — o botão
   "Fechar comanda" agora abre o modal de pagamento.
2. O modal mostra: total da conta, total já pago (soma dos `Pagamento`
   registrados nesta sessão de fechamento), e o **restante**.
3. Ao registrar um pagamento:
   - `DINHEIRO`: o valor recebido pode ser maior que o restante. Nesse caso
     `valor pago = restante`, e `troco = valor recebido - restante`.
   - `CREDITO` / `DEBITO` / `PIX`: o valor não pode exceder o restante — a
     API rejeita com `RegraDeNegocioException` se exceder. Sem troco.
   - Valor deve ser > 0.
4. Depois de cada pagamento parcial, se o restante > 0, o modal continua
   aberto pedindo a próxima forma de pagamento (loop).
5. A comanda só é efetivamente fechada (`StatusComanda.FECHADA`, baixa de
   estoque, mesa liberada) quando a soma dos pagamentos bate **exatamente**
   com o total da conta (restante == 0). Nunca fecha com sobra ou falta.
6. Se o usuário cancelar o modal a qualquer momento antes de bater o total,
   nenhum `Pagamento` é persistido (transação só é committed no fechamento
   final) e a comanda permanece aberta como estava.

## Endpoint(s)
- Novo: `POST /api/comandas/{id}/pagamentos` — registra um pagamento
  parcial. Retorna o estado atualizado (total pago, restante). Quando o
  restante chega a zero, o próprio endpoint dispara o fechamento da comanda
  (reaproveitando `ComandaService.fechar()`).
- A transação de registrar N pagamentos + fechar precisa ser atômica do
  ponto de vista do usuário, mas cada pagamento parcial é persistido
  imediatamente (não é preciso reverter em caso de queda de energia entre
  parcelas — isso é aceitável para v1, consistente com sessão em memória já
  aceita no projeto).

## Integração com Caixa
- `CaixaService.calcularSaldoEsperado` passa a somar todos os `Pagamento`
  com `formaPagamento == DINHEIRO` cujas comandas fecharam durante a janela
  do caixa aberto (dataHora do pagamento >= dataAbertura do caixa).
- Cartão (crédito/débito) e Pix **não** afetam o saldo físico do caixa.
- Modal "Fechar caixa" ganha um segundo campo: **"Vendido nas
  maquininhas"** (soma de crédito + débito + pix no período do caixa),
  ao lado do já existente "Valor contado no fechamento" (dinheiro).
- Botão **"Completar"** (temporário, fase de testes): preenche os dois
  campos automaticamente com os valores que o sistema calculou (saldo
  esperado em dinheiro + total cartão/pix). Marcado no código com
  `// TODO remover em produção` para ser removido quando a Fase 6 (piloto
  real) começar.

## Fora de escopo
- Impressão de notinha/comprovante.
- Gráfico de mix de vendas por forma de pagamento no Sales Analytics.
