# Graph Report - .  (2026-08-11)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 1232 nodes · 2904 edges · 147 communities (46 shown, 101 thin omitted)
- Extraction: 84% EXTRACTED · 16% INFERRED · 0% AMBIGUOUS · INFERRED: 462 edges (avg confidence: 0.79)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `88bef8f4`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- org.springframework.http.ResponseEntity
- Impressora
- desktop/js/app.js
- ItemComanda
- Produto
- org.junit.jupiter.api.Test
- Gestor Comercial (sistema PDV)
- QuitacaoConsumo
- MovimentoCaixa
- FuncionarioSeeder
- Comanda
- Mesa
- Pagamento
- FichaTecnica
- com.vitorraphael.gestor_comercial.model.Caixa
- ImpressaoService
- MateriaPrima
- static/js/app.js
- AutenticacaoInterceptor.java
- ComandaService
- Caixa
- .registrar
- FormaPagamento
- MovimentoEstoque
- CategoriaController.java
- Funcionario
- ExigeGerente
- FuncionarioServiceTest
- CaixaService
- GlobalExceptionHandler.java
- MovimentoCaixaController.java
- MesaServiceTest.java
- PinHashService
- .criarComanda
- ImpressoraController.java
- org.springframework.data.jpa.repository.JpaRepository
- .buscarPorId
- MesaController.java
- mvnw
- manifest.json
- .login
- FuncionarioService
- jakarta.persistence.Entity
- PerfilFuncionario
- AnalyticsDashboardResponse
- GestorComercialApplication
- GestorComercialApplicationTests.java
- StatusMesa
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
- com.vitorraphael.gestor_comercial.dto.ProdutoRequest
- com.vitorraphael.gestor_comercial.dto.ProdutoResponse
- com.vitorraphael.gestor_comercial.model.ItemComanda
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
- ExtendWith
- Test
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
- ExtendWith
- Test
- BeforeEach
- CategoriaRepository
- ExtendWith
- ProdutoRepository
- Test

## God Nodes (most connected - your core abstractions)
1. `ItemComanda` - 53 edges
2. `Comanda` - 47 edges
3. `MateriaPrima` - 35 edges
4. `Pagamento` - 34 edges
5. `Funcionario` - 33 edges
6. `api()` - 32 edges
7. `el()` - 31 edges
8. `Produto` - 30 edges
9. `Impressora` - 28 edges
10. `MovimentoCaixa` - 26 edges

## Surprising Connections (you probably didn't know these)
- `gestor-comercial (projeto, dono deste grafo)` --conceptually_related_to--> `Gestor Comercial (sistema PDV)`  [INFERRED]
  CLAUDE.md → PLANTA_PROJETO.md
- `MovimentoCaixa` --references--> `Caixa`  [EXTRACTED]
  src/main/java/com/vitorraphael/gestor_comercial/model/MovimentoCaixa.java → src/main/java/com/vitorraphael/gestor_comercial/model/Caixa.java
- `MovimentoCaixaRepository` --references--> `MovimentoCaixa`  [EXTRACTED]
  src/main/java/com/vitorraphael/gestor_comercial/repository/MovimentoCaixaRepository.java → src/main/java/com/vitorraphael/gestor_comercial/model/MovimentoCaixa.java
- `MovimentoCaixaService` --references--> `CaixaService`  [EXTRACTED]
  src/main/java/com/vitorraphael/gestor_comercial/service/MovimentoCaixaService.java → src/main/java/com/vitorraphael/gestor_comercial/service/CaixaService.java
- `ImpressaoService` --references--> `RoteamentoImpressaoService`  [EXTRACTED]
  src/main/java/com/vitorraphael/gestor_comercial/service/ImpressaoService.java → src/main/java/com/vitorraphael/gestor_comercial/service/RoteamentoImpressaoService.java

## Import Cycles
- None detected.

## Hyperedges (group relationships)
- **Validação de mercado da arquitetura local-first** — planta_projeto_arquitetura_local_first, planta_projeto_consumer_concorrente, planta_projeto_toast_pos [EXTRACTED 1.00]
- **Roteamento de impressão por categoria entre as 3 impressoras reais** — planta_projeto_tabela_roteamento_impressoras, planta_projeto_impressora_caixa, planta_projeto_impressora_trailer_1, planta_projeto_impressora_trailer_2 [EXTRACTED 1.00]
- **Roadmap de fases mapeado aos módulos planejados** — planta_projeto_roadmap_fases, planta_projeto_modulo_cardapio, planta_projeto_modulo_mesas_comandas, planta_projeto_modulo_caixa, planta_projeto_modulo_impressao, planta_projeto_interface_atendente_pwa, planta_projeto_modulo_usuarios_funcionarios, planta_projeto_piloto_grand_chef_pro [EXTRACTED 1.00]

