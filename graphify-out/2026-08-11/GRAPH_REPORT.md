# Graph Report - .  (2026-08-11)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 863 nodes · 1878 edges · 68 communities (34 shown, 34 thin omitted)
- Extraction: 88% EXTRACTED · 12% INFERRED · 0% AMBIGUOUS · INFERRED: 233 edges (avg confidence: 0.79)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `bca171cd`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- Caixa
- Categoria
- ComandaServiceTest.java
- ExigeGerente
- desktop/js/app.js
- Gestor Comercial (sistema PDV)
- ItemComandaServiceTest.java
- ProdutoService
- ImpressaoService
- static/js/app.js
- Comanda
- Mesa
- CategoriaController.java
- ItemComanda
- ItemComandaController.java
- Produto
- ItemComandaService.java
- CaixaController.java
- FuncionarioService
- ProdutoServiceTest
- FuncionarioServiceTest
- Funcionario
- GlobalExceptionHandler.java
- SessaoService
- PinHashService
- .login
- .criar
- WebConfig
- MesaController.java
- mvnw
- manifest.json
- ComandaService
- GestorComercialApplication
- GestorComercialApplicationTests.java
- StatusMesa
- AbrirCaixaRequest.java
- AssociarImpressoraRequest.java
- CategoriaRequest.java
- ErroResponse.java
- FecharCaixaRequest.java
- ImpressoraRequest.java
- MovimentoCaixaRequest.java
- ProdutoRequest.java
- sw.js
- MesaRequest
- PatchMapping
- com.vitorraphael:gestor-comercial
- ProdutoRepository
- ProdutoRequest
- ProdutoResponse
- Component
- Override
- Override
- GetMapping
- PostMapping
- RequestMapping
- ResponseEntity
- RestController
- GetMapping
- PatchMapping
- PostMapping
- RequestMapping
- ResponseEntity
- RestController
- Service
- ComandaRepository
- MesaRepository

## God Nodes (most connected - your core abstractions)
1. `Funcionario` - 40 edges
2. `Caixa` - 38 edges
3. `Categoria` - 31 edges
4. `MovimentoCaixa` - 30 edges
5. `Comanda` - 29 edges
6. `Impressora` - 28 edges
7. `ItemComanda` - 24 edges
8. `Mesa` - 23 edges
9. `Produto` - 21 edges
10. `api()` - 21 edges

## Surprising Connections (you probably didn't know these)
- `gestor-comercial (projeto, dono deste grafo)` --conceptually_related_to--> `Gestor Comercial (sistema PDV)`  [INFERRED]
  CLAUDE.md → PLANTA_PROJETO.md
- `Comanda` --references--> `Mesa`  [EXTRACTED]
  src/main/java/com/vitorraphael/gestor_comercial/model/Comanda.java → src/main/java/com/vitorraphael/gestor_comercial/model/Mesa.java
- `ItemComanda` --references--> `Comanda`  [EXTRACTED]
  src/main/java/com/vitorraphael/gestor_comercial/model/ItemComanda.java → src/main/java/com/vitorraphael/gestor_comercial/model/Comanda.java
- `ComandaRepository` --references--> `Comanda`  [EXTRACTED]
  src/main/java/com/vitorraphael/gestor_comercial/repository/ComandaRepository.java → src/main/java/com/vitorraphael/gestor_comercial/model/Comanda.java
- `ItemComanda` --references--> `Produto`  [EXTRACTED]
  src/main/java/com/vitorraphael/gestor_comercial/model/ItemComanda.java → src/main/java/com/vitorraphael/gestor_comercial/model/Produto.java

## Import Cycles
- None detected.

## Hyperedges (group relationships)
- **Validação de mercado da arquitetura local-first** — planta_projeto_arquitetura_local_first, planta_projeto_consumer_concorrente, planta_projeto_toast_pos [EXTRACTED 1.00]
- **Roteamento de impressão por categoria entre as 3 impressoras reais** — planta_projeto_tabela_roteamento_impressoras, planta_projeto_impressora_caixa, planta_projeto_impressora_trailer_1, planta_projeto_impressora_trailer_2 [EXTRACTED 1.00]
- **Roadmap de fases mapeado aos módulos planejados** — planta_projeto_roadmap_fases, planta_projeto_modulo_cardapio, planta_projeto_modulo_mesas_comandas, planta_projeto_modulo_caixa, planta_projeto_modulo_impressao, planta_projeto_interface_atendente_pwa, planta_projeto_modulo_usuarios_funcionarios, planta_projeto_piloto_grand_chef_pro [EXTRACTED 1.00]

## Communities (68 total, 34 thin omitted)

