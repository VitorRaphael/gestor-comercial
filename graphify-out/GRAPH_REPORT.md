# Graph Report - .  (2026-08-11)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 1222 nodes · 2896 edges · 143 communities (44 shown, 99 thin omitted)
- Extraction: 84% EXTRACTED · 16% INFERRED · 0% AMBIGUOUS · INFERRED: 461 edges (avg confidence: 0.79)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `649e1ca9`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- org.springframework.http.ResponseEntity
- Impressora
- Produto
- desktop/js/app.js
- RegraDeNegocioException
- Comanda
- Gestor Comercial (sistema PDV)
- ItemComanda
- MateriaPrima
- Mesa
- FuncionarioService
- FuncionarioSeeder
- MovimentoCaixa
- FichaTecnica
- ImpressaoService
- static/js/app.js
- Funcionario
- Pagamento
- MovimentoEstoqueService
- CaixaService
- Caixa
- FuncionarioServiceTest
- MovimentoEstoque
- PagamentoRepository
- AnalyticsService
- ExigeGerente
- CategoriaController.java
- AutenticacaoInterceptor.java
- QuitacaoConsumo
- PinHashService
- .criarCaixa
- PerfilFuncionario
- ImpressoraController.java
- MovimentoCaixaServiceTest.java
- MesaController.java
- mvnw
- manifest.json
- CaixaServiceTest
- FormaPagamento
- PagamentoService
- .findByComandaId
- jakarta.persistence.Entity
- org.springframework.data.jpa.repository.JpaRepository
- AnalyticsDashboardResponse
- GestorComercialApplication
- GestorComercialApplicationTests.java
- StatusMesa
- Override
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
- PagamentoResponse.java
- sw.js
- AbrirCaixaRequest
- CaixaResponse
- CaixaService
- com.vitorraphael.gestor_comercial.dto.ProdutoRequest
- com.vitorraphael.gestor_comercial.dto.ProdutoResponse
- com.vitorraphael.gestor_comercial.model.FormaPagamento
- com.vitorraphael.gestor_comercial.repository.PagamentoRepository
- ComandaRepository
- DeleteMapping
- FecharCaixaRequest
- ItemComandaRepository
- MesaRequest
- PagamentoResponse
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
1. `ItemComanda` - 52 edges
2. `Comanda` - 50 edges
3. `MateriaPrima` - 35 edges
4. `Pagamento` - 34 edges
5. `Funcionario` - 33 edges
6. `api()` - 32 edges
7. `el()` - 31 edges
8. `Caixa` - 30 edges
9. `Produto` - 30 edges
10. `MovimentoCaixa` - 29 edges

## Surprising Connections (you probably didn't know these)
- `gestor-comercial (projeto, dono deste grafo)` --conceptually_related_to--> `Gestor Comercial (sistema PDV)`  [INFERRED]
  CLAUDE.md → PLANTA_PROJETO.md
- `ProdutoRepository` --references--> `Produto`  [EXTRACTED]
  src/main/java/com/vitorraphael/gestor_comercial/repository/ProdutoRepository.java → src/main/java/com/vitorraphael/gestor_comercial/model/Produto.java
- `Caixa` --references--> `StatusCaixa`  [EXTRACTED]
  src/main/java/com/vitorraphael/gestor_comercial/model/Caixa.java → src/main/java/com/vitorraphael/gestor_comercial/model/StatusCaixa.java
- `MovimentoCaixa` --references--> `Caixa`  [EXTRACTED]
  src/main/java/com/vitorraphael/gestor_comercial/model/MovimentoCaixa.java → src/main/java/com/vitorraphael/gestor_comercial/model/Caixa.java
- `MovimentoCaixaRepository` --references--> `MovimentoCaixa`  [EXTRACTED]
  src/main/java/com/vitorraphael/gestor_comercial/repository/MovimentoCaixaRepository.java → src/main/java/com/vitorraphael/gestor_comercial/model/MovimentoCaixa.java

## Import Cycles
- None detected.

## Hyperedges (group relationships)
- **Validação de mercado da arquitetura local-first** — planta_projeto_arquitetura_local_first, planta_projeto_consumer_concorrente, planta_projeto_toast_pos [EXTRACTED 1.00]
- **Roteamento de impressão por categoria entre as 3 impressoras reais** — planta_projeto_tabela_roteamento_impressoras, planta_projeto_impressora_caixa, planta_projeto_impressora_trailer_1, planta_projeto_impressora_trailer_2 [EXTRACTED 1.00]
- **Roadmap de fases mapeado aos módulos planejados** — planta_projeto_roadmap_fases, planta_projeto_modulo_cardapio, planta_projeto_modulo_mesas_comandas, planta_projeto_modulo_caixa, planta_projeto_modulo_impressao, planta_projeto_interface_atendente_pwa, planta_projeto_modulo_usuarios_funcionarios, planta_projeto_piloto_grand_chef_pro [EXTRACTED 1.00]