## Communities (147 total, 101 thin omitted)

### Community 0 - "org.springframework.http.ResponseEntity"
Cohesion: 0.07
Nodes (38): com.vitorraphael.gestor_comercial.dto.AbrirCaixaRequest, com.vitorraphael.gestor_comercial.dto.AnalyticsDashboardResponse, com.vitorraphael.gestor_comercial.dto.CaixaResponse, com.vitorraphael.gestor_comercial.dto.FecharCaixaRequest, com.vitorraphael.gestor_comercial.dto.ItemComandaRequest, com.vitorraphael.gestor_comercial.dto.PagamentoResponse, com.vitorraphael.gestor_comercial.security.ExigeGerente, com.vitorraphael.gestor_comercial.service.SessaoService (+30 more)

### Community 1 - "Impressora"
Cohesion: 0.05
Nodes (31): JpaRepository, ImpressoraResponse, Categoria, Entity, Override, Impressora, Entity, Override (+23 more)

### Community 2 - "desktop/js/app.js"
Cohesion: 0.14
Nodes (56): abrirComandaDaMesa(), abrirModalAbrirCaixa(), abrirModalAdicionarItem(), abrirModalBase(), abrirModalCancelarItem(), abrirModalCompraEstoque(), abrirModalEditarMateriaPrima(), abrirModalEditarProduto() (+48 more)

### Community 3 - "ItemComanda"
Cohesion: 0.08
Nodes (10): ItemCurvaAbc, ItemMixCategoria, PontoFaturamento, ItemComanda, Comanda, Funcionario, Override, Produto (+2 more)

### Community 4 - "Produto"
Cohesion: 0.10
Nodes (12): Categoria, org.springframework.web.bind.annotation.PatchMapping, org.springframework.web.bind.annotation.PutMapping, ProdutoController, CategoriaResponse, ProdutoRequest, ProdutoResponse, Produto (+4 more)

### Community 5 - "org.junit.jupiter.api.Test"
Cohesion: 0.11
Nodes (22): com.vitorraphael.gestor_comercial.model.Categoria, com.vitorraphael.gestor_comercial.model.Mesa, com.vitorraphael.gestor_comercial.model.Produto, com.vitorraphael.gestor_comercial.model.StatusComanda, com.vitorraphael.gestor_comercial.model.StatusMesa, com.vitorraphael.gestor_comercial.repository.CategoriaRepository, com.vitorraphael.gestor_comercial.repository.ComandaRepository, com.vitorraphael.gestor_comercial.repository.MesaRepository (+14 more)

### Community 6 - "Gestor Comercial (sistema PDV)"
Cohesion: 0.06
Nodes (37): gestor-comercial (projeto, dono deste grafo), graphify-out/GRAPH_REPORT.md, graphify-out/ (diretório do grafo), ifood-merchant-api (projeto irmão, regra já estabelecida), Regra Inegociável — Graphify, Arquitetura Local-first (não cloud-first), Banco de dados SQLite, Consumer (concorrente nacional, validação de mercado) (+29 more)

### Community 7 - "QuitacaoConsumo"
Cohesion: 0.09
Nodes (10): ConsumoRegistroResponse, ConsumosFuncionarioResponse, QuitacaoRegistroResponse, SaldoDevedorResponse, Funcionario, Override, QuitacaoConsumo, QuitacaoConsumoRepository (+2 more)

### Community 8 - "MovimentoCaixa"
Cohesion: 0.11
Nodes (10): MovimentoCaixa, MovimentoCaixaResponse, Entity, Override, MovimentoCaixa, TipoMovimento, CONSUMO_FUNCIONARIO, DESPESA (+2 more)

### Community 9 - "FuncionarioSeeder"
Cohesion: 0.12
Nodes (17): AutenticacaoInterceptor, CommandLineRunner, Component, Configuration, FuncionarioRepository, FuncionarioService, InterceptorRegistry, MesaRepository (+9 more)

### Community 10 - "Comanda"
Cohesion: 0.15
Nodes (4): PontoMapaCalor, Comanda, Funcionario, Override

### Community 11 - "Mesa"
Cohesion: 0.12
Nodes (9): RecursoNaoEncontradoException, RegraDeNegocioException, Entity, Override, Mesa, MesaRepository, Service, MesaService (+1 more)

### Community 12 - "Pagamento"
Cohesion: 0.12
Nodes (6): FormaPagamento, Comanda, Funcionario, Override, Pagamento, PagamentoResponse

### Community 13 - "FichaTecnica"
Cohesion: 0.13
Nodes (6): Produto, FichaTecnica, Override, Produto, FichaTecnicaRepository, FichaTecnicaService

