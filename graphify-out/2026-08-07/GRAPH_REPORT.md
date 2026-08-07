# Graph Report - .  (2026-08-07)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 833 nodes · 1850 edges · 48 communities (30 shown, 18 thin omitted)
- Extraction: 88% EXTRACTED · 12% INFERRED · 0% AMBIGUOUS · INFERRED: 226 edges (avg confidence: 0.79)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `9b0fad57`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- Caixa
- Categoria
- ExigeGerente
- desktop/js/app.js
- Gestor Comercial (sistema PDV)
- RegraDeNegocioException
- ComandaServiceTest.java
- Funcionario
- MesaController.java
- Comanda
- ImpressaoService
- ItemComanda
- static/js/app.js
- Produto
- ItemComandaService.java
- CategoriaController.java
- ItemComandaServiceTest.java
- ItemComandaController.java
- SessaoService
- Mesa
- CaixaController.java
- PerfilFuncionario
- FuncionarioController.java
- FuncionarioService
- PinHashService
- WebConfig
- mvnw
- manifest.json
- .autenticar
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
- Override
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

## Communities (48 total, 18 thin omitted)

### Community 0 - "Caixa"
Cohesion: 0.06
Nodes (30): CaixaResponse, MovimentoCaixaResponse, Caixa, Entity, Override, Entity, Override, MovimentoCaixa (+22 more)

### Community 1 - "Categoria"
Cohesion: 0.06
Nodes (30): ImpressoraResponse, Categoria, Entity, Override, Impressora, Entity, Override, CategoriaRepository (+22 more)

### Community 2 - "ExigeGerente"
Cohesion: 0.08
Nodes (30): ImpressoraRequest, ImpressoraResponse, ImpressoraService, Produto, ProdutoRequest, ProdutoResponse, ProdutoService, Retention (+22 more)

### Community 3 - "desktop/js/app.js"
Cohesion: 0.17
Nodes (37): abrirComandaDaMesa(), abrirModalAbrirCaixa(), abrirModalAdicionarItem(), abrirModalBase(), abrirModalFecharCaixa(), abrirModalFormulario(), abrirModalNovoMovimento(), api() (+29 more)

### Community 4 - "Gestor Comercial (sistema PDV)"
Cohesion: 0.06
Nodes (37): gestor-comercial (projeto, dono deste grafo), graphify-out/GRAPH_REPORT.md, graphify-out/ (diretório do grafo), ifood-merchant-api (projeto irmão, regra já estabelecida), Regra Inegociável — Graphify, Arquitetura Local-first (não cloud-first), Banco de dados SQLite, Consumer (concorrente nacional, validação de mercado) (+29 more)

### Community 5 - "RegraDeNegocioException"
Cohesion: 0.13
Nodes (19): ErroResponse, ExceptionHandler, MethodArgumentNotValidException, MovimentoCaixaRequest, MovimentoCaixaResponse, MovimentoCaixaService, RecursoNaoEncontradoException, RegraDeNegocioException (+11 more)

### Community 6 - "ComandaServiceTest.java"
Cohesion: 0.15
Nodes (15): Comanda, ComandaService, ComandaController, GetMapping, PostMapping, RequestMapping, ResponseEntity, RestController (+7 more)

### Community 7 - "Funcionario"
Cohesion: 0.17
Nodes (5): ExtendWith, Funcionario, Override, FuncionarioServiceTest, Test

### Community 8 - "MesaController.java"
Cohesion: 0.13
Nodes (16): Mesa, MesaRequest, MesaResponse, MesaService, GetMapping, PostMapping, RequestMapping, ResponseEntity (+8 more)

### Community 9 - "Comanda"
Cohesion: 0.13
Nodes (6): Comanda, Entity, Override, StatusComanda, ComandaRepository, StatusComanda

### Community 10 - "ImpressaoService"
Cohesion: 0.14
Nodes (14): Logger, ImpressaoController, PostMapping, RequestMapping, ResponseEntity, RestController, ItemImpressaoResponse, ItemComanda (+6 more)

### Community 11 - "ItemComanda"
Cohesion: 0.11
Nodes (3): ItemComanda, Entity, Override

### Community 12 - "static/js/app.js"
Cohesion: 0.22
Nodes (22): abrirMesa(), abrirModalProduto(), abrirModalQuantidade(), api(), aplicarPermissoesDeInterface(), carregarItens(), carregarMesas(), el() (+14 more)

### Community 13 - "Produto"
Cohesion: 0.15
Nodes (6): ProdutoRepository, Entity, Override, Produto, Service, ProdutoService

### Community 14 - "ItemComandaService.java"
Cohesion: 0.16
Nodes (12): RecursoNaoEncontradoException, RegraDeNegocioException, StatusComanda, ABERTA, FECHADA, ItemComandaRepository, ComandaService, Service (+4 more)

