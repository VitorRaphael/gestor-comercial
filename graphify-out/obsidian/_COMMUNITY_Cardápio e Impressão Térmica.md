---
type: community
cohesion: 0.20
members: 10
---

# Cardápio e Impressão Térmica

**Cohesion:** 0.20 - loosely connected
**Members:** 10 nodes

## Members
- [[Entidade Produto]] - concept - PLANTA_PROJETO.md
- [[Impressora do caixa (fechamento de conta, bebidas)]] - concept - PLANTA_PROJETO.md
- [[Impressora do trailer 1 (hambúrguer, espetinhos, porções)]] - concept - PLANTA_PROJETO.md
- [[Impressora do trailer 2 (caipirinha, açaí, sorvete)]] - concept - PLANTA_PROJETO.md
- [[Impressão centralizada via ESCPOS]] - concept - PLANTA_PROJETO.md
- [[Módulo Cardápio]] - concept - PLANTA_PROJETO.md
- [[Módulo Impressão]] - concept - PLANTA_PROJETO.md
- [[Servidor Spring Boot (caixa)]] - concept - PLANTA_PROJETO.md
- [[Tabela de roteamento de impressão por categoria]] - concept - PLANTA_PROJETO.md
- [[escpos-coffee (biblioteca Java)]] - concept - PLANTA_PROJETO.md

## Live Query (requires Dataview plugin)

```dataview
TABLE source_file, type FROM #community/Cardpio_e_Impresso_Trmica
SORT file.name ASC
```

## Connections to other communities
- 2 edges to [[_COMMUNITY_Mesas, Caixa e Roadmap]]
- 1 edge to [[_COMMUNITY_Visão e Arquitetura do Projeto]]

## Top bridge nodes
- [[Módulo Cardápio]] - degree 3, connects to 1 community
- [[Módulo Impressão]] - degree 3, connects to 1 community
- [[Servidor Spring Boot (caixa)]] - degree 2, connects to 1 community