### Community 14 - "com.vitorraphael.gestor_comercial.model.Caixa"
Cohesion: 0.21
Nodes (6): Caixa, com.vitorraphael.gestor_comercial.model.Caixa, com.vitorraphael.gestor_comercial.model.StatusCaixa, CaixaRepository, Caixa, Caixa

### Community 15 - "ImpressaoService"
Cohesion: 0.14
Nodes (14): Logger, ImpressaoController, PostMapping, RequestMapping, ResponseEntity, RestController, ItemImpressaoResponse, ItemComanda (+6 more)

### Community 16 - "MateriaPrima"
Cohesion: 0.14
Nodes (7): Override, MateriaPrima, UnidadeMedida, G, KG, L, UN

### Community 17 - "static/js/app.js"
Cohesion: 0.22
Nodes (22): abrirMesa(), abrirModalProduto(), abrirModalQuantidade(), api(), aplicarPermissoesDeInterface(), carregarItens(), carregarMesas(), el() (+14 more)

### Community 18 - "AutenticacaoInterceptor.java"
Cohesion: 0.16
Nodes (11): HandlerInterceptor, HttpServletRequest, HttpServletResponse, NaoAutorizadoException, AutenticacaoInterceptor, Component, Override, Service (+3 more)

### Community 19 - "ComandaService"
Cohesion: 0.13
Nodes (10): MesaService, ProdutoService, StatusComanda, ABERTA, CANCELADA, FECHADA, ComandaRepository, ItemComandaRepository (+2 more)

### Community 20 - "Caixa"
Cohesion: 0.13
Nodes (7): CaixaResponse, Caixa, Entity, Override, StatusCaixa, ABERTO, FECHADO

### Community 21 - ".registrar"
Cohesion: 0.15
Nodes (8): com.vitorraphael.gestor_comercial.model.MateriaPrima, com.vitorraphael.gestor_comercial.model.MovimentoEstoque, com.vitorraphael.gestor_comercial.model.TipoMovimentoEstoque, com.vitorraphael.gestor_comercial.repository.MovimentoEstoqueRepository, FichaTecnicaService, MateriaPrimaService, MovimentoEstoque, MovimentoEstoqueService

### Community 22 - "FormaPagamento"
Cohesion: 0.13
Nodes (12): com.vitorraphael.gestor_comercial.model.Comanda, com.vitorraphael.gestor_comercial.repository.ItemComandaRepository, ItemComanda, FormaPagamento, CONSUMO_INTERNO, CREDITO, DEBITO, DINHEIRO (+4 more)

### Community 23 - "MovimentoEstoque"
Cohesion: 0.15
Nodes (7): Comanda, Override, MovimentoEstoque, TipoMovimentoEstoque, AJUSTE_MANUAL, ENTRADA_COMPRA, SAIDA_VENDA

### Community 24 - "CategoriaController.java"
Cohesion: 0.23
Nodes (11): AssociarImpressoraRequest, CategoriaRequest, CategoriaResponse, CategoriaService, CategoriaController, GetMapping, PatchMapping, PostMapping (+3 more)

### Community 25 - "Funcionario"
Cohesion: 0.19
Nodes (5): com.vitorraphael.gestor_comercial.model.PerfilFuncionario, Entity, Funcionario, Override, Funcionario

### Community 26 - "ExigeGerente"
Cohesion: 0.24
Nodes (11): Retention, FuncionarioController, GetMapping, PatchMapping, PostMapping, RequestMapping, ResponseEntity, RestController (+3 more)

### Community 27 - "FuncionarioServiceTest"
Cohesion: 0.26
Nodes (5): BeforeEach, ExtendWith, FuncionarioRepository, FuncionarioServiceTest, Test

### Community 28 - "CaixaService"
Cohesion: 0.24
Nodes (10): CaixaService, com.vitorraphael.gestor_comercial.model.FormaPagamento, com.vitorraphael.gestor_comercial.model.MovimentoCaixa, com.vitorraphael.gestor_comercial.model.TipoMovimento, com.vitorraphael.gestor_comercial.repository.CaixaRepository, com.vitorraphael.gestor_comercial.repository.MovimentoCaixaRepository, com.vitorraphael.gestor_comercial.repository.PagamentoRepository, CaixaService (+2 more)

### Community 29 - "GlobalExceptionHandler.java"
Cohesion: 0.30
Nodes (7): ErroResponse, ExceptionHandler, MethodArgumentNotValidException, RestControllerAdvice, GlobalExceptionHandler, ResponseEntity, AcessoNegadoException

### Community 30 - "MovimentoCaixaController.java"
Cohesion: 0.26
Nodes (10): MovimentoCaixaRequest, MovimentoCaixaResponse, MovimentoCaixaService, GetMapping, PostMapping, RequestMapping, ResponseEntity, RestController (+2 more)

