# Graph Report - .  (2026-08-07)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 594 nodes · 1293 edges · 28 communities (21 shown, 7 thin omitted)
- Extraction: 88% EXTRACTED · 12% INFERRED · 0% AMBIGUOUS · INFERRED: 161 edges (avg confidence: 0.8)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `7db60e8b`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- MovimentoCaixa
- Comanda
- Caixa
- Produto
- RecursoNaoEncontradoException
- Gestor Comercial (sistema PDV)
- ComandaServiceTest.java
- ProdutoController.java
- ImpressaoService
- ItemComandaServiceTest.java
- ItemComandaController.java
- Impressora
- CategoriaService
- CategoriaController.java
- ImpressoraController.java
- Categoria
- RoteamentoImpressaoServiceTest
- mvnw
- RoteamentoImpressaoService
- GestorComercialApplicationTests.java
- Categoria
- GestorComercialApplication
- StatusMesa
- CategoriaRequest.java
- CategoriaService
- com.vitorraphael:gestor-comercial
- ComandaRepository
- MesaRepository

## God Nodes (most connected - your core abstractions)
1. `Caixa` - 39 edges
2. `Categoria` - 33 edges
3. `MovimentoCaixa` - 31 edges
4. `Comanda` - 29 edges
5. `Impressora` - 29 edges
6. `Produto` - 26 edges
7. `ItemComanda` - 24 edges
8. `Mesa` - 23 edges
9. `CaixaService` - 17 edges
10. `TipoMovimento` - 16 edges

## Surprising Connections (you probably didn't know these)
- `gestor-comercial (projeto, dono deste grafo)` --conceptually_related_to--> `Gestor Comercial (sistema PDV)`  [INFERRED]
  CLAUDE.md → PLANTA_PROJETO.md
- `ItemComanda` --references--> `Comanda`  [EXTRACTED]
  src/main/java/com/vitorraphael/gestor_comercial/model/ItemComanda.java → src/main/java/com/vitorraphael/gestor_comercial/model/Comanda.java
- `Produto` --references--> `Categoria`  [EXTRACTED]
  src/main/java/com/vitorraphael/gestor_comercial/model/Produto.java → src/main/java/com/vitorraphael/gestor_comercial/model/Categoria.java
- `ItemComandaService` --references--> `ComandaService`  [EXTRACTED]
  src/main/java/com/vitorraphael/gestor_comercial/service/ItemComandaService.java → src/main/java/com/vitorraphael/gestor_comercial/service/ComandaService.java
- `ProdutoService` --references--> `CategoriaService`  [EXTRACTED]
  src/main/java/com/vitorraphael/gestor_comercial/service/ProdutoService.java → src/main/java/com/vitorraphael/gestor_comercial/service/CategoriaService.java

## Import Cycles
- None detected.

## Hyperedges (group relationships)
- **Validação de mercado da arquitetura local-first** — planta_projeto_arquitetura_local_first, planta_projeto_consumer_concorrente, planta_projeto_toast_pos [EXTRACTED 1.00]
- **Roteamento de impressão por categoria entre as 3 impressoras reais** — planta_projeto_tabela_roteamento_impressoras, planta_projeto_impressora_caixa, planta_projeto_impressora_trailer_1, planta_projeto_impressora_trailer_2 [EXTRACTED 1.00]
- **Roadmap de fases mapeado aos módulos planejados** — planta_projeto_roadmap_fases, planta_projeto_modulo_cardapio, planta_projeto_modulo_mesas_comandas, planta_projeto_modulo_caixa, planta_projeto_modulo_impressao, planta_projeto_interface_atendente_pwa, planta_projeto_modulo_usuarios_funcionarios, planta_projeto_piloto_grand_chef_pro [EXTRACTED 1.00]

## Communities (28 total, 7 thin omitted)

### Community 0 - "MovimentoCaixa"
Cohesion: 0.07
Nodes (29): RegraDeNegocioException, GetMapping, PostMapping, RequestMapping, ResponseEntity, RestController, MovimentoCaixaController, MovimentoCaixaRequest (+21 more)

### Community 1 - "Comanda"
Cohesion: 0.06
Nodes (20): RecursoNaoEncontradoException, RegraDeNegocioException, Comanda, Entity, Override, StatusComanda, Entity, Override (+12 more)

### Community 2 - "Caixa"
Cohesion: 0.08
Nodes (17): CaixaController, GetMapping, PostMapping, RequestMapping, ResponseEntity, RestController, AbrirCaixaRequest, CaixaResponse (+9 more)

### Community 3 - "Produto"
Cohesion: 0.06
Nodes (14): JpaRepository, ProdutoRepository, ItemComanda, Entity, Override, Entity, Override, Produto (+6 more)

### Community 4 - "RecursoNaoEncontradoException"
Cohesion: 0.10
Nodes (22): ExceptionHandler, Mesa, MesaService, MethodArgumentNotValidException, RecursoNaoEncontradoException, RestControllerAdvice, GlobalExceptionHandler, ResponseEntity (+14 more)

### Community 5 - "Gestor Comercial (sistema PDV)"
Cohesion: 0.06
Nodes (37): gestor-comercial (projeto, dono deste grafo), graphify-out/GRAPH_REPORT.md, graphify-out/ (diretório do grafo), ifood-merchant-api (projeto irmão, regra já estabelecida), Regra Inegociável — Graphify, Arquitetura Local-first (não cloud-first), Banco de dados SQLite, Consumer (concorrente nacional, validação de mercado) (+29 more)

### Community 6 - "ComandaServiceTest.java"
Cohesion: 0.15
Nodes (15): Comanda, ComandaService, ComandaController, GetMapping, PostMapping, RequestMapping, ResponseEntity, RestController (+7 more)

### Community 7 - "ProdutoController.java"
Cohesion: 0.13
Nodes (17): PatchMapping, Produto, ProdutoService, GetMapping, PostMapping, RequestMapping, ResponseEntity, RestController (+9 more)

### Community 8 - "ImpressaoService"
Cohesion: 0.14
Nodes (14): Logger, ImpressaoController, PostMapping, RequestMapping, ResponseEntity, RestController, ItemImpressaoResponse, ItemComanda (+6 more)

### Community 9 - "ItemComandaServiceTest.java"
Cohesion: 0.19
Nodes (12): ComandaRepository, ItemComandaRepository, MesaRepository, ItemComandaServiceTest, BeforeEach, CategoriaRepository, ExtendWith, ItemComandaService (+4 more)

### Community 10 - "ItemComandaController.java"
Cohesion: 0.20
Nodes (11): DeleteMapping, ItemComanda, ItemComandaService, ItemComandaController, GetMapping, PostMapping, RequestMapping, ResponseEntity (+3 more)

### Community 11 - "Impressora"
Cohesion: 0.19
Nodes (6): Impressora, Entity, Override, ImpressoraRepository, ImpressoraService, Service

### Community 12 - "CategoriaService"
Cohesion: 0.17
Nodes (8): CategoriaService, CategoriaRepository, Service, CategoriaServiceTest, BeforeEach, CategoriaRepository, ExtendWith, Test

### Community 13 - "CategoriaController.java"
Cohesion: 0.23
Nodes (9): CategoriaRequest, CategoriaResponse, CategoriaController, GetMapping, PostMapping, RequestMapping, ResponseEntity, RestController (+1 more)

### Community 14 - "ImpressoraController.java"
Cohesion: 0.21
Nodes (8): ImpressoraController, GetMapping, PostMapping, RequestMapping, ResponseEntity, RestController, ImpressoraRequest, ImpressoraResponse

### Community 15 - "Categoria"
Cohesion: 0.23
Nodes (4): Categoria, Entity, Override, CategoriaRepository

### Community 16 - "RoteamentoImpressaoServiceTest"
Cohesion: 0.29
Nodes (7): BeforeEach, ExtendWith, ItemComanda, ItemComandaService, Produto, Test, RoteamentoImpressaoServiceTest

### Community 17 - "mvnw"
Cohesion: 0.33
Nodes (6): mvnw script, clean(), die(), exec_maven(), set_java_home(), verbose()

### Community 18 - "RoteamentoImpressaoService"
Cohesion: 0.31
Nodes (4): ItemComanda, ItemComandaService, Service, RoteamentoImpressaoService

### Community 19 - "GestorComercialApplicationTests.java"
Cohesion: 0.60
Nodes (3): SpringBootTest, GestorComercialApplicationTests, Test

### Community 22 - "StatusMesa"
Cohesion: 0.50
Nodes (3): StatusMesa, LIVRE, OCUPADA

## Knowledge Gaps
- **30 isolated node(s):** `com.vitorraphael:gestor-comercial`, `graphify-out/ (diretório do grafo)`, `graphify-out/GRAPH_REPORT.md`, `ifood-merchant-api (projeto irmão, regra já estabelecida)`, `Food truck do pai do Vitor (negócio real)` (+25 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **7 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `Categoria` connect `Categoria` to `Produto`, `ProdutoController.java`, `ItemComandaServiceTest.java`, `Impressora`, `CategoriaService`, `CategoriaController.java`, `RoteamentoImpressaoServiceTest`?**
  _High betweenness centrality (0.231) - this node is a cross-community bridge._
- **Why does `Impressora` connect `Impressora` to `CategoriaService`, `ImpressoraController.java`, `Categoria`, `RoteamentoImpressaoServiceTest`, `RoteamentoImpressaoService`?**
  _High betweenness centrality (0.127) - this node is a cross-community bridge._
- **Why does `Produto` connect `Produto` to `Comanda`, `Categoria`?**
  _High betweenness centrality (0.101) - this node is a cross-community bridge._
- **What connects `com.vitorraphael:gestor-comercial`, `graphify-out/ (diretório do grafo)`, `graphify-out/GRAPH_REPORT.md` to the rest of the system?**
  _30 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `MovimentoCaixa` be split into smaller, more focused modules?**
  _Cohesion score 0.06874717322478517 - nodes in this community are weakly interconnected._
- **Should `Comanda` be split into smaller, more focused modules?**
  _Cohesion score 0.06448412698412699 - nodes in this community are weakly interconnected._
- **Should `Caixa` be split into smaller, more focused modules?**
  _Cohesion score 0.08196721311475409 - nodes in this community are weakly interconnected._