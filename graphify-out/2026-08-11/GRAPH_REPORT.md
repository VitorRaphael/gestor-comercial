# Graph Report - .  (2026-08-11)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 1164 nodes · 2735 edges · 134 communities (39 shown, 95 thin omitted)
- Extraction: 84% EXTRACTED · 16% INFERRED · 0% AMBIGUOUS · INFERRED: 441 edges (avg confidence: 0.79)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `649e1ca9`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- org.springframework.http.ResponseEntity
- Impressora
- Produto
- ItemComanda
- desktop/js/app.js
- Mesa
- Gestor Comercial (sistema PDV)
- Comanda
- Override
- Caixa
- MovimentoCaixa
- CategoriaController.java
- RegraDeNegocioException
- MateriaPrima
- ImpressaoService
- static/js/app.js
- ComandaService
- Pagamento
- MovimentoEstoqueService
- CaixaServiceTest
- PerfilFuncionario
- MovimentoEstoque
- Funcionario
- ExigeGerente
- FuncionarioService
- .criarFuncionarioComPin
- CaixaService
- PinHashService
- .login
- org.springframework.data.jpa.repository.JpaRepository
- StatusComanda
- MovimentoCaixaServiceTest.java
- MesaController.java
- mvnw
- manifest.json
- AnalyticsDashboardResponse
- jakarta.persistence.Entity
- GestorComercialApplication
- GestorComercialApplicationTests.java
- Mesa
- StatusMesa
- .equals
- AbrirCaixaRequest.java
- AssociarImpressoraRequest.java
- CategoriaRequest.java
- ErroResponse.java
- FecharCaixaRequest.java
- ImpressoraRequest.java
- ItemComandaRequest.java
- LoginRequest.java
- LoginResponse.java
- MovimentoCaixaRequest.java
- sw.js
- AbrirCaixaRequest
- CaixaResponse
- CaixaService
- com.vitorraphael.gestor_comercial.dto.ProdutoRequest
- com.vitorraphael.gestor_comercial.dto.ProdutoResponse
- ComandaRepository
- ComandaService
- DeleteMapping
- FecharCaixaRequest
- ItemComandaRepository
- MesaRequest
- PatchMapping
- com.vitorraphael:gestor-comercial
- ProdutoRepository
- ProdutoRequest
- ProdutoResponse
- Component
- Override
- Override
- PostMapping
- RequestMapping
- ResponseEntity
- RestController
- GetMapping
- PostMapping
- RequestMapping
- ResponseEntity
- RestController
- GetMapping
- PostMapping
- RequestMapping
- ResponseEntity
- RestController
- GetMapping
- PostMapping
- RequestMapping
- ResponseEntity
- RestController
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
- Entity
- StatusComanda
- Entity
- Entity
- Override
- StatusComanda
- Caixa
- Service
- Comanda
- Service
- Service
- Service
- Service
- BeforeEach
- ComandaRepository
- ExtendWith
- MesaRepository
- Test
- BeforeEach
- CategoriaRepository
- ComandaRepository
- ExtendWith
- ItemComandaService
- MesaRepository
- ProdutoRepository
- Test
- BeforeEach
- CategoriaRepository
- ExtendWith
- ProdutoRepository
- Test

## God Nodes (most connected - your core abstractions)
1. `ItemComanda` - 53 edges
2. `Comanda` - 51 edges
3. `MateriaPrima` - 35 edges
4. `Funcionario` - 33 edges
5. `Caixa` - 30 edges
6. `Produto` - 30 edges
7. `MovimentoCaixa` - 29 edges
8. `Impressora` - 28 edges
9. `api()` - 28 edges
10. `el()` - 27 edges

## Surprising Connections (you probably didn't know these)
- `gestor-comercial (projeto, dono deste grafo)` --conceptually_related_to--> `Gestor Comercial (sistema PDV)`  [INFERRED]
  CLAUDE.md → PLANTA_PROJETO.md
- `ProdutoRepository` --references--> `Produto`  [EXTRACTED]
  src/main/java/com/vitorraphael/gestor_comercial/repository/ProdutoRepository.java → src/main/java/com/vitorraphael/gestor_comercial/model/Produto.java
- `MovimentoCaixa` --references--> `Caixa`  [EXTRACTED]
  src/main/java/com/vitorraphael/gestor_comercial/model/MovimentoCaixa.java → src/main/java/com/vitorraphael/gestor_comercial/model/Caixa.java