### Community 31 - "MesaServiceTest.java"
Cohesion: 0.23
Nodes (7): Mesa, MesaResponse, BeforeEach, ExtendWith, MesaRepository, Test, MesaServiceTest

### Community 32 - "PinHashService"
Cohesion: 0.27
Nodes (5): SecureRandom, Service, PinHashService, Test, PinHashServiceTest

### Community 34 - "ImpressoraController.java"
Cohesion: 0.29
Nodes (9): ImpressoraRequest, ImpressoraResponse, ImpressoraService, ImpressoraController, GetMapping, PostMapping, RequestMapping, ResponseEntity (+1 more)

### Community 35 - "org.springframework.data.jpa.repository.JpaRepository"
Cohesion: 0.19
Nodes (4): org.springframework.data.jpa.repository.JpaRepository, MateriaPrimaRepository, MovimentoEstoqueRepository, MateriaPrimaService

### Community 36 - ".buscarPorId"
Cohesion: 0.27
Nodes (3): MovimentoCaixaRepository, Service, MovimentoCaixaService

### Community 37 - "MesaController.java"
Cohesion: 0.36
Nodes (7): GetMapping, MesaResponse, RequestMapping, ResponseEntity, RestController, MesaService, MesaController

### Community 38 - "mvnw"
Cohesion: 0.33
Nodes (6): mvnw script, clean(), die(), exec_maven(), set_java_home(), verbose()

### Community 39 - "manifest.json"
Cohesion: 0.20
Nodes (9): background_color, display, icons, name, orientation, scope, short_name, start_url (+1 more)

### Community 40 - ".login"
Cohesion: 0.32
Nodes (3): com.vitorraphael.gestor_comercial.dto.LoginRequest, com.vitorraphael.gestor_comercial.dto.LoginResponse, LoginResponse

### Community 41 - "FuncionarioService"
Cohesion: 0.39
Nodes (4): com.vitorraphael.gestor_comercial.model.Funcionario, com.vitorraphael.gestor_comercial.repository.FuncionarioRepository, PinHashService, FuncionarioService

### Community 43 - "PerfilFuncionario"
Cohesion: 0.38
Nodes (4): FuncionarioRequest, PerfilFuncionario, ATENDENTE, GERENTE

### Community 44 - "AnalyticsDashboardResponse"
Cohesion: 0.33
Nodes (5): AnalyticsDashboardResponse, ItemCurvaAbc, ItemMixCategoria, PontoFaturamento, PontoMapaCalor

### Community 46 - "GestorComercialApplicationTests.java"
Cohesion: 0.60
Nodes (3): SpringBootTest, GestorComercialApplicationTests, Test

### Community 47 - "StatusMesa"
Cohesion: 0.50
Nodes (3): StatusMesa, LIVRE, OCUPADA

## Knowledge Gaps
- **73 isolated node(s):** `com.vitorraphael:gestor-comercial`, `graphify-out/ (diretório do grafo)`, `graphify-out/GRAPH_REPORT.md`, `ifood-merchant-api (projeto irmão, regra já estabelecida)`, `Food truck do pai do Vitor (negócio real)` (+68 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **101 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `ItemComanda` connect `ItemComanda` to `org.springframework.http.ResponseEntity`, `org.springframework.data.jpa.repository.JpaRepository`, `org.junit.jupiter.api.Test`, `jakarta.persistence.Entity`, `Comanda`, `ComandaService`, `.registrar`?**
  _High betweenness centrality (0.048) - this node is a cross-community bridge._
- **Why does `MateriaPrima` connect `MateriaPrima` to `org.springframework.http.ResponseEntity`, `org.springframework.data.jpa.repository.JpaRepository`, `org.junit.jupiter.api.Test`, `jakarta.persistence.Entity`, `FichaTecnica`, `.registrar`, `MovimentoEstoque`?**
  _High betweenness centrality (0.048) - this node is a cross-community bridge._
- **Why does `Produto` connect `Produto` to `FuncionarioSeeder`, `jakarta.persistence.Entity`, `org.junit.jupiter.api.Test`?**
  _High betweenness centrality (0.045) - this node is a cross-community bridge._
- **What connects `com.vitorraphael:gestor-comercial`, `graphify-out/ (diretório do grafo)`, `graphify-out/GRAPH_REPORT.md` to the rest of the system?**
  _73 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `org.springframework.http.ResponseEntity` be split into smaller, more focused modules?**
  _Cohesion score 0.06566791510611736 - nodes in this community are weakly interconnected._
- **Should `Impressora` be split into smaller, more focused modules?**
  _Cohesion score 0.05495151337055539 - nodes in this community are weakly interconnected._
- **Should `desktop/js/app.js` be split into smaller, more focused modules?**
  _Cohesion score 0.1367211131276467 - nodes in this community are weakly interconnected._