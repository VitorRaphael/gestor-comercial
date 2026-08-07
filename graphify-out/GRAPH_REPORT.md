# Graph Report - .  (2026-08-07)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 368 nodes · 716 edges · 16 communities (14 shown, 2 thin omitted)
- Extraction: 94% EXTRACTED · 6% INFERRED · 0% AMBIGUOUS · INFERRED: 45 edges (avg confidence: 0.8)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `a7d33b87`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- Comanda
- Produto
- Gestor Comercial (sistema PDV)
- ItemComanda
- ComandaServiceTest.java
- ProdutoController.java
- MesaController.java
- GlobalExceptionHandler.java
- ItemComandaController.java
- ItemComandaServiceTest.java
- CategoriaController.java
- mvnw
- GestorComercialApplicationTests.java
- GestorComercialApplication
- StatusMesa
- com.vitorraphael:gestor-comercial

## God Nodes (most connected - your core abstractions)
1. `Comanda` - 29 edges
2. `Produto` - 26 edges
3. `ItemComanda` - 24 edges
4. `Mesa` - 23 edges
5. `Categoria` - 19 edges
6. `ItemComandaServiceTest` - 16 edges
7. `ComandaServiceTest` - 12 edges
8. `ComandaService` - 11 edges
9. `ProdutoService` - 11 edges
10. `MesaService` - 10 edges

## Surprising Connections (you probably didn't know these)
- `gestor-comercial (projeto, dono deste grafo)` --conceptually_related_to--> `Gestor Comercial (sistema PDV)`  [INFERRED]
  CLAUDE.md → PLANTA_PROJETO.md
- `ProdutoRepository` --references--> `Produto`  [EXTRACTED]
  src/main/java/com/vitorraphael/gestor_comercial/repository/ProdutoRepository.java → src/main/java/com/vitorraphael/gestor_comercial/model/Produto.java
- `ItemComanda` --references--> `Comanda`  [EXTRACTED]
  src/main/java/com/vitorraphael/gestor_comercial/model/ItemComanda.java → src/main/java/com/vitorraphael/gestor_comercial/model/Comanda.java
- `ItemComanda` --references--> `Produto`  [EXTRACTED]
  src/main/java/com/vitorraphael/gestor_comercial/model/ItemComanda.java → src/main/java/com/vitorraphael/gestor_comercial/model/Produto.java
- `ItemComandaService` --references--> `ComandaService`  [EXTRACTED]
  src/main/java/com/vitorraphael/gestor_comercial/service/ItemComandaService.java → src/main/java/com/vitorraphael/gestor_comercial/service/ComandaService.java

## Import Cycles
- None detected.

## Hyperedges (group relationships)
- **Validação de mercado da arquitetura local-first** — planta_projeto_arquitetura_local_first, planta_projeto_consumer_concorrente, planta_projeto_toast_pos [EXTRACTED 1.00]
- **Roteamento de impressão por categoria entre as 3 impressoras reais** — planta_projeto_tabela_roteamento_impressoras, planta_projeto_impressora_caixa, planta_projeto_impressora_trailer_1, planta_projeto_impressora_trailer_2 [EXTRACTED 1.00]
- **Roadmap de fases mapeado aos módulos planejados** — planta_projeto_roadmap_fases, planta_projeto_modulo_cardapio, planta_projeto_modulo_mesas_comandas, planta_projeto_modulo_caixa, planta_projeto_modulo_impressao, planta_projeto_interface_atendente_pwa, planta_projeto_modulo_usuarios_funcionarios, planta_projeto_piloto_grand_chef_pro [EXTRACTED 1.00]

## Communities (16 total, 2 thin omitted)

### Community 0 - "Comanda"
Cohesion: 0.07
Nodes (20): RecursoNaoEncontradoException, RegraDeNegocioException, Comanda, Entity, Override, StatusComanda, Entity, Override (+12 more)

### Community 1 - "Produto"
Cohesion: 0.08
Nodes (12): ProdutoRepository, Categoria, Entity, Override, Entity, Override, Produto, CategoriaRepository (+4 more)