### Community 0 - "Caixa"
Cohesion: 0.06
Nodes (30): CaixaResponse, MovimentoCaixaResponse, Caixa, Entity, Override, Entity, Override, MovimentoCaixa (+22 more)

### Community 1 - "Categoria"
Cohesion: 0.06
Nodes (30): ImpressoraResponse, Categoria, Entity, Override, Impressora, Entity, Override, CategoriaRepository (+22 more)

### Community 2 - "ComandaServiceTest.java"
Cohesion: 0.09
Nodes (23): Comanda, ComandaService, Mesa, MesaService, ComandaController, GetMapping, PostMapping, RequestMapping (+15 more)

### Community 3 - "ExigeGerente"
Cohesion: 0.08
Nodes (30): ImpressoraRequest, ImpressoraResponse, ImpressoraService, MovimentoCaixaRequest, MovimentoCaixaResponse, MovimentoCaixaService, Retention, FuncionarioController (+22 more)

### Community 4 - "desktop/js/app.js"
Cohesion: 0.17
Nodes (39): abrirComandaDaMesa(), abrirModalAbrirCaixa(), abrirModalAdicionarItem(), abrirModalBase(), abrirModalEditarProduto(), abrirModalFecharCaixa(), abrirModalFormulario(), abrirModalNovaImpressora() (+31 more)

### Community 5 - "Gestor Comercial (sistema PDV)"
Cohesion: 0.06
Nodes (37): gestor-comercial (projeto, dono deste grafo), graphify-out/GRAPH_REPORT.md, graphify-out/ (diretório do grafo), ifood-merchant-api (projeto irmão, regra já estabelecida), Regra Inegociável — Graphify, Arquitetura Local-first (não cloud-first), Banco de dados SQLite, Consumer (concorrente nacional, validação de mercado) (+29 more)

### Community 6 - "ItemComandaServiceTest.java"
Cohesion: 0.11
Nodes (21): ComandaRepository, CommandLineRunner, Component, FuncionarioRepository, FuncionarioService, ItemComandaRepository, MesaRepository, FuncionarioSeeder (+13 more)

### Community 7 - "ProdutoService"
Cohesion: 0.14
Nodes (15): com.vitorraphael.gestor_comercial.dto.ProdutoRequest, com.vitorraphael.gestor_comercial.dto.ProdutoResponse, com.vitorraphael.gestor_comercial.model.Produto, com.vitorraphael.gestor_comercial.repository.ProdutoRepository, com.vitorraphael.gestor_comercial.security.ExigeGerente, org.springframework.http.ResponseEntity, org.springframework.stereotype.Service, org.springframework.web.bind.annotation.GetMapping (+7 more)

### Community 8 - "ImpressaoService"
Cohesion: 0.14
Nodes (14): Logger, ImpressaoController, PostMapping, RequestMapping, ResponseEntity, RestController, ItemImpressaoResponse, ItemComanda (+6 more)

### Community 9 - "static/js/app.js"
Cohesion: 0.22
Nodes (22): abrirMesa(), abrirModalProduto(), abrirModalQuantidade(), api(), aplicarPermissoesDeInterface(), carregarItens(), carregarMesas(), el() (+14 more)

### Community 10 - "Comanda"
Cohesion: 0.15
Nodes (4): Comanda, Entity, Override, StatusComanda

### Community 11 - "Mesa"
Cohesion: 0.17
Nodes (7): Entity, Override, Mesa, MesaRepository, Service, MesaService, StatusMesa

### Community 12 - "CategoriaController.java"
Cohesion: 0.18
Nodes (13): AssociarImpressoraRequest, Categoria, CategoriaRequest, CategoriaResponse, CategoriaService, CategoriaController, GetMapping, PatchMapping (+5 more)

### Community 13 - "ItemComanda"
Cohesion: 0.15
Nodes (3): ItemComanda, Entity, Override

### Community 14 - "ItemComandaController.java"
Cohesion: 0.20
Nodes (11): DeleteMapping, ItemComanda, ItemComandaService, ItemComandaController, GetMapping, PostMapping, RequestMapping, ResponseEntity (+3 more)

### Community 15 - "Produto"
Cohesion: 0.13
Nodes (5): JpaRepository, Entity, Override, Produto, ProdutoRepository

### Community 16 - "ItemComandaService.java"
Cohesion: 0.17
Nodes (8): RecursoNaoEncontradoException, RegraDeNegocioException, StatusComanda, ABERTA, FECHADA, ItemComandaRepository, ItemComandaService, Service

### Community 17 - "CaixaController.java"
Cohesion: 0.26
Nodes (10): AbrirCaixaRequest, CaixaResponse, CaixaService, FecharCaixaRequest, CaixaController, GetMapping, PostMapping, RequestMapping (+2 more)