- `MovimentoCaixaRepository` --references--> `MovimentoCaixa`  [EXTRACTED]
  src/main/java/com/vitorraphael/gestor_comercial/repository/MovimentoCaixaRepository.java → src/main/java/com/vitorraphael/gestor_comercial/model/MovimentoCaixa.java
- `CaixaServiceTest` --references--> `MovimentoCaixaRepository`  [EXTRACTED]
  src/test/java/com/vitorraphael/gestor_comercial/service/CaixaServiceTest.java → src/main/java/com/vitorraphael/gestor_comercial/repository/MovimentoCaixaRepository.java

## Import Cycles
- None detected.

## Hyperedges (group relationships)
- **Validação de mercado da arquitetura local-first** — planta_projeto_arquitetura_local_first, planta_projeto_consumer_concorrente, planta_projeto_toast_pos [EXTRACTED 1.00]
- **Roteamento de impressão por categoria entre as 3 impressoras reais** — planta_projeto_tabela_roteamento_impressoras, planta_projeto_impressora_caixa, planta_projeto_impressora_trailer_1, planta_projeto_impressora_trailer_2 [EXTRACTED 1.00]
- **Roadmap de fases mapeado aos módulos planejados** — planta_projeto_roadmap_fases, planta_projeto_modulo_cardapio, planta_projeto_modulo_mesas_comandas, planta_projeto_modulo_caixa, planta_projeto_modulo_impressao, planta_projeto_interface_atendente_pwa, planta_projeto_modulo_usuarios_funcionarios, planta_projeto_piloto_grand_chef_pro [EXTRACTED 1.00]

## Communities (134 total, 95 thin omitted)

### Community 0 - "org.springframework.http.ResponseEntity"
Cohesion: 0.05
Nodes (46): com.vitorraphael.gestor_comercial.dto.AbrirCaixaRequest, com.vitorraphael.gestor_comercial.dto.AnalyticsDashboardResponse, com.vitorraphael.gestor_comercial.dto.CaixaResponse, com.vitorraphael.gestor_comercial.dto.FecharCaixaRequest, com.vitorraphael.gestor_comercial.dto.ItemComandaRequest, com.vitorraphael.gestor_comercial.security.ExigeGerente, com.vitorraphael.gestor_comercial.service.SessaoService, ItemCurvaAbc (+38 more)

### Community 1 - "Impressora"
Cohesion: 0.05
Nodes (32): JpaRepository, ImpressoraResponse, Categoria, Entity, Override, Impressora, Entity, Override (+24 more)

### Community 2 - "Produto"
Cohesion: 0.07
Nodes (25): Categoria, com.vitorraphael.gestor_comercial.model.Categoria, com.vitorraphael.gestor_comercial.model.Comanda, com.vitorraphael.gestor_comercial.model.ItemComanda, com.vitorraphael.gestor_comercial.model.Mesa, com.vitorraphael.gestor_comercial.model.StatusComanda, com.vitorraphael.gestor_comercial.model.StatusMesa, com.vitorraphael.gestor_comercial.repository.CategoriaRepository (+17 more)

### Community 3 - "ItemComanda"
Cohesion: 0.06
Nodes (11): Produto, ProdutoService, FichaTecnica, Override, Produto, ItemComanda, Funcionario, Override (+3 more)

### Community 4 - "desktop/js/app.js"
Cohesion: 0.14
Nodes (52): abrirComandaDaMesa(), abrirModalAbrirCaixa(), abrirModalAdicionarItem(), abrirModalBase(), abrirModalCancelarItem(), abrirModalCompraEstoque(), abrirModalEditarMateriaPrima(), abrirModalEditarProduto() (+44 more)

### Community 5 - "Mesa"
Cohesion: 0.09
Nodes (14): RecursoNaoEncontradoException, RegraDeNegocioException, Entity, Override, Mesa, MesaRepository, Service, MesaService (+6 more)

### Community 6 - "Gestor Comercial (sistema PDV)"
Cohesion: 0.06
Nodes (37): gestor-comercial (projeto, dono deste grafo), graphify-out/GRAPH_REPORT.md, graphify-out/ (diretório do grafo), ifood-merchant-api (projeto irmão, regra já estabelecida), Regra Inegociável — Graphify, Arquitetura Local-first (não cloud-first), Banco de dados SQLite, Consumer (concorrente nacional, validação de mercado) (+29 more)

### Community 7 - "Comanda"
Cohesion: 0.14
Nodes (3): PontoMapaCalor, Comanda, Funcionario

### Community 8 - "Override"
Cohesion: 0.11
Nodes (17): AutenticacaoInterceptor, CommandLineRunner, Component, Configuration, FuncionarioRepository, FuncionarioService, InterceptorRegistry, MesaRepository (+9 more)

