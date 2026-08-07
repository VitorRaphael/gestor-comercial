# Graph Report - .  (2026-08-07)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 789 nodes · 1718 edges · 46 communities (30 shown, 16 thin omitted)
- Extraction: 87% EXTRACTED · 13% INFERRED · 0% AMBIGUOUS · INFERRED: 220 edges (avg confidence: 0.8)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `cf1ff69c`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- Caixa
- Categoria
- ExigeGerente
- RegraDeNegocioException
- Gestor Comercial (sistema PDV)
- ComandaServiceTest.java
- ProdutoController.java
- Comanda
- ImpressaoService
- ItemComanda
- Funcionario
- app.js
- Produto
- ItemComandaService.java
- NaoAutorizadoException
- ItemComandaServiceTest.java
- ItemComandaController.java
- Mesa
- CaixaController.java
- SessaoService
- MovimentoCaixaController.java
- .criarFuncionarioComPin
- PinHashService
- PerfilFuncionario
- ImpressoraController.java
- FuncionarioService
- mvnw
- manifest.json
- JpaRepository
- GestorComercialApplication
- GestorComercialApplicationTests.java
- StatusMesa
- AbrirCaixaRequest.java
- AssociarImpressoraRequest.java
- CategoriaRequest.java
- ErroResponse.java
- FecharCaixaRequest.java
- ImpressoraRequest.java
- MesaRequest.java
- MovimentoCaixaRequest.java
- ProdutoRequest.java
- sw.js
- PatchMapping
- com.vitorraphael:gestor-comercial
- ComandaRepository
- MesaRepository

## God Nodes (most connected - your core abstractions)
1. `Funcionario` - 40 edges
2. `Caixa` - 38 edges
3. `Categoria` - 32 edges
4. `MovimentoCaixa` - 30 edges
5. `Comanda` - 29 edges
6. `Impressora` - 28 edges
7. `Produto` - 26 edges
8. `ItemComanda` - 24 edges
9. `Mesa` - 23 edges
10. `ExigeGerente` - 22 edges

## Surprising Connections (you probably didn't know these)
- `gestor-comercial (projeto, dono deste grafo)` --conceptually_related_to--> `Gestor Comercial (sistema PDV)`  [INFERRED]
  CLAUDE.md → PLANTA_PROJETO.md
- `ProdutoRepository` --references--> `Produto`  [EXTRACTED]
  src/main/java/com/vitorraphael/gestor_comercial/repository/ProdutoRepository.java → src/main/java/com/vitorraphael/gestor_comercial/model/Produto.java
- `Comanda` --references--> `Mesa`  [EXTRACTED]
  src/main/java/com/vitorraphael/gestor_comercial/model/Comanda.java → src/main/java/com/vitorraphael/gestor_comercial/model/Mesa.java
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

## Communities (46 total, 16 thin omitted)

### Community 0 - "Caixa"
Cohesion: 0.06
Nodes (30): CaixaResponse, MovimentoCaixaResponse, Caixa, Entity, Override, Entity, Override, MovimentoCaixa (+22 more)

### Community 1 - "Categoria"
Cohesion: 0.06
Nodes (30): ImpressoraResponse, Categoria, Entity, Override, Impressora, Entity, Override, CategoriaRepository (+22 more)

### Community 2 - "ExigeGerente"
Cohesion: 0.09
Nodes (25): AssociarImpressoraRequest, Categoria, CategoriaRequest, CategoriaResponse, CategoriaService, Retention, CategoriaController, GetMapping (+17 more)

### Community 3 - "RegraDeNegocioException"
Cohesion: 0.10
Nodes (24): ErroResponse, ExceptionHandler, Mesa, MesaRequest, MesaResponse, MesaService, MethodArgumentNotValidException, RecursoNaoEncontradoException (+16 more)

### Community 4 - "Gestor Comercial (sistema PDV)"
Cohesion: 0.06
Nodes (37): gestor-comercial (projeto, dono deste grafo), graphify-out/GRAPH_REPORT.md, graphify-out/ (diretório do grafo), ifood-merchant-api (projeto irmão, regra já estabelecida), Regra Inegociável — Graphify, Arquitetura Local-first (não cloud-first), Banco de dados SQLite, Consumer (concorrente nacional, validação de mercado) (+29 more)

### Community 5 - "ComandaServiceTest.java"
Cohesion: 0.15
Nodes (15): Comanda, ComandaService, ComandaController, GetMapping, PostMapping, RequestMapping, ResponseEntity, RestController (+7 more)

### Community 6 - "ProdutoController.java"
Cohesion: 0.12
Nodes (18): Produto, ProdutoRequest, ProdutoResponse, ProdutoService, GetMapping, PatchMapping, PostMapping, RequestMapping (+10 more)

### Community 7 - "Comanda"
Cohesion: 0.13
Nodes (6): Comanda, Entity, Override, StatusComanda, ComandaRepository, StatusComanda

### Community 8 - "ImpressaoService"
Cohesion: 0.14
Nodes (14): Logger, ImpressaoController, PostMapping, RequestMapping, ResponseEntity, RestController, ItemImpressaoResponse, ItemComanda (+6 more)

### Community 9 - "ItemComanda"
Cohesion: 0.11
Nodes (3): ItemComanda, Entity, Override

### Community 10 - "Funcionario"
Cohesion: 0.15
Nodes (4): Entity, Override, Funcionario, Override

### Community 11 - "app.js"
Cohesion: 0.24
Nodes (22): abrirMesa(), abrirModalProduto(), abrirModalQuantidade(), api(), aplicarPermissoesDeInterface(), carregarItens(), carregarMesas(), el() (+14 more)

