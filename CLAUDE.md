## 🔴 REGRA INEGOCIÁVEL — Graphify (leia isto ANTES de qualquer outra coisa)

Isto não é uma sugestão, é um HARD GATE, igual ao já estabelecido no projeto `ifood-merchant-api`.

- **ENTRADA (antes de responder qualquer pergunta sobre arquitetura, dependências, "o que usa X", "como Y se conecta com Z", ou antes de ler mais de 1-2 arquivos pra entender o projeto):** consulte `graphify-out/` primeiro (`graphify query "<pergunta>"`, `graphify path`, `graphify explain`) em vez de abrir arquivos um por um. Isso é mais barato e mais rápido que ler tudo de novo.

- **SAÍDA (depois de QUALQUER edição de código — criar, editar ou deletar arquivo/método/classe, não importa o quão pequena):** rode `graphify . --update --code-only` (ou `graphify . --code-only` se não houver grafo ainda) e, se a comunidade/estrutura mudou, `graphify cluster-only .` — ANTES de encerrar a resposta, não depois, não "na próxima vez".

- **Checagem de sanidade:** se `git rev-parse HEAD` (curto) não bater com o "Built from commit" no topo de `graphify-out/GRAPH_REPORT.md`, o grafo está desatualizado — atualize antes de confiar nele.

Este grafo é próprio do `gestor-comercial` — não confundir nem misturar com o grafo do `ifood-merchant-api`, que é um projeto separado.