### Community 15 - "CategoriaController.java"
Cohesion: 0.18
Nodes (13): AssociarImpressoraRequest, Categoria, CategoriaRequest, CategoriaResponse, CategoriaService, CategoriaController, GetMapping, PatchMapping (+5 more)

### Community 16 - "ItemComandaServiceTest.java"
Cohesion: 0.19
Nodes (12): ComandaRepository, ItemComandaRepository, MesaRepository, ItemComandaServiceTest, BeforeEach, CategoriaRepository, ExtendWith, ItemComandaService (+4 more)

### Community 17 - "ItemComandaController.java"
Cohesion: 0.20
Nodes (11): DeleteMapping, ItemComanda, ItemComandaService, ItemComandaController, GetMapping, PostMapping, RequestMapping, ResponseEntity (+3 more)

### Community 18 - "SessaoService"
Cohesion: 0.16
Nodes (9): AuthController, PostMapping, RequestMapping, ResponseEntity, RestController, LoginRequest, LoginResponse, Service (+1 more)

### Community 19 - "Mesa"
Cohesion: 0.17
Nodes (5): Entity, Override, Mesa, MesaRepository, StatusMesa

### Community 20 - "CaixaController.java"
Cohesion: 0.26
Nodes (10): AbrirCaixaRequest, CaixaResponse, CaixaService, FecharCaixaRequest, CaixaController, GetMapping, PostMapping, RequestMapping (+2 more)

### Community 21 - "PerfilFuncionario"
Cohesion: 0.14
Nodes (12): Entity, HandlerInterceptor, HttpServletRequest, HttpServletResponse, FuncionarioRequest, NaoAutorizadoException, PerfilFuncionario, ATENDENTE (+4 more)

### Community 22 - "FuncionarioController.java"
Cohesion: 0.24
Nodes (8): FuncionarioController, GetMapping, PatchMapping, PostMapping, RequestMapping, ResponseEntity, RestController, FuncionarioResponse

### Community 23 - "FuncionarioService"
Cohesion: 0.25
Nodes (8): BeforeEach, CommandLineRunner, FuncionarioSeeder, Component, Override, FuncionarioRepository, FuncionarioService, Service

### Community 24 - "PinHashService"
Cohesion: 0.28
Nodes (5): SecureRandom, Service, PinHashService, Test, PinHashServiceTest

### Community 25 - "WebConfig"
Cohesion: 0.31
Nodes (7): AutenticacaoInterceptor, Configuration, InterceptorRegistry, Override, WebConfig, ViewControllerRegistry, WebMvcConfigurer

### Community 26 - "mvnw"
Cohesion: 0.33
Nodes (6): mvnw script, clean(), die(), exec_maven(), set_java_home(), verbose()

### Community 27 - "manifest.json"
Cohesion: 0.20
Nodes (9): background_color, display, icons, name, orientation, scope, short_name, start_url (+1 more)

### Community 32 - "GestorComercialApplicationTests.java"
Cohesion: 0.60
Nodes (3): SpringBootTest, GestorComercialApplicationTests, Test

### Community 33 - "StatusMesa"
Cohesion: 0.50
Nodes (3): StatusMesa, LIVRE, OCUPADA

## Knowledge Gaps
- **54 isolated node(s):** `com.vitorraphael:gestor-comercial`, `graphify-out/ (diretório do grafo)`, `graphify-out/GRAPH_REPORT.md`, `ifood-merchant-api (projeto irmão, regra já estabelecida)`, `Food truck do pai do Vitor (negócio real)` (+49 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **18 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `ExigeGerente` connect `ExigeGerente` to `RegraDeNegocioException`, `MesaController.java`, `CategoriaController.java`, `CaixaController.java`, `FuncionarioController.java`?**
  _High betweenness centrality (0.114) - this node is a cross-community bridge._
- **Why does `Categoria` connect `Categoria` to `ItemComandaServiceTest.java`, `ExigeGerente`, `Produto`?**
  _High betweenness centrality (0.111) - this node is a cross-community bridge._
- **Are the 2 inferred relationships involving `Funcionario` (e.g. with `.deveAutenticarComTokenDeSessaoValido()` and `.naoDeveAutenticarAposEncerrarSessao()`) actually correct?**
  _`Funcionario` has 2 INFERRED edges - model-reasoned connections that need verification._
- **What connects `com.vitorraphael:gestor-comercial`, `graphify-out/ (diretório do grafo)`, `graphify-out/GRAPH_REPORT.md` to the rest of the system?**
  _54 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `Caixa` be split into smaller, more focused modules?**
  _Cohesion score 0.05554386703134862 - nodes in this community are weakly interconnected._
- **Should `Categoria` be split into smaller, more focused modules?**
  _Cohesion score 0.05524537173082574 - nodes in this community are weakly interconnected._
- **Should `ExigeGerente` be split into smaller, more focused modules?**
  _Cohesion score 0.07955596669750231 - nodes in this community are weakly interconnected._