### Community 9 - "Caixa"
Cohesion: 0.13
Nodes (7): CaixaResponse, Caixa, Entity, Override, StatusCaixa, ABERTO, FECHADO

### Community 10 - "MovimentoCaixa"
Cohesion: 0.13
Nodes (9): MovimentoCaixaResponse, Entity, Override, MovimentoCaixa, TipoMovimento, CONSUMO_FUNCIONARIO, DESPESA, REFORCO (+1 more)

### Community 11 - "CategoriaController.java"
Cohesion: 0.13
Nodes (20): AssociarImpressoraRequest, CategoriaRequest, CategoriaResponse, CategoriaService, ImpressoraRequest, ImpressoraResponse, ImpressoraService, CategoriaController (+12 more)

### Community 12 - "RegraDeNegocioException"
Cohesion: 0.15
Nodes (17): ErroResponse, ExceptionHandler, MethodArgumentNotValidException, MovimentoCaixaRequest, MovimentoCaixaResponse, MovimentoCaixaService, RegraDeNegocioException, RestControllerAdvice (+9 more)

### Community 13 - "MateriaPrima"
Cohesion: 0.14
Nodes (7): Override, MateriaPrima, UnidadeMedida, G, KG, L, UN

### Community 14 - "ImpressaoService"
Cohesion: 0.14
Nodes (14): Logger, ImpressaoController, PostMapping, RequestMapping, ResponseEntity, RestController, ItemImpressaoResponse, ItemComanda (+6 more)

### Community 15 - "static/js/app.js"
Cohesion: 0.22
Nodes (22): abrirMesa(), abrirModalProduto(), abrirModalQuantidade(), api(), aplicarPermissoesDeInterface(), carregarItens(), carregarMesas(), el() (+14 more)

### Community 16 - "ComandaService"
Cohesion: 0.15
Nodes (11): com.vitorraphael.gestor_comercial.dto.PagamentoResponse, com.vitorraphael.gestor_comercial.model.FormaPagamento, com.vitorraphael.gestor_comercial.model.Produto, com.vitorraphael.gestor_comercial.repository.PagamentoRepository, ItemComanda, MesaService, org.springframework.stereotype.Service, ItemComandaRepository (+3 more)

### Community 17 - "Pagamento"
Cohesion: 0.12
Nodes (8): Comanda, PagamentoResponse, FormaPagamento, CREDITO, DEBITO, DINHEIRO, PIX, Pagamento

### Community 18 - "MovimentoEstoqueService"
Cohesion: 0.15
Nodes (8): com.vitorraphael.gestor_comercial.model.MateriaPrima, com.vitorraphael.gestor_comercial.model.MovimentoEstoque, com.vitorraphael.gestor_comercial.model.TipoMovimentoEstoque, com.vitorraphael.gestor_comercial.repository.MovimentoEstoqueRepository, FichaTecnicaService, MateriaPrimaService, MovimentoEstoque, MovimentoEstoqueService

### Community 19 - "CaixaServiceTest"
Cohesion: 0.20
Nodes (8): Caixa, com.vitorraphael.gestor_comercial.model.Caixa, com.vitorraphael.gestor_comercial.model.StatusCaixa, CaixaRepository, CaixaServiceTest, BeforeEach, ExtendWith, Test

### Community 20 - "PerfilFuncionario"
Cohesion: 0.14
Nodes (12): HandlerInterceptor, HttpServletRequest, HttpServletResponse, FuncionarioRequest, AcessoNegadoException, NaoAutorizadoException, PerfilFuncionario, ATENDENTE (+4 more)

### Community 21 - "MovimentoEstoque"
Cohesion: 0.15
Nodes (7): Comanda, Override, MovimentoEstoque, TipoMovimentoEstoque, AJUSTE_MANUAL, ENTRADA_COMPRA, SAIDA_VENDA

### Community 22 - "Funcionario"
Cohesion: 0.22
Nodes (7): Entity, Funcionario, Override, Service, SessaoService, Test, SessaoServiceTest

### Community 23 - "ExigeGerente"
Cohesion: 0.22
Nodes (11): Retention, FuncionarioController, GetMapping, PatchMapping, PostMapping, RequestMapping, ResponseEntity, RestController (+3 more)

### Community 24 - "FuncionarioService"
Cohesion: 0.18
Nodes (6): com.vitorraphael.gestor_comercial.model.Funcionario, com.vitorraphael.gestor_comercial.model.PerfilFuncionario, com.vitorraphael.gestor_comercial.repository.FuncionarioRepository, PinHashService, FuncionarioService, Funcionario

