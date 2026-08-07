# Graph Report - .  (2026-08-07)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 497 nodes · 1074 edges · 21 communities (18 shown, 3 thin omitted)
- Extraction: 88% EXTRACTED · 12% INFERRED · 0% AMBIGUOUS · INFERRED: 125 edges (avg confidence: 0.8)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `e512f902`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- Caixa
- Comanda
- MovimentoCaixa
- RegraDeNegocioException
- Gestor Comercial (sistema PDV)
- ComandaServiceTest.java
- ProdutoController.java
- CategoriaController.java
- ItemComanda
- ItemComandaController.java
- Categoria
- ItemComandaServiceTest.java
- Produto
- mvnw
- ItemComandaService.java
- JpaRepository
- ProdutoService
- GestorComercialApplicationTests.java
- GestorComercialApplication
- StatusMesa
- com.vitorraphael:gestor-comercial

## God Nodes (most connected - your core abstractions)
1. `Caixa` - 39 edges
2. `MovimentoCaixa` - 31 edges
3. `Comanda` - 29 edges
4. `Produto` - 26 edges
5. `ItemComanda` - 24 edges
6. `Mesa` - 23 edges
7. `Categoria` - 19 edges
8. `CaixaService` - 17 edges
9. `ItemComandaServiceTest` - 16 edges
10. `TipoMovimento` - 16 edges

## Surprising Connections (you probably didn't know these)
- `gestor-comercial (projeto, dono deste grafo)` --conceptually_related_to--> `Gestor Comercial (sistema PDV)`  [INFERRED]
  CLAUDE.md → PLANTA_PROJETO.md
- `ProdutoRepository` --references--> `Produto`  [EXTRACTED]
  src/main/java/com/vitorraphael/gestor_comercial/repository/ProdutoRepository.java → src/main/java/com/vitorraphael/gestor_comercial/model/Produto.java
- `Produto` --references--> `Categoria`  [EXTRACTED]
  src/main/java/com/vitorraphael/gestor_comercial/model/Produto.java → src/main/java/com/vitorraphael/gestor_comercial/model/Categoria.java
- `ItemComanda` --references--> `Comanda`  [EXTRACTED]
  src/main/java/com/vitorraphael/gestor_comercial/model/ItemComanda.java → src/main/java/com/vitorraphael/gestor_comercial/model/Comanda.java
- `ItemComanda` --references--> `Produto`  [EXTRACTED]
  src/main/java/com/vitorraphael/gestor_comercial/model/ItemComanda.java → src/main/java/com/vitorraphael/gestor_comercial/model/Produto.java

## Import Cycles
- None detected.

## Hyperedges (group relationships)
- **Validação de mercado da arquitetura local-first** — planta_projeto_arquitetura_local_first, planta_projeto_consumer_concorrente, planta_projeto_toast_pos [EXTRACTED 1.00]
- **Roteamento de impressão por categoria entre as 3 impressoras reais** — planta_projeto_tabela_roteamento_impressoras, planta_projeto_impressora_caixa, planta_projeto_impressora_trailer_1, planta_projeto_impressora_trailer_2 [EXTRACTED 1.00]
- **Roadmap de fases mapeado aos módulos planejados** — planta_projeto_roadmap_fases, planta_projeto_modulo_cardapio, planta_projeto_modulo_mesas_comandas, planta_projeto_modulo_caixa, planta_projeto_modulo_impressao, planta_projeto_interface_atendente_pwa, planta_projeto_modulo_usuarios_funcionarios, planta_projeto_piloto_grand_chef_pro [EXTRACTED 1.00]

## Communities (21 total, 3 thin omitted)

### Community 0 - "Caixa"
Cohesion: 0.06
Nodes (29): CaixaController, GetMapping, PostMapping, RequestMapping, ResponseEntity, RestController, AbrirCaixaRequest, CaixaResponse (+21 more)

### Community 1 - "Comanda"
Cohesion: 0.06
Nodes (20): RecursoNaoEncontradoException, RegraDeNegocioException, Comanda, Entity, Override, StatusComanda, Entity, Override (+12 more)

### Community 2 - "MovimentoCaixa"
Cohesion: 0.10
Nodes (16): GetMapping, PostMapping, RequestMapping, ResponseEntity, RestController, MovimentoCaixaController, MovimentoCaixaRequest, MovimentoCaixaResponse (+8 more)

