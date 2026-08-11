# Cancelamento de item e de mesa

## Contexto
Hoje não existe forma de cancelar um item lançado por engano ou cancelar
uma comanda inteira (ex: cliente foi embora sem pagar). Este spec adiciona
os dois fluxos, exigindo aprovação do gerente (PIN) e motivo, com registro
permanente para auditoria.

## Cancelamento de item
- `ItemComanda` ganha: `cancelado` (boolean, default `false`),
  `dataCancelamento`, `motivoCancelamento`, `canceladoPor` (`@ManyToOne`
  para `Funcionario`).
- Na comanda aberta, o item cancelado **continua visível na lista**,
  renderizado riscado (CSS `text-decoration: line-through`), mas é
  **excluído do cálculo do total** da comanda.
- Fluxo: botão "Cancelar" no item da comanda → modal pede o **PIN do
  gerente** → após validar, mostra campo de texto obrigatório pro
  **motivo** → confirma → grava os 4 campos.
- Item cancelado não pode ser cancelado de novo (idempotência: se
  `cancelado == true`, ação fica desabilitada).

## Cancelamento de mesa (comanda inteira)
- `StatusComanda` ganha um terceiro valor: `CANCELADA` (além de `ABERTA` e
  `FECHADA`).
- `Comanda` ganha: `dataCancelamento`, `motivoCancelamento`,
  `canceladoPor` (`@ManyToOne` para `Funcionario`).
- Fluxo: botão "Cancelar mesa" → mesmo modal de PIN do gerente + motivo →
  confirma:
  - Todos os `ItemComanda` da comanda são marcados como cancelados (mesmos
    4 campos do cancelamento de item, reaproveitando o mesmo
    `motivoCancelamento`/`canceladoPor`/`dataCancelamento`) — para que o
    relatório de cancelamentos veja cada item também, não só a comanda.
  - `Comanda.status = CANCELADA`.
  - `Mesa.status = LIVRE`.
  - A comanda sai da lista "Comandas abertas" (diferente do cancelamento de
    item, aqui a comanda inteira deixa de estar editável/visível na tela
    operacional — só aparece no relatório).
- Comanda cancelada não passa por `ComandaService.fechar()` — não há baixa
  de estoque nem geração de `Pagamento` (nada foi vendido).

## Re-autenticação (PIN do gerente)
- Novo endpoint (ou reaproveitamento do `/api/auth/login` existente) que
  **valida** um PIN sem trocar a sessão do usuário logado no momento —
  apenas confirma que o PIN pertence a um `Funcionario` com
  `perfil == GERENTE`. PIN inválido ou de perfil `ATENDENTE` → rejeita, e
  nenhum cancelamento é persistido.

## Relatório de cancelamentos (dentro de Sales Analytics)
- Nova seção "Cancelamentos", respeitando o mesmo filtro de período já
  existente na tela (últimos 7/30/90 dias).
- Tabela: data/hora, tipo (`Item` ou `Mesa inteira`), descrição (nome do
  produto, ou "Mesa {número}"), valor cancelado, motivo, funcionário que
  autorizou.
- Fonte dos dados: união de `ItemComanda` com `cancelado = true` e
  `Comanda` com `status = CANCELADA` no período.

## Fora de escopo
- Notificação/alerta em tempo real de cancelamentos.
- Limite de valor ou aprovação em múltiplos níveis.