### Community 12 - "Produto"
Cohesion: 0.15
Nodes (6): ProdutoRepository, Entity, Override, Produto, Service, ProdutoService

### Community 13 - "ItemComandaService.java"
Cohesion: 0.16
Nodes (12): RecursoNaoEncontradoException, RegraDeNegocioException, StatusComanda, ABERTA, FECHADA, ItemComandaRepository, ComandaService, Service (+4 more)

### Community 14 - "NaoAutorizadoException"
Cohesion: 0.14
Nodes (13): Configuration, HandlerInterceptor, HttpServletRequest, HttpServletResponse, InterceptorRegistry, Override, WebConfig, AcessoNegadoException (+5 more)

### Community 15 - "ItemComandaServiceTest.java"
Cohesion: 0.19
Nodes (12): ComandaRepository, ItemComandaRepository, MesaRepository, ItemComandaServiceTest, BeforeEach, CategoriaRepository, ExtendWith, ItemComandaService (+4 more)

### Community 16 - "ItemComandaController.java"
Cohesion: 0.20
Nodes (11): DeleteMapping, ItemComanda, ItemComandaService, ItemComandaController, GetMapping, PostMapping, RequestMapping, ResponseEntity (+3 more)

### Community 17 - "Mesa"
Cohesion: 0.17
Nodes (5): Entity, Override, Mesa, MesaRepository, StatusMesa

### Community 18 - "CaixaController.java"
Cohesion: 0.26
Nodes (10): AbrirCaixaRequest, CaixaResponse, CaixaService, FecharCaixaRequest, CaixaController, GetMapping, PostMapping, RequestMapping (+2 more)

### Community 19 - "SessaoService"
Cohesion: 0.18
Nodes (9): AuthController, PostMapping, RequestMapping, ResponseEntity, RestController, LoginRequest, LoginResponse, Service (+1 more)

### Community 20 - "MovimentoCaixaController.java"
Cohesion: 0.26
Nodes (10): MovimentoCaixaRequest, MovimentoCaixaResponse, MovimentoCaixaService, GetMapping, PostMapping, RequestMapping, ResponseEntity, RestController (+2 more)

### Community 21 - ".criarFuncionarioComPin"
Cohesion: 0.31
Nodes (4): BeforeEach, ExtendWith, FuncionarioServiceTest, Test

### Community 22 - "PinHashService"
Cohesion: 0.27
Nodes (5): SecureRandom, Service, PinHashService, Test, PinHashServiceTest

### Community 23 - "PerfilFuncionario"
Cohesion: 0.24
Nodes (5): PerfilFuncionario, ATENDENTE, GERENTE, Test, SessaoServiceTest

### Community 24 - "ImpressoraController.java"
Cohesion: 0.29
Nodes (9): ImpressoraRequest, ImpressoraResponse, ImpressoraService, ImpressoraController, GetMapping, PostMapping, RequestMapping, ResponseEntity (+1 more)

### Community 25 - "FuncionarioService"
Cohesion: 0.42
Nodes (6): CommandLineRunner, FuncionarioSeeder, Component, FuncionarioRepository, FuncionarioService, Service

### Community 26 - "mvnw"
Cohesion: 0.33
Nodes (6): mvnw script, clean(), die(), exec_maven(), set_java_home(), verbose()

### Community 27 - "manifest.json"
Cohesion: 0.20
Nodes (9): background_color, display, icons, name, orientation, scope, short_name, start_url (+1 more)

### Community 30 - "GestorComercialApplicationTests.java"
Cohesion: 0.60
Nodes (3): SpringBootTest, GestorComercialApplicationTests, Test

### Community 31 - "StatusMesa"
Cohesion: 0.50
Nodes (3): StatusMesa, LIVRE, OCUPADA

## Knowledge Gaps
- **52 isolated node(s):** `com.vitorraphael:gestor-comercial`, `graphify-out/ (diretório do grafo)`, `graphify-out/GRAPH_REPORT.md`, `ifood-merchant-api (projeto irmão, regra já estabelecida)`, `Food truck do pai do Vitor (negócio real)` (+47 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **16 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `ExigeGerente` connect `ExigeGerente` to `RegraDeNegocioException`, `ProdutoController.java`, `CaixaController.java`, `MovimentoCaixaController.java`, `ImpressoraController.java`?**
  _High betweenness centrality (0.129) - this node is a cross-community bridge._
- **Why does `Categoria` connect `Categoria` to `Produto`, `ProdutoController.java`, `ItemComandaServiceTest.java`?**
  _High betweenness centrality (0.124) - this node is a cross-community bridge._
- **Why does `Funcionario` connect `Funcionario` to `ExigeGerente`, `NaoAutorizadoException`, `SessaoService`, `.criarFuncionarioComPin`, `PerfilFuncionario`, `FuncionarioService`, `JpaRepository`?**
  _High betweenness centrality (0.088) - this node is a cross-community bridge._
- **Are the 2 inferred relationships involving `Funcionario` (e.g. with `.deveAutenticarComTokenDeSessaoValido()` and `.naoDeveAutenticarAposEncerrarSessao()`) actually correct?**
  _`Funcionario` has 2 INFERRED edges - model-reasoned connections that need verification._
- **What connects `com.vitorraphael:gestor-comercial`, `graphify-out/ (diretório do grafo)`, `graphify-out/GRAPH_REPORT.md` to the rest of the system?**
  _52 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `Caixa` be split into smaller, more focused modules?**
  _Cohesion score 0.05554386703134862 - nodes in this community are weakly interconnected._
- **Should `Categoria` be split into smaller, more focused modules?**
  _Cohesion score 0.05524537173082574 - nodes in this community are weakly interconnected._