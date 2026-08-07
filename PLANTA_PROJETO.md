# Planta do Projeto — Gestor Comercial

## 1. Visão Geral

Sistema de gestão comercial (PDV) para comércios físicos — controle de mesas/comandas, cardápio, caixa (abertura/fechamento, sangria, despesas), funcionários e impressão térmica setorizada. Desenvolvido em Java/Spring Boot, para uso real no food truck do pai do Vitor, com objetivo declarado de substituir o sistema pago atualmente usado (**Grand Chef Pro**).

Este projeto é irmão do [`ifood-merchant-api`](https://github.com/VitorRaphael/ifood-merchant-api) (que integra com a API de delivery do iFood) — ambos servem o mesmo negócio real e ambos fazem parte da estratégia de portfólio/currículo do Vitor para conseguir estágio em Sistemas de Informação até ~início de 2027.

## 2. Contexto e Motivação

- O pai do Vitor já opera um food truck físico com mesas/cadeiras para consumo no local, e paga hoje por um gestor de vendas comercial (Grand Chef Pro) para: cadastro de cardápio, abertura de mesas, lançamento de pedidos, observações, controle de caixa (sangria, despesas, consumo de funcionários) e impressão de pedidos/contas.
- Objetivo do Vitor: construir o próprio sistema, do zero, cobrindo essas mesmas funções — tanto para reduzir o custo real do pai quanto como peça de portfólio técnico.
- Assim como o `ifood-merchant-api`, a ideia de longo prazo é este sistema também poder ser oferecido de graça a outros pequenos comércios (troca por depoimento/prova social), então decisões de arquitetura já levam em conta facilidade de distribuição/instalação ("banana-easy").
- Diferença importante em relação ao projeto do iFood: ali o ambiente de desenvolvimento é uma loja de teste/sandbox (baixo risco). Aqui, quando for para produção, é o caixa e o salão reais — erro tem custo operacional imediato. Por isso está planejado um **piloto em paralelo** com o Grand Chef Pro antes de qualquer substituição definitiva.

## 3. Decisões de Arquitetura

**Local-first, não cloud-first.** É um único estabelecimento físico — servidor e dispositivos dos atendentes compartilham a mesma rede Wi-Fi local, sem depender de internet para operar no dia a dia.

- **Servidor**: Spring Boot rodando no computador já existente no caixa. Concentra toda a lógica de negócio, banco de dados e a impressão.
- **Interface do atendente**: aplicativo web (PWA — "adicionar à tela inicial" do navegador do celular), não um app nativo Android. Motivo: evita abrir uma nova trilha de aprendizado (Android/Kotlin) e, como a impressão é centralizada no servidor (ver abaixo), a principal vantagem de um app nativo (acesso direto a hardware) deixa de ser relevante.
- **Impressão**: centralizada no servidor, não no celular do atendente. O servidor recebe o pedido, mapeia cada item por categoria a uma impressora (tabela de roteamento) e envia comandos **ESC/POS** para a impressora correta (rede/USB). Biblioteca planejada: `escpos-coffee` (Java).
- **Sem necessidade de loja de aplicativos** para a v1 (PWA). Se um app nativo vier a ser necessário no futuro, a distribuição seria via APK direto (sideload), sem depender da Play Store.
- **Offline**: não é um problema a resolver — por design, não há dependência de internet no caminho crítico. Sincronização com nuvem (para dashboards remotos, ecoando o padrão do `ifood-merchant-api`) seria *best-effort*, nunca bloqueante.
- **Banco de dados: SQLite**, não MySQL — mesma decisão já validada no `ifood-merchant-api`. Um único arquivo `.db`, sem servidor de banco separado para instalar/configurar, o que facilita tanto a instalação no computador do pai quanto uma futura distribuição para outros comércios.

Essa arquitetura foi validada contra pesquisa de mercado (2026-08-06): a **Consumer** (concorrente nacional) opera exatamente sobre rede Wi-Fi local sem depender de internet; a **Toast POS** (referência americana) usa um "hub local" que mantém a distribuição de pedidos entre terminais mesmo durante queda de internet — confirmando que local-server-first é o padrão maduro do setor, não um atalho.

## 4. Stack Tecnológica

| Camada | Escolha |
|---|---|
| Linguagem | Java 25 (LTS) |
| Framework | Spring Boot 4.1.0 |
| Build | Maven |
| Persistência | Spring Data JPA + Hibernate |
| Banco de dados | SQLite (`sqlite-jdbc` + `hibernate-community-dialects`) |
| Validação | Spring Boot Starter Validation |
| Impressão térmica | ESC/POS via `escpos-coffee` (a integrar na Fase 3) |
| Interface do atendente | Web app / PWA (a definir stack de frontend) |
| Grupo Maven | `com.vitorraphael` |
| Artefato | `gestor-comercial` |

## 5. Configuração de Impressoras (real, confirmada com o Vitor)

O food truck do pai já opera hoje com 3 impressoras térmicas, cada uma vinculada a categorias específicas — esta é a tabela de roteamento real que o sistema precisa reproduzir:

| Impressora | Localização | Categorias impressas |
|---|---|---|
| Impressora do caixa | Caixa central | Fechamento de conta (nota/comprovante), bebidas |
| Impressora do trailer 1 | Trailer 1 | Hambúrguer, espetinhos, porções |
| Impressora do trailer 2 | Trailer 2 | Caipirinha, açaí, sorvete |

Padrão de mercado confirmado por pesquisa: o roteamento por categoria de produto → impressora, com impressão em até ~30s após confirmação do pedido, é como sistemas reais (Saipos, Consumer, Anota AI, Sisfood, Sischef) operam.

## 6. Módulos Planejados

- **Cardápio** — cadastro de produtos, categorias (usadas também para o roteamento de impressão), preços.
- **Mesas/Comandas** — abrir mesa, adicionar/remover itens, observações por item, fechar conta.
- **Caixa** — abertura/fechamento de caixa, sangria, reforço, despesas, consumo de funcionários.
- **Impressão** — roteamento de impressão por categoria de produto (Fase 3).
- **Usuários/Funcionários** — cadastro de atendentes, controle de acesso básico.

Entidades iniciais previstas para a Fase 1 (ainda a desenhar em detalhe): `Produto`, `Mesa`, `Comanda`, `ItemComanda`.

## 7. Roadmap por Fases (rascunho inicial)

- **Fase 1** — Cardápio + Mesas + Comandas (sem impressão ainda). Objetivo: bater pedido, adicionar/remover item, ver comanda aberta.
- **Fase 2** — Caixa: abertura/fechamento, sangria, reforço, despesas.
- **Fase 3** — Impressão térmica roteada por categoria (tabela da seção 5), via ESC/POS.
- **Fase 4** — Interface do atendente (PWA) consumindo a API.
- **Fase 5** — Usuários/funcionários e controle de acesso.
- **Fase 6** — Piloto em paralelo com o Grand Chef Pro, na loja real, antes de qualquer substituição definitiva.

*(Fases sujeitas a ajuste conforme o desenvolvimento avança — este é o rascunho combinado até 2026-08-06, não um compromisso rígido.)*

## 8. Repositório e Distribuição

- Repositório: [`github.com/VitorRaphael/gestor-comercial`](https://github.com/VitorRaphael/gestor-comercial) — público, para fins de portfólio/currículo.
- Licença: nenhuma definida por enquanto ("No license" no GitHub) — decisão consciente: sem licença explícita, o código já fica protegido por copyright padrão (visível para avaliação, mas não legalmente livre para cópia/reuso). Uma licença específica será escolhida quando/se o projeto for formalmente aberto para distribuição a terceiros.
- Visão de longo prazo (não ativa ainda): oferecer o sistema gratuitamente a outros pequenos comércios em troca de depoimento, replicando a estratégia já em curso no `ifood-merchant-api`.

## 9. Perguntas em Aberto

- Stack definitiva do frontend do PWA (ainda não decidida).
- Detalhamento das entidades e seus relacionamentos (a fazer na próxima sessão).
- Como o servidor vai rodar de forma desatendida no computador do caixa (mesmo problema já identificado no projeto do iFood — ver "O problema do produto final e HTML").
- Formato exato de autenticação/permissões por atendente (ainda não definido).