### Community 2 - "Gestor Comercial (sistema PDV)"
Cohesion: 0.06
Nodes (37): gestor-comercial (projeto, dono deste grafo), graphify-out/GRAPH_REPORT.md, graphify-out/ (diretório do grafo), ifood-merchant-api (projeto irmão, regra já estabelecida), Regra Inegociável — Graphify, Arquitetura Local-first (não cloud-first), Banco de dados SQLite, Consumer (concorrente nacional, validação de mercado) (+29 more)

### Community 3 - "ItemComanda"
Cohesion: 0.09
Nodes (8): JpaRepository, ItemComanda, Entity, Override, ItemComandaRepository, ProdutoRepository, ItemComandaService, Service

### Community 4 - "ComandaServiceTest.java"
Cohesion: 0.15
Nodes (15): Comanda, ComandaService, ComandaController, GetMapping, PostMapping, RequestMapping, ResponseEntity, RestController (+7 more)

### Community 5 - "ProdutoController.java"
Cohesion: 0.13
Nodes (17): PatchMapping, Produto, ProdutoService, GetMapping, PostMapping, RequestMapping, ResponseEntity, RestController (+9 more)

### Community 6 - "MesaController.java"
Cohesion: 0.14
Nodes (15): Mesa, MesaService, GetMapping, PostMapping, RequestMapping, ResponseEntity, RestController, MesaController (+7 more)

### Community 7 - "GlobalExceptionHandler.java"
Cohesion: 0.16
Nodes (13): ExceptionHandler, MethodArgumentNotValidException, RecursoNaoEncontradoException, RegraDeNegocioException, RestControllerAdvice, GlobalExceptionHandler, ResponseEntity, ErroResponse (+5 more)

### Community 8 - "ItemComandaController.java"
Cohesion: 0.20
Nodes (11): DeleteMapping, ItemComanda, ItemComandaService, ItemComandaController, GetMapping, PostMapping, RequestMapping, ResponseEntity (+3 more)

### Community 9 - "ItemComandaServiceTest.java"
Cohesion: 0.22
Nodes (10): ItemComandaRepository, ItemComandaServiceTest, BeforeEach, CategoriaRepository, ComandaRepository, ExtendWith, MesaRepository, ProdutoRepository (+2 more)

### Community 10 - "CategoriaController.java"
Cohesion: 0.22
Nodes (10): Categoria, CategoriaService, CategoriaController, GetMapping, PostMapping, RequestMapping, ResponseEntity, RestController (+2 more)

### Community 11 - "mvnw"
Cohesion: 0.33
Nodes (6): mvnw script, clean(), die(), exec_maven(), set_java_home(), verbose()

### Community 12 - "GestorComercialApplicationTests.java"
Cohesion: 0.60
Nodes (3): SpringBootTest, GestorComercialApplicationTests, Test

### Community 14 - "StatusMesa"
Cohesion: 0.50
Nodes (3): StatusMesa, LIVRE, OCUPADA

## Knowledge Gaps
- **23 isolated node(s):** `com.vitorraphael:gestor-comercial`, `graphify-out/ (diretório do grafo)`, `graphify-out/GRAPH_REPORT.md`, `ifood-merchant-api (projeto irmão, regra já estabelecida)`, `Food truck do pai do Vitor (negócio real)` (+18 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **2 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `Comanda` connect `Comanda` to `ItemComanda`?**
  _High betweenness centrality (0.155) - this node is a cross-community bridge._
- **Why does `Mesa` connect `Comanda` to `ItemComanda`?**
  _High betweenness centrality (0.130) - this node is a cross-community bridge._
- **Why does `ItemComanda` connect `ItemComanda` to `Comanda`, `Produto`?**
  _High betweenness centrality (0.086) - this node is a cross-community bridge._
- **What connects `com.vitorraphael:gestor-comercial`, `graphify-out/ (diretório do grafo)`, `graphify-out/GRAPH_REPORT.md` to the rest of the system?**
  _23 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `Comanda` be split into smaller, more focused modules?**
  _Cohesion score 0.06547619047619048 - nodes in this community are weakly interconnected._
- **Should `Produto` be split into smaller, more focused modules?**
  _Cohesion score 0.08478513356562137 - nodes in this community are weakly interconnected._
- **Should `Gestor Comercial (sistema PDV)` be split into smaller, more focused modules?**
  _Cohesion score 0.06156156156156156 - nodes in this community are weakly interconnected._