### Community 3 - "RegraDeNegocioException"
Cohesion: 0.10
Nodes (23): ExceptionHandler, Mesa, MesaService, MethodArgumentNotValidException, RecursoNaoEncontradoException, RegraDeNegocioException, RestControllerAdvice, GlobalExceptionHandler (+15 more)

### Community 4 - "Gestor Comercial (sistema PDV)"
Cohesion: 0.06
Nodes (37): gestor-comercial (projeto, dono deste grafo), graphify-out/GRAPH_REPORT.md, graphify-out/ (diretório do grafo), ifood-merchant-api (projeto irmão, regra já estabelecida), Regra Inegociável — Graphify, Arquitetura Local-first (não cloud-first), Banco de dados SQLite, Consumer (concorrente nacional, validação de mercado) (+29 more)

### Community 5 - "ComandaServiceTest.java"
Cohesion: 0.14
Nodes (16): Comanda, ComandaService, ComandaController, GetMapping, PostMapping, RequestMapping, ResponseEntity, RestController (+8 more)

### Community 6 - "ProdutoController.java"
Cohesion: 0.13
Nodes (17): PatchMapping, Produto, ProdutoService, GetMapping, PostMapping, RequestMapping, ResponseEntity, RestController (+9 more)

### Community 7 - "CategoriaController.java"
Cohesion: 0.14
Nodes (15): Categoria, CategoriaService, CategoriaController, GetMapping, PostMapping, RequestMapping, ResponseEntity, RestController (+7 more)

### Community 8 - "ItemComanda"
Cohesion: 0.14
Nodes (3): ItemComanda, Entity, Override

### Community 9 - "ItemComandaController.java"
Cohesion: 0.20
Nodes (11): DeleteMapping, ItemComanda, ItemComandaService, ItemComandaController, GetMapping, PostMapping, RequestMapping, ResponseEntity (+3 more)

### Community 10 - "Categoria"
Cohesion: 0.17
Nodes (6): Categoria, Entity, Override, CategoriaRepository, CategoriaService, Service

### Community 11 - "ItemComandaServiceTest.java"
Cohesion: 0.24
Nodes (9): ItemComandaRepository, ItemComandaServiceTest, BeforeEach, CategoriaRepository, ComandaRepository, ExtendWith, MesaRepository, ProdutoRepository (+1 more)

### Community 12 - "Produto"
Cohesion: 0.18
Nodes (3): Entity, Override, Produto

### Community 13 - "mvnw"
Cohesion: 0.33
Nodes (6): mvnw script, clean(), die(), exec_maven(), set_java_home(), verbose()

### Community 14 - "ItemComandaService.java"
Cohesion: 0.33
Nodes (3): ItemComandaRepository, ItemComandaService, Service

### Community 16 - "ProdutoService"
Cohesion: 0.53
Nodes (3): ProdutoRepository, Service, ProdutoService

### Community 17 - "GestorComercialApplicationTests.java"
Cohesion: 0.60
Nodes (3): SpringBootTest, GestorComercialApplicationTests, Test

### Community 19 - "StatusMesa"
Cohesion: 0.50
Nodes (3): StatusMesa, LIVRE, OCUPADA

## Knowledge Gaps
- **29 isolated node(s):** `com.vitorraphael:gestor-comercial`, `graphify-out/ (diretório do grafo)`, `graphify-out/GRAPH_REPORT.md`, `ifood-merchant-api (projeto irmão, regra já estabelecida)`, `Food truck do pai do Vitor (negócio real)` (+24 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **3 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `Caixa` connect `Caixa` to `MovimentoCaixa`?**
  _High betweenness centrality (0.111) - this node is a cross-community bridge._
- **Why does `Comanda` connect `Comanda` to `ItemComanda`, `ItemComandaService.java`?**
  _High betweenness centrality (0.098) - this node is a cross-community bridge._
- **What connects `com.vitorraphael:gestor-comercial`, `graphify-out/ (diretório do grafo)`, `graphify-out/GRAPH_REPORT.md` to the rest of the system?**
  _29 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `Caixa` be split into smaller, more focused modules?**
  _Cohesion score 0.06358543417366946 - nodes in this community are weakly interconnected._
- **Should `Comanda` be split into smaller, more focused modules?**
  _Cohesion score 0.0625 - nodes in this community are weakly interconnected._
- **Should `MovimentoCaixa` be split into smaller, more focused modules?**
  _Cohesion score 0.09634551495016612 - nodes in this community are weakly interconnected._
- **Should `RegraDeNegocioException` be split into smaller, more focused modules?**
  _Cohesion score 0.09615384615384616 - nodes in this community are weakly interconnected._