### Community 25 - ".criarFuncionarioComPin"
Cohesion: 0.30
Nodes (5): BeforeEach, ExtendWith, FuncionarioRepository, FuncionarioServiceTest, Test

### Community 26 - "CaixaService"
Cohesion: 0.25
Nodes (4): com.vitorraphael.gestor_comercial.repository.CaixaRepository, com.vitorraphael.gestor_comercial.repository.MovimentoCaixaRepository, PagamentoRepository, CaixaService

### Community 27 - "PinHashService"
Cohesion: 0.27
Nodes (5): SecureRandom, Service, PinHashService, Test, PinHashServiceTest

### Community 28 - ".login"
Cohesion: 0.21
Nodes (3): com.vitorraphael.gestor_comercial.dto.LoginRequest, com.vitorraphael.gestor_comercial.dto.LoginResponse, LoginResponse

### Community 29 - "org.springframework.data.jpa.repository.JpaRepository"
Cohesion: 0.23
Nodes (4): org.springframework.data.jpa.repository.JpaRepository, MateriaPrimaRepository, MovimentoEstoqueRepository, MateriaPrimaService

### Community 30 - "StatusComanda"
Cohesion: 0.21
Nodes (5): StatusComanda, ABERTA, CANCELADA, FECHADA, ComandaRepository

### Community 31 - "MovimentoCaixaServiceTest.java"
Cohesion: 0.30
Nodes (7): MovimentoCaixaRepository, Service, MovimentoCaixaService, BeforeEach, ExtendWith, Test, MovimentoCaixaServiceTest

### Community 32 - "MesaController.java"
Cohesion: 0.36
Nodes (7): GetMapping, MesaResponse, RequestMapping, ResponseEntity, RestController, MesaService, MesaController

### Community 33 - "mvnw"
Cohesion: 0.33
Nodes (6): mvnw script, clean(), die(), exec_maven(), set_java_home(), verbose()

### Community 34 - "manifest.json"
Cohesion: 0.20
Nodes (9): background_color, display, icons, name, orientation, scope, short_name, start_url (+1 more)

### Community 35 - "AnalyticsDashboardResponse"
Cohesion: 0.33
Nodes (5): AnalyticsDashboardResponse, ItemCurvaAbc, ItemMixCategoria, PontoFaturamento, PontoMapaCalor

### Community 38 - "GestorComercialApplicationTests.java"
Cohesion: 0.60
Nodes (3): SpringBootTest, GestorComercialApplicationTests, Test

### Community 40 - "StatusMesa"
Cohesion: 0.50
Nodes (3): StatusMesa, LIVRE, OCUPADA

## Knowledge Gaps
- **71 isolated node(s):** `com.vitorraphael:gestor-comercial`, `graphify-out/ (diretório do grafo)`, `graphify-out/GRAPH_REPORT.md`, `ifood-merchant-api (projeto irmão, regra já estabelecida)`, `Food truck do pai do Vitor (negócio real)` (+66 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **95 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `FuncionarioService` connect `FuncionarioService` to `org.springframework.http.ResponseEntity`, `ItemComanda`, `ComandaService`, `ExigeGerente`, `.criarFuncionarioComPin`, `.login`?**
  _High betweenness centrality (0.043) - this node is a cross-community bridge._
- **Why does `Categoria` connect `Impressora` to `Produto`?**
  _High betweenness centrality (0.043) - this node is a cross-community bridge._
- **Are the 2 inferred relationships involving `Funcionario` (e.g. with `.deveAutenticarComTokenDeSessaoValido()` and `.naoDeveAutenticarAposEncerrarSessao()`) actually correct?**
  _`Funcionario` has 2 INFERRED edges - model-reasoned connections that need verification._
- **What connects `com.vitorraphael:gestor-comercial`, `graphify-out/ (diretório do grafo)`, `graphify-out/GRAPH_REPORT.md` to the rest of the system?**
  _71 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `org.springframework.http.ResponseEntity` be split into smaller, more focused modules?**
  _Cohesion score 0.05003217503217503 - nodes in this community are weakly interconnected._
- **Should `Impressora` be split into smaller, more focused modules?**
  _Cohesion score 0.05238095238095238 - nodes in this community are weakly interconnected._
- **Should `Produto` be split into smaller, more focused modules?**
  _Cohesion score 0.07289002557544758 - nodes in this community are weakly interconnected._