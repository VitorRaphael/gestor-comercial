# Graph Report - .  (2026-08-07)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 133 nodes · 172 edges · 11 communities (9 shown, 2 thin omitted)
- Extraction: 99% EXTRACTED · 1% INFERRED · 0% AMBIGUOUS · INFERRED: 1 edges (avg confidence: 0.95)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `da3f82d0`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- ItemComanda
- Roadmap por Fases (1 a 6)
- Gestor Comercial (sistema PDV)
- Comanda
- Mesa
- Categoria
- mvnw
- JpaRepository
- GestorComercialApplicationTests.java
- GestorComercialApplication
- com.vitorraphael:gestor-comercial

## God Nodes (most connected - your core abstractions)
1. `Comanda` - 19 edges
2. `ItemComanda` - 18 edges
3. `Produto` - 18 edges
4. `Mesa` - 14 edges
5. `Categoria` - 11 edges
6. `Gestor Comercial (sistema PDV)` - 8 edges
7. `Roadmap por Fases (1 a 6)` - 7 edges
8. `Arquitetura Local-first (não cloud-first)` - 6 edges
9. `StatusComanda` - 6 edges
10. `StatusMesa` - 6 edges

## Surprising Connections (you probably didn't know these)
- `gestor-comercial (projeto, dono deste grafo)` --conceptually_related_to--> `Gestor Comercial (sistema PDV)`  [INFERRED]
  CLAUDE.md → PLANTA_PROJETO.md
- `Produto` --references--> `Categoria`  [EXTRACTED]
  src/main/java/com/vitorraphael/gestor_comercial/model/Produto.java → src/main/java/com/vitorraphael/gestor_comercial/model/Categoria.java
- `Comanda` --references--> `Mesa`  [EXTRACTED]
  src/main/java/com/vitorraphael/gestor_comercial/model/Comanda.java → src/main/java/com/vitorraphael/gestor_comercial/model/Mesa.java
- `ItemComanda` --references--> `Comanda`  [EXTRACTED]
  src/main/java/com/vitorraphael/gestor_comercial/model/ItemComanda.java → src/main/java/com/vitorraphael/gestor_comercial/model/Comanda.java
- `ComandaRepository` --references--> `Comanda`  [EXTRACTED]
  src/main/java/com/vitorraphael/gestor_comercial/repository/ComandaRepository.java → src/main/java/com/vitorraphael/gestor_comercial/model/Comanda.java

## Import Cycles
- None detected.

## Hyperedges (group relationships)
- **Validação de mercado da arquitetura local-first** — planta_projeto_arquitetura_local_first, planta_projeto_consumer_concorrente, planta_projeto_toast_pos [EXTRACTED 1.00]
- **Roteamento de impressão por categoria entre as 3 impressoras reais** — planta_projeto_tabela_roteamento_impressoras, planta_projeto_impressora_caixa, planta_projeto_impressora_trailer_1, planta_projeto_impressora_trailer_2 [EXTRACTED 1.00]
- **Roadmap de fases mapeado aos módulos planejados** — planta_projeto_roadmap_fases, planta_projeto_modulo_cardapio, planta_projeto_modulo_mesas_comandas, planta_projeto_modulo_caixa, planta_projeto_modulo_impressao, planta_projeto_interface_atendente_pwa, planta_projeto_modulo_usuarios_funcionarios, planta_projeto_piloto_grand_chef_pro [EXTRACTED 1.00]

## Communities (11 total, 2 thin omitted)

### Community 0 - "ItemComanda"
Cohesion: 0.10
Nodes (4): ItemComanda, Entity, Entity, Produto

### Community 1 - "Roadmap por Fases (1 a 6)"
Cohesion: 0.11
Nodes (19): Entidade Comanda, Entidade ItemComanda, Entidade Mesa, Entidade Produto, escpos-coffee (biblioteca Java), Grand Chef Pro (sistema pago concorrente a substituir), Impressão centralizada via ESC/POS, Impressora do caixa (fechamento de conta, bebidas) (+11 more)

### Community 2 - "Gestor Comercial (sistema PDV)"
Cohesion: 0.12
Nodes (18): gestor-comercial (projeto, dono deste grafo), graphify-out/GRAPH_REPORT.md, graphify-out/ (diretório do grafo), ifood-merchant-api (projeto irmão, regra já estabelecida), Regra Inegociável — Graphify, Arquitetura Local-first (não cloud-first), Banco de dados SQLite, Consumer (concorrente nacional, validação de mercado) (+10 more)

### Community 3 - "Comanda"
Cohesion: 0.14
Nodes (5): Comanda, Entity, StatusComanda, ABERTA, FECHADA

### Community 4 - "Mesa"
Cohesion: 0.16
Nodes (5): Entity, Mesa, StatusMesa, LIVRE, OCUPADA

### Community 5 - "Categoria"
Cohesion: 0.22
Nodes (3): Categoria, Entity, CategoriaRepository

### Community 6 - "mvnw"
Cohesion: 0.33
Nodes (6): mvnw script, clean(), die(), exec_maven(), set_java_home(), verbose()

### Community 7 - "JpaRepository"
Cohesion: 0.33
Nodes (5): JpaRepository, ComandaRepository, ItemComandaRepository, MesaRepository, ProdutoRepository

### Community 8 - "GestorComercialApplicationTests.java"
Cohesion: 0.60
Nodes (3): SpringBootTest, GestorComercialApplicationTests, Test

## Knowledge Gaps
- **23 isolated node(s):** `com.vitorraphael:gestor-comercial`, `graphify-out/ (diretório do grafo)`, `graphify-out/GRAPH_REPORT.md`, `ifood-merchant-api (projeto irmão, regra já estabelecida)`, `Food truck do pai do Vitor (negócio real)` (+18 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **2 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `Comanda` connect `Comanda` to `ItemComanda`, `Mesa`, `JpaRepository`?**
  _High betweenness centrality (0.172) - this node is a cross-community bridge._
- **Why does `ItemComanda` connect `ItemComanda` to `Comanda`, `JpaRepository`?**
  _High betweenness centrality (0.167) - this node is a cross-community bridge._
- **Why does `Produto` connect `ItemComanda` to `Categoria`, `JpaRepository`?**
  _High betweenness centrality (0.129) - this node is a cross-community bridge._
- **What connects `com.vitorraphael:gestor-comercial`, `graphify-out/ (diretório do grafo)`, `graphify-out/GRAPH_REPORT.md` to the rest of the system?**
  _23 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `ItemComanda` be split into smaller, more focused modules?**
  _Cohesion score 0.09782608695652174 - nodes in this community are weakly interconnected._
- **Should `Roadmap por Fases (1 a 6)` be split into smaller, more focused modules?**
  _Cohesion score 0.1111111111111111 - nodes in this community are weakly interconnected._
- **Should `Gestor Comercial (sistema PDV)` be split into smaller, more focused modules?**
  _Cohesion score 0.12418300653594772 - nodes in this community are weakly interconnected._