### Community 18 - "FuncionarioService"
Cohesion: 0.18
Nodes (9): RecursoNaoEncontradoException, RegraDeNegocioException, AuthController, PostMapping, RequestMapping, ResponseEntity, RestController, FuncionarioService (+1 more)

### Community 19 - "ProdutoServiceTest"
Cohesion: 0.20
Nodes (9): Produto, ProdutoService, ProdutoResponse, BeforeEach, CategoriaRepository, ExtendWith, ProdutoRepository, Test (+1 more)

### Community 20 - "FuncionarioServiceTest"
Cohesion: 0.26
Nodes (5): BeforeEach, ExtendWith, FuncionarioRepository, FuncionarioServiceTest, Test

### Community 21 - "Funcionario"
Cohesion: 0.28
Nodes (5): Entity, Funcionario, Override, Test, SessaoServiceTest

### Community 22 - "GlobalExceptionHandler.java"
Cohesion: 0.30
Nodes (7): ErroResponse, ExceptionHandler, MethodArgumentNotValidException, RestControllerAdvice, GlobalExceptionHandler, ResponseEntity, AcessoNegadoException

### Community 23 - "SessaoService"
Cohesion: 0.22
Nodes (9): HandlerInterceptor, HttpServletRequest, HttpServletResponse, NaoAutorizadoException, AutenticacaoInterceptor, Component, Override, Service (+1 more)

### Community 24 - "PinHashService"
Cohesion: 0.25
Nodes (5): SecureRandom, Service, PinHashService, Test, PinHashServiceTest

### Community 26 - ".criar"
Cohesion: 0.24
Nodes (4): FuncionarioRequest, PerfilFuncionario, ATENDENTE, GERENTE

### Community 27 - "WebConfig"
Cohesion: 0.27
Nodes (7): AutenticacaoInterceptor, Configuration, InterceptorRegistry, Override, WebConfig, ViewControllerRegistry, WebMvcConfigurer

### Community 28 - "MesaController.java"
Cohesion: 0.36
Nodes (7): GetMapping, MesaResponse, RequestMapping, ResponseEntity, RestController, MesaService, MesaController

### Community 29 - "mvnw"
Cohesion: 0.33
Nodes (6): mvnw script, clean(), die(), exec_maven(), set_java_home(), verbose()

### Community 30 - "manifest.json"
Cohesion: 0.20
Nodes (9): background_color, display, icons, name, orientation, scope, short_name, start_url (+1 more)

### Community 31 - "ComandaService"
Cohesion: 0.33
Nodes (4): ComandaRepository, StatusComanda, ComandaService, Service

### Community 33 - "GestorComercialApplicationTests.java"
Cohesion: 0.60
Nodes (3): SpringBootTest, GestorComercialApplicationTests, Test

### Community 34 - "StatusMesa"
Cohesion: 0.50
Nodes (3): StatusMesa, LIVRE, OCUPADA

## Knowledge Gaps
- **53 isolated node(s):** `com.vitorraphael:gestor-comercial`, `graphify-out/ (diretório do grafo)`, `graphify-out/GRAPH_REPORT.md`, `ifood-merchant-api (projeto irmão, regra já estabelecida)`, `Food truck do pai do Vitor (negócio real)` (+48 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **34 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `Categoria` connect `Categoria` to `ProdutoServiceTest`, `ProdutoService`, `ItemComandaServiceTest.java`, `Produto`?**
  _High betweenness centrality (0.103) - this node is a cross-community bridge._
- **Why does `Funcionario` connect `Funcionario` to `ExigeGerente`, `FuncionarioService`, `FuncionarioServiceTest`, `SessaoService`, `.login`, `.criar`?**
  _High betweenness centrality (0.067) - this node is a cross-community bridge._
- **Are the 2 inferred relationships involving `Funcionario` (e.g. with `.deveAutenticarComTokenDeSessaoValido()` and `.naoDeveAutenticarAposEncerrarSessao()`) actually correct?**
  _`Funcionario` has 2 INFERRED edges - model-reasoned connections that need verification._
- **What connects `com.vitorraphael:gestor-comercial`, `graphify-out/ (diretório do grafo)`, `graphify-out/GRAPH_REPORT.md` to the rest of the system?**
  _53 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `Caixa` be split into smaller, more focused modules?**
  _Cohesion score 0.05554386703134862 - nodes in this community are weakly interconnected._
- **Should `Categoria` be split into smaller, more focused modules?**
  _Cohesion score 0.05540499849442939 - nodes in this community are weakly interconnected._
- **Should `ComandaServiceTest.java` be split into smaller, more focused modules?**
  _Cohesion score 0.09178743961352658 - nodes in this community are weakly interconnected._