## Communities (143 total, 99 thin omitted)

### Community 0 - "org.springframework.http.ResponseEntity"
Cohesion: 0.06
Nodes (39): com.vitorraphael.gestor_comercial.dto.AbrirCaixaRequest, com.vitorraphael.gestor_comercial.dto.AnalyticsDashboardResponse, com.vitorraphael.gestor_comercial.dto.CaixaResponse, com.vitorraphael.gestor_comercial.dto.FecharCaixaRequest, com.vitorraphael.gestor_comercial.dto.ItemComandaRequest, com.vitorraphael.gestor_comercial.dto.PagamentoResponse, com.vitorraphael.gestor_comercial.security.ExigeGerente, com.vitorraphael.gestor_comercial.service.SessaoService (+31 more)

### Community 1 - "Impressora"
Cohesion: 0.06
Nodes (30): ImpressoraResponse, Categoria, Entity, Override, Impressora, Entity, Override, CategoriaRepository (+22 more)

### Community 2 - "Produto"
Cohesion: 0.08
Nodes (24): Categoria, com.vitorraphael.gestor_comercial.model.Categoria, com.vitorraphael.gestor_comercial.model.Comanda, com.vitorraphael.gestor_comercial.model.ItemComanda, com.vitorraphael.gestor_comercial.model.Mesa, com.vitorraphael.gestor_comercial.model.StatusComanda, com.vitorraphael.gestor_comercial.model.StatusMesa, com.vitorraphael.gestor_comercial.repository.CategoriaRepository (+16 more)

### Community 3 - "desktop/js/app.js"
Cohesion: 0.14
Nodes (56): abrirComandaDaMesa(), abrirModalAbrirCaixa(), abrirModalAdicionarItem(), abrirModalBase(), abrirModalCancelarItem(), abrirModalCompraEstoque(), abrirModalEditarMateriaPrima(), abrirModalEditarProduto() (+48 more)

### Community 4 - "RegraDeNegocioException"
Cohesion: 0.09
Nodes (24): ErroResponse, ExceptionHandler, MethodArgumentNotValidException, MovimentoCaixaRequest, MovimentoCaixaResponse, MovimentoCaixaService, RecursoNaoEncontradoException, RegraDeNegocioException (+16 more)

### Community 5 - "Comanda"
Cohesion: 0.13
Nodes (5): Mesa, MesaResponse, Comanda, Funcionario, Override

### Community 6 - "Gestor Comercial (sistema PDV)"
Cohesion: 0.06
Nodes (37): gestor-comercial (projeto, dono deste grafo), graphify-out/GRAPH_REPORT.md, graphify-out/ (diretório do grafo), ifood-merchant-api (projeto irmão, regra já estabelecida), Regra Inegociável — Graphify, Arquitetura Local-first (não cloud-first), Banco de dados SQLite, Consumer (concorrente nacional, validação de mercado) (+29 more)

### Community 7 - "ItemComanda"
Cohesion: 0.11
Nodes (4): Produto, ItemComanda, Funcionario, Override

### Community 8 - "MateriaPrima"
Cohesion: 0.12
Nodes (11): MateriaPrimaController, MateriaPrimaRequest, MateriaPrimaResponse, MateriaPrima, UnidadeMedida, G, KG, L (+3 more)

### Community 9 - "Mesa"
Cohesion: 0.10
Nodes (11): JpaRepository, RecursoNaoEncontradoException, RegraDeNegocioException, Entity, Override, Mesa, MesaRepository, ProdutoRepository (+3 more)

### Community 10 - "FuncionarioService"
Cohesion: 0.14
Nodes (17): com.vitorraphael.gestor_comercial.model.PerfilFuncionario, com.vitorraphael.gestor_comercial.model.Produto, com.vitorraphael.gestor_comercial.repository.FuncionarioRepository, MesaService, org.springframework.stereotype.Service, PinHashService, ProdutoService, StatusComanda (+9 more)

### Community 11 - "FuncionarioSeeder"
Cohesion: 0.12
Nodes (17): AutenticacaoInterceptor, CommandLineRunner, Component, Configuration, FuncionarioRepository, FuncionarioService, InterceptorRegistry, MesaRepository (+9 more)

### Community 12 - "MovimentoCaixa"
Cohesion: 0.14
Nodes (9): MovimentoCaixaResponse, Entity, Override, MovimentoCaixa, TipoMovimento, CONSUMO_FUNCIONARIO, DESPESA, REFORCO (+1 more)

### Community 13 - "FichaTecnica"
Cohesion: 0.13
Nodes (5): FichaTecnica, Override, Produto, FichaTecnicaRepository, FichaTecnicaService

### Community 14 - "ImpressaoService"
Cohesion: 0.14
Nodes (14): Logger, ImpressaoController, PostMapping, RequestMapping, ResponseEntity, RestController, ItemImpressaoResponse, ItemComanda (+6 more)

### Community 15 - "static/js/app.js"
Cohesion: 0.22
Nodes (22): abrirMesa(), abrirModalProduto(), abrirModalQuantidade(), api(), aplicarPermissoesDeInterface(), carregarItens(), carregarMesas(), el() (+14 more)

### Community 16 - "Funcionario"
Cohesion: 0.18
Nodes (6): Entity, Funcionario, Override, Funcionario, Test, SessaoServiceTest

### Community 17 - "Pagamento"
Cohesion: 0.13
Nodes (5): Comanda, Funcionario, Override, Pagamento, PagamentoResponse

### Community 18 - "MovimentoEstoqueService"
Cohesion: 0.15
Nodes (8): com.vitorraphael.gestor_comercial.model.MateriaPrima, com.vitorraphael.gestor_comercial.model.MovimentoEstoque, com.vitorraphael.gestor_comercial.model.TipoMovimentoEstoque, com.vitorraphael.gestor_comercial.repository.MovimentoEstoqueRepository, FichaTecnicaService, MateriaPrimaService, MovimentoEstoque, MovimentoEstoqueService

### Community 19 - "CaixaService"
Cohesion: 0.21
Nodes (3): com.vitorraphael.gestor_comercial.repository.CaixaRepository, com.vitorraphael.gestor_comercial.repository.MovimentoCaixaRepository, CaixaService

### Community 20 - "Caixa"
Cohesion: 0.17
Nodes (4): CaixaResponse, Caixa, Entity, Override

### Community 21 - "FuncionarioServiceTest"
Cohesion: 0.19
Nodes (6): BeforeEach, com.vitorraphael.gestor_comercial.model.Funcionario, ExtendWith, FuncionarioRepository, FuncionarioServiceTest, Test

### Community 22 - "MovimentoEstoque"
Cohesion: 0.15
Nodes (7): Comanda, Override, MovimentoEstoque, TipoMovimentoEstoque, AJUSTE_MANUAL, ENTRADA_COMPRA, SAIDA_VENDA

### Community 23 - "PagamentoRepository"
Cohesion: 0.18
Nodes (8): ConsumoRegistroResponse, ConsumosFuncionarioResponse, QuitacaoRegistroResponse, SaldoDevedorResponse, PagamentoRepository, QuitacaoConsumoRepository, FuncionarioService, QuitacaoConsumoService

### Community 24 - "AnalyticsService"
Cohesion: 0.18
Nodes (6): ItemCurvaAbc, ItemMixCategoria, PontoFaturamento, PontoMapaCalor, AnalyticsService, AnalyticsDashboardResponse

### Community 25 - "ExigeGerente"
Cohesion: 0.20
Nodes (11): Retention, FuncionarioController, GetMapping, PatchMapping, PostMapping, RequestMapping, ResponseEntity, RestController (+3 more)

### Community 26 - "CategoriaController.java"
Cohesion: 0.23
Nodes (11): AssociarImpressoraRequest, CategoriaRequest, CategoriaResponse, CategoriaService, CategoriaController, GetMapping, PatchMapping, PostMapping (+3 more)

### Community 27 - "AutenticacaoInterceptor.java"
Cohesion: 0.20
Nodes (9): HandlerInterceptor, HttpServletRequest, HttpServletResponse, NaoAutorizadoException, AutenticacaoInterceptor, Component, Override, Service (+1 more)

### Community 28 - "QuitacaoConsumo"
Cohesion: 0.20
Nodes (3): Funcionario, Override, QuitacaoConsumo

### Community 29 - "PinHashService"
Cohesion: 0.27
Nodes (5): SecureRandom, Service, PinHashService, Test, PinHashServiceTest

### Community 30 - ".criarCaixa"
Cohesion: 0.26
Nodes (4): Caixa, StatusCaixa, ABERTO, FECHADO

### Community 31 - "PerfilFuncionario"
Cohesion: 0.19
Nodes (7): com.vitorraphael.gestor_comercial.dto.LoginRequest, com.vitorraphael.gestor_comercial.dto.LoginResponse, LoginResponse, FuncionarioRequest, PerfilFuncionario, ATENDENTE, GERENTE

### Community 32 - "ImpressoraController.java"
Cohesion: 0.29
Nodes (9): ImpressoraRequest, ImpressoraResponse, ImpressoraService, ImpressoraController, GetMapping, PostMapping, RequestMapping, ResponseEntity (+1 more)

### Community 33 - "MovimentoCaixaServiceTest.java"
Cohesion: 0.27
Nodes (7): MovimentoCaixaRepository, Service, MovimentoCaixaService, BeforeEach, ExtendWith, Test, MovimentoCaixaServiceTest

### Community 34 - "MesaController.java"
Cohesion: 0.36
Nodes (7): GetMapping, MesaResponse, RequestMapping, ResponseEntity, RestController, MesaService, MesaController

### Community 35 - "mvnw"
Cohesion: 0.33
Nodes (6): mvnw script, clean(), die(), exec_maven(), set_java_home(), verbose()

### Community 36 - "manifest.json"
Cohesion: 0.20
Nodes (9): background_color, display, icons, name, orientation, scope, short_name, start_url (+1 more)

### Community 37 - "CaixaServiceTest"
Cohesion: 0.24
Nodes (7): com.vitorraphael.gestor_comercial.model.Caixa, com.vitorraphael.gestor_comercial.model.StatusCaixa, CaixaRepository, CaixaServiceTest, BeforeEach, ExtendWith, Test

### Community 38 - "FormaPagamento"
Cohesion: 0.22
Nodes (6): FormaPagamento, CONSUMO_INTERNO, CREDITO, DEBITO, DINHEIRO, PIX

### Community 39 - "PagamentoService"
Cohesion: 0.32
Nodes (4): ComandaService, ItemComanda, FuncionarioService, PagamentoService

### Community 43 - "AnalyticsDashboardResponse"
Cohesion: 0.33
Nodes (5): AnalyticsDashboardResponse, ItemCurvaAbc, ItemMixCategoria, PontoFaturamento, PontoMapaCalor

### Community 45 - "GestorComercialApplicationTests.java"
Cohesion: 0.60
Nodes (3): SpringBootTest, GestorComercialApplicationTests, Test

### Community 46 - "StatusMesa"
Cohesion: 0.50
Nodes (3): StatusMesa, LIVRE, OCUPADA

## Knowledge Gaps
- **73 isolated node(s):** `com.vitorraphael:gestor-comercial`, `graphify-out/ (diretório do grafo)`, `graphify-out/GRAPH_REPORT.md`, `ifood-merchant-api (projeto irmão, regra já estabelecida)`, `Food truck do pai do Vitor (negócio real)` (+68 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **99 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `Comanda` connect `Comanda` to `org.springframework.http.ResponseEntity`, `ItemComanda`, `.findByComandaId`, `jakarta.persistence.Entity`, `FuncionarioService`, `MovimentoEstoqueService`, `AnalyticsService`?**
  _High betweenness centrality (0.044) - this node is a cross-community bridge._
- **Why does `Produto` connect `Produto` to `org.springframework.http.ResponseEntity`, `jakarta.persistence.Entity`, `FuncionarioSeeder`, `Mesa`?**
  _High betweenness centrality (0.043) - this node is a cross-community bridge._
- **Why does `Categoria` connect `Impressora` to `Produto`?**
  _High betweenness centrality (0.042) - this node is a cross-community bridge._
- **What connects `com.vitorraphael:gestor-comercial`, `graphify-out/ (diretório do grafo)`, `graphify-out/GRAPH_REPORT.md` to the rest of the system?**
  _73 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `org.springframework.http.ResponseEntity` be split into smaller, more focused modules?**
  _Cohesion score 0.06118421052631579 - nodes in this community are weakly interconnected._
- **Should `Impressora` be split into smaller, more focused modules?**
  _Cohesion score 0.05617283950617284 - nodes in this community are weakly interconnected._
- **Should `Produto` be split into smaller, more focused modules?**
  _Cohesion score 0.08090957165520889 - nodes in this community are weakly interconnected._