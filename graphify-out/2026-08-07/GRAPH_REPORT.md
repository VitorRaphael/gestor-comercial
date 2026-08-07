# Graph Report - .  (2026-08-07)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 623 nodes · 1350 edges · 31 communities (23 shown, 8 thin omitted)
- Extraction: 88% EXTRACTED · 12% INFERRED · 0% AMBIGUOUS · INFERRED: 162 edges (avg confidence: 0.8)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `c1634adb`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- Caixa
- Produto
- Comanda
- MovimentoCaixa
- RegraDeNegocioException
- Gestor Comercial (sistema PDV)
- ComandaServiceTest.java
- ProdutoController.java
- ImpressaoService
- ItemComandaServiceTest.java
- ItemComandaController.java
- Impressora
- CategoriaService
- CategoriaController.java
- app.js
- ImpressoraController.java
- Categoria
- RoteamentoImpressaoServiceTest
- mvnw
- manifest.json
- RoteamentoImpressaoService
- GestorComercialApplicationTests.java
- Categoria
- GestorComercialApplication
- StatusMesa
- CategoriaRequest.java
- sw.js
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

## Communities (31 total, 8 thin omitted)

### Community 0 - "Caixa"
Cohesion: 0.06
Nodes (29): CaixaController, GetMapping, PostMapping, RequestMapping, ResponseEntity, RestController, AbrirCaixaRequest, CaixaResponse (+21 more)

### Community 1 - "Produto"
Cohesion: 0.06
Nodes (15): JpaRepository, ProdutoRepository, RecursoNaoEncontradoException, ItemComanda, Entity, Override, Entity, Override (+7 more)

### Community 2 - "Comanda"
Cohesion: 0.07
Nodes (19): RegraDeNegocioException, Comanda, Entity, Override, StatusComanda, Entity, Override, Mesa (+11 more)

### Community 3 - "MovimentoCaixa"
Cohesion: 0.10
Nodes (16): GetMapping, PostMapping, RequestMapping, ResponseEntity, RestController, MovimentoCaixaController, MovimentoCaixaRequest, MovimentoCaixaResponse (+8 more)

### Community 4 - "RegraDeNegocioException"
Cohesion: 0.10
Nodes (23): ExceptionHandler, Mesa, MesaService, MethodArgumentNotValidException, RecursoNaoEncontradoException, RegraDeNegocioException, RestControllerAdvice, GlobalExceptionHandler (+15 more)

### Community 5 - "Gestor Comercial (sistema PDV)"
Cohesion: 0.06
Nodes (37): gestor-comercial (projeto, dono deste grafo), graphify-out/GRAPH_REPORT.md, graphify-out/ (diretório do grafo), ifood-merchant-api (projeto irmão, regra já estabelecida), Regra Inegociável — Graphify, Arquitetura Local-first (não cloud-first), Banco de dados SQLite, Consumer (concorrente nacional, validação de mercado) (+29 more)

### Community 6 - "ComandaServiceTest.java"
Cohesion: 0.15
Nodes (15): Comanda, ComandaService, ComandaController, GetMapping, PostMapping, RequestMapping, ResponseEntity, RestController (+7 more)

### Community 7 - "ProdutoController.java"
Cohesion: 0.13
Nodes (16): Produto, ProdutoService, GetMapping, PostMapping, RequestMapping, ResponseEntity, RestController, ProdutoController (+8 more)

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
Cohesion: 0.18
Nodes (5): Impressora, Override, ImpressoraRepository, ImpressoraService, Service

### Community 12 - "CategoriaService"
Cohesion: 0.18
Nodes (9): Entity, CategoriaService, CategoriaRepository, Service, CategoriaServiceTest, BeforeEach, CategoriaRepository, ExtendWith (+1 more)

### Community 13 - "CategoriaController.java"
Cohesion: 0.21
Nodes (10): CategoriaRequest, CategoriaResponse, PatchMapping, CategoriaController, GetMapping, PostMapping, RequestMapping, ResponseEntity (+2 more)

### Community 14 - "app.js"
Cohesion: 0.35
Nodes (16): abrirMesa(), abrirModalProduto(), abrirModalQuantidade(), api(), carregarItens(), carregarMesas(), el(), formatarMoeda() (+8 more)

### Community 15 - "ImpressoraController.java"
Cohesion: 0.21
Nodes (8): ImpressoraController, GetMapping, PostMapping, RequestMapping, ResponseEntity, RestController, ImpressoraRequest, ImpressoraResponse

### Community 16 - "Categoria"
Cohesion: 0.23
Nodes (4): Categoria, Entity, Override, CategoriaRepository

### Community 17 - "RoteamentoImpressaoServiceTest"
Cohesion: 0.29
Nodes (7): BeforeEach, ExtendWith, ItemComanda, ItemComandaService, Produto, Test, RoteamentoImpressaoServiceTest

### Community 18 - "mvnw"
Cohesion: 0.33
Nodes (6): mvnw script, clean(), die(), exec_maven(), set_java_home(), verbose()

### Community 19 - "manifest.json"
Cohesion: 0.20
Nodes (9): background_color, display, icons, name, orientation, scope, short_name, start_url (+1 more)

### Community 20 - "RoteamentoImpressaoService"
Cohesion: 0.31
Nodes (4): ItemComanda, ItemComandaService, Service, RoteamentoImpressaoService

### Community 21 - "GestorComercialApplicationTests.java"
Cohesion: 0.60
Nodes (3): SpringBootTest, GestorComercialApplicationTests, Test

### Community 24 - "StatusMesa"
Cohesion: 0.50
Nodes (3): StatusMesa, LIVRE, OCUPADA

## Knowledge Gaps
- **41 isolated node(s):** `com.vitorraphael:gestor-comercial`, `graphify-out/ (diretório do grafo)`, `graphify-out/GRAPH_REPORT.md`, `ifood-merchant-api (projeto irmão, regra já estabelecida)`, `Food truck do pai do Vitor (negócio real)` (+36 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **8 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `Categoria` connect `Categoria` to `Produto`, `ProdutoController.java`, `ItemComandaServiceTest.java`, `Impressora`, `CategoriaService`, `CategoriaController.java`, `RoteamentoImpressaoServiceTest`?**
  _High betweenness centrality (0.210) - this node is a cross-community bridge._
- **Why does `Impressora` connect `Impressora` to `CategoriaService`, `ImpressoraController.java`, `Categoria`, `RoteamentoImpressaoServiceTest`, `RoteamentoImpressaoService`?**
  _High betweenness centrality (0.116) - this node is a cross-community bridge._
- **Why does `Produto` connect `Produto` to `Categoria`?**
  _High betweenness centrality (0.092) - this node is a cross-community bridge._
- **What connects `com.vitorraphael:gestor-comercial`, `graphify-out/ (diretório do grafo)`, `graphify-out/GRAPH_REPORT.md` to the rest of the system?**
  _41 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `Caixa` be split into smaller, more focused modules?**
  _Cohesion score 0.06292749658002736 - nodes in this community are weakly interconnected._
- **Should `Produto` be split into smaller, more focused modules?**
  _Cohesion score 0.059562841530054644 - nodes in this community are weakly interconnected._
- **Should `Comanda` be split into smaller, more focused modules?**
  _Cohesion score 0.06610169491525424 - nodes in this community are weakly interconnected._