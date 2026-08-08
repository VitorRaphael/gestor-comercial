const state = {
  sessao: null,
  viewAtual: "mesas",
  categorias: [],
  produtos: [],
  comandaSelecionadaId: null,
  itensComandaSelecionada: [],
  itemModal: { categoriaId: null, produto: null, quantidade: 1 },
};

const el = (id) => document.getElementById(id);

// ---------- Sessão / API ----------

function carregarSessaoSalva() {
  const bruta = localStorage.getItem("sessao");
  if (!bruta) return null;
  try {
    return JSON.parse(bruta);
  } catch (_) {
    return null;
  }
}

function salvarSessao(sessao) {
  state.sessao = sessao;
  localStorage.setItem("sessao", JSON.stringify(sessao));
}

function limparSessao() {
  state.sessao = null;
  localStorage.removeItem("sessao");
}

async function api(metodo, caminho, corpo) {
  const headers = corpo ? { "Content-Type": "application/json" } : {};
  if (state.sessao) headers["Authorization"] = `Bearer ${state.sessao.token}`;

  const resposta = await fetch(caminho, {
    method: metodo,
    headers,
    body: corpo ? JSON.stringify(corpo) : undefined,
  });

  if (!resposta.ok) {
    let mensagem = `Erro ${resposta.status}`;
    try {
      const dados = await resposta.json();
      mensagem = dados.mensagem || mensagem;
    } catch (_) { /* corpo vazio ou não-JSON */ }

    if (resposta.status === 401) {
      limparSessao();
      mostrarTelaLogin();
    }

    throw new Error(mensagem);
  }

  if (resposta.status === 204) return null;
  return resposta.json();
}

function mostrarToast(mensagem, ehErro = false) {
  const toast = el("toast");
  toast.textContent = mensagem;
  toast.className = "toast" + (ehErro ? " erro" : "");
  toast.hidden = false;
  clearTimeout(mostrarToast._timer);
  mostrarToast._timer = setTimeout(() => { toast.hidden = true; }, 3000);
}

function formatarMoeda(valor) {
  return Number(valor).toLocaleString("pt-BR", { style: "currency", currency: "BRL" });
}

function formatarDataHora(iso) {
  return new Date(iso).toLocaleString("pt-BR");
}

// ---------- Modal genérico ----------

let modalConfirmarHandler = null;

function fecharModal() {
  el("modal").hidden = true;
  modalConfirmarHandler = null;
}

el("modal-fechar").addEventListener("click", fecharModal);
el("modal-cancelar").addEventListener("click", fecharModal);
el("modal-confirmar").addEventListener("click", () => {
  if (modalConfirmarHandler) modalConfirmarHandler();
});

function abrirModalBase(titulo, corpoHtml, textoConfirmar, aoConfirmar) {
  el("modal-titulo").textContent = titulo;
  el("modal-corpo").innerHTML = corpoHtml;
  el("modal-confirmar").textContent = textoConfirmar;
  modalConfirmarHandler = aoConfirmar;
  el("modal").hidden = false;
}

function campoHtml(campo) {
  if (campo.tipo === "select") {
    const opcoes = campo.opcoes.map((o) => `<option value="${o.value}">${o.label}</option>`).join("");
    return `<div><label>${campo.label}</label><select id="campo-${campo.nome}">${opcoes}</select></div>`;
  }
  return `<div><label>${campo.label}</label><input id="campo-${campo.nome}" type="${campo.tipo}" ${campo.attrs || ""}></div>`;
}

function abrirModalFormulario(titulo, campos, textoConfirmar, aoConfirmar) {
  const corpoHtml = campos.map(campoHtml).join("");
  abrirModalBase(titulo, corpoHtml, textoConfirmar, async () => {
    const valores = {};
    for (const campo of campos) valores[campo.nome] = el(`campo-${campo.nome}`).value;
    try {
      await aoConfirmar(valores);
      fecharModal();
    } catch (erro) {
      mostrarToast(erro.message, true);
    }
  });
}

// ---------- Navegação ----------

function aplicarPermissoesDeInterface() {
  const ehGerente = state.sessao?.perfil === "GERENTE";
  for (const elemento of document.querySelectorAll("[data-gerente]")) {
    elemento.hidden = !ehGerente;
  }
}

function setView(nome) {
  state.viewAtual = nome;
  for (const secao of document.querySelectorAll(".secao")) {
    secao.hidden = secao.id !== `view-${nome}`;
  }
  for (const item of document.querySelectorAll(".nav-item")) {
    item.classList.toggle("ativo", item.dataset.view === nome);
  }
  el("titulo-secao").textContent = {
    mesas: "Mesas",
    comandas: "Comandas",
    produtos: "Produtos",
    categorias: "Categorias",
    impressoras: "Impressoras",
    caixa: "Caixa",
    funcionarios: "Funcionários",
  }[nome];

  if (nome === "mesas") carregarMesas();
  if (nome === "comandas") carregarComandasAbertas();
  if (nome === "produtos") carregarProdutos();
  if (nome === "categorias") carregarCategorias();
  if (nome === "impressoras") carregarImpressoras();
  if (nome === "caixa") carregarCaixa();
  if (nome === "funcionarios") carregarFuncionarios();
}

for (const item of document.querySelectorAll(".nav-item")) {
  item.addEventListener("click", () => setView(item.dataset.view));
}

// ---------- Autenticação ----------

function mostrarTelaLogin() {
  el("view-login").hidden = false;
  el("app-shell").hidden = true;
  el("input-pin").value = "";
}

async function entrar() {
  const pin = el("input-pin").value.trim();
  if (!pin) { mostrarToast("Informe o PIN.", true); return; }

  try {
    const resposta = await api("POST", "/api/auth/login", { pin });
    salvarSessao(resposta);
    entrarNoApp();
  } catch (erro) {
    mostrarToast(erro.message, true);
  }
}

function entrarNoApp() {
  el("view-login").hidden = true;
  el("app-shell").hidden = false;
  el("usuario-nome").textContent = state.sessao.nome;
  el("usuario-perfil").textContent = state.sessao.perfil === "GERENTE" ? "Gerente" : "Atendente";
  aplicarPermissoesDeInterface();
  setView("mesas");
}

el("btn-entrar").addEventListener("click", entrar);
el("input-pin").addEventListener("keydown", (evento) => {
  if (evento.key === "Enter") entrar();
});

el("btn-sair").addEventListener("click", async () => {
  try {
    await api("POST", "/api/auth/logout");
  } catch (_) { /* encerra localmente mesmo se falhar no servidor */ }
  limparSessao();
  mostrarTelaLogin();
});

// ---------- Mesas ----------

async function carregarMesas() {
  try {
    const mesas = await api("GET", "/api/mesas");
    const grid = el("grid-mesas");
    grid.innerHTML = "";
    for (const mesa of mesas) {
      const ocupada = mesa.status === "OCUPADA";
      const botao = document.createElement("button");
      botao.className = "mesa-card" + (ocupada ? " ocupada" : "");
      botao.innerHTML = `<span>${mesa.numero}</span><span class="mesa-status">${ocupada ? "ocupada" : "livre"}</span>`;
      botao.addEventListener("click", () => abrirComandaDaMesa(mesa));
      grid.appendChild(botao);
    }
  } catch (erro) {
    mostrarToast(erro.message, true);
  }
}

async function abrirComandaDaMesa(mesa) {
  try {
    let comanda;
    if (mesa.status === "OCUPADA") {
      const abertas = await api("GET", "/api/comandas/abertas");
      comanda = abertas.find((c) => c.mesaId === mesa.id);
      if (!comanda) throw new Error("Mesa ocupada mas nenhuma comanda aberta foi encontrada.");
    } else {
      comanda = await api("POST", `/api/mesas/${mesa.id}/comandas`);
    }
    setView("comandas");
    await carregarComandasAbertas();
    await selecionarComanda(comanda.id);
  } catch (erro) {
    mostrarToast(erro.message, true);
  }
}

// ---------- Comandas ----------

async function carregarComandasAbertas() {
  try {
    const comandas = await api("GET", "/api/comandas/abertas");
    const lista = el("lista-comandas");
    lista.innerHTML = "";

    if (comandas.length === 0) {
      lista.innerHTML = `<li class="estado-vazio">Nenhuma comanda aberta.</li>`;
    }

    for (const comanda of comandas) {
      const li = document.createElement("li");
      li.className = "item-comanda-lista" + (comanda.id === state.comandaSelecionadaId ? " selecionada" : "");
      li.innerHTML = `<span class="mesa-num">Mesa ${comanda.mesaNumero}</span>`;
      li.addEventListener("click", () => selecionarComanda(comanda.id));
      lista.appendChild(li);
    }
  } catch (erro) {
    mostrarToast(erro.message, true);
  }
}

async function selecionarComanda(comandaId) {
  state.comandaSelecionadaId = comandaId;
  await carregarComandasAbertas();
  await carregarItensDaComandaSelecionada();
}

async function carregarItensDaComandaSelecionada() {
  if (!state.comandaSelecionadaId) return;
  try {
    const [comanda, itens] = await Promise.all([
      api("GET", `/api/comandas/${state.comandaSelecionadaId}`),
      api("GET", `/api/comandas/${state.comandaSelecionadaId}/itens`),
    ]);
    state.itensComandaSelecionada = itens;
    renderizarDetalheComanda(comanda, itens);
  } catch (erro) {
    mostrarToast(erro.message, true);
  }
}

function renderizarDetalheComanda(comanda, itens) {
  el("comanda-vazia").hidden = true;
  el("comanda-detalhe").hidden = false;

  el("detalhe-mesa-numero").textContent = `Mesa ${comanda.mesaNumero}`;
  const badge = el("detalhe-status");
  badge.textContent = comanda.status;
  badge.className = "badge" + (comanda.status === "FECHADA" ? " fechada" : "");

  const tbody = el("detalhe-itens-tbody");
  tbody.innerHTML = "";
  let total = 0;

  for (const item of itens) {
    const subtotal = item.precoUnitario * item.quantidade;
    total += subtotal;
    const tr = document.createElement("tr");
    tr.innerHTML = `
      <td>${item.produtoNome}${item.observacao ? `<br><span style="color:var(--texto-fraco);font-size:0.8rem">${item.observacao}</span>` : ""}</td>
      <td>${formatarMoeda(item.precoUnitario)}</td>
      <td>${item.quantidade}</td>
      <td>${formatarMoeda(subtotal)}</td>
      <td></td>
    `;
    const tdAcoes = tr.lastElementChild;
    const btnRemover = document.createElement("button");
    btnRemover.className = "botao-link perigo";
    btnRemover.textContent = "Remover";
    btnRemover.addEventListener("click", () => removerItem(item.id));
    tdAcoes.appendChild(btnRemover);
    tbody.appendChild(tr);
  }

  if (itens.length === 0) {
    tbody.innerHTML = `<tr class="tabela-vazia"><td colspan="5">Nenhum item lançado ainda.</td></tr>`;
  }

  el("detalhe-total-valor").textContent = formatarMoeda(total);

  const comandaFechada = comanda.status === "FECHADA";
  el("btn-detalhe-add-item").hidden = comandaFechada;
  el("btn-detalhe-fechar").hidden = comandaFechada;
}

async function removerItem(itemId) {
  try {
    await api("DELETE", `/api/comandas/${state.comandaSelecionadaId}/itens/${itemId}`);
    await carregarItensDaComandaSelecionada();
  } catch (erro) {
    mostrarToast(erro.message, true);
  }
}

el("btn-detalhe-fechar").addEventListener("click", async () => {
  if (!confirm("Fechar esta comanda?")) return;
  try {
    await api("POST", `/api/comandas/${state.comandaSelecionadaId}/fechar`);
    state.comandaSelecionadaId = null;
    el("comanda-detalhe").hidden = true;
    el("comanda-vazia").hidden = false;
    await carregarComandasAbertas();
    await carregarMesas();
  } catch (erro) {
    mostrarToast(erro.message, true);
  }
});

el("btn-detalhe-imprimir").addEventListener("click", async () => {
  try {
    await api("POST", `/api/comandas/${state.comandaSelecionadaId}/imprimir`);
    mostrarToast("Enviado para impressão.");
  } catch (erro) {
    mostrarToast(erro.message, true);
  }
});

el("btn-detalhe-add-item").addEventListener("click", abrirModalAdicionarItem);

async function abrirModalAdicionarItem() {
  try {
    if (state.categorias.length === 0) state.categorias = await api("GET", "/api/categorias");
    state.produtos = await api("GET", "/api/produtos");

    state.itemModal = { categoriaId: state.categorias[0]?.id ?? null, produto: null, quantidade: 1 };

    abrirModalBase("Adicionar item", corpoModalItem(), "Adicionar", confirmarAdicionarItem);
    renderCorpoModalItem();
  } catch (erro) {
    mostrarToast(erro.message, true);
  }
}

function corpoModalItem() {
  return `
    <div id="item-modal-categorias" class="categorias-tabs" style="display:flex;gap:8px;overflow-x:auto;"></div>
    <div id="item-modal-produtos" class="produto-picker"></div>
    <div id="item-modal-stepper" class="stepper"></div>
    <div>
      <label>Observação</label>
      <textarea id="campo-observacao" placeholder="ex: sem cebola"></textarea>
    </div>
  `;
}

function renderCorpoModalItem() {
  const tabsEl = el("item-modal-categorias");
  tabsEl.innerHTML = "";
  for (const categoria of state.categorias) {
    const botao = document.createElement("button");
    botao.className = "botao botao-secundario";
    if (categoria.id === state.itemModal.categoriaId) botao.style.boxShadow = "inset 0 0 0 2px var(--roxo)";
    botao.textContent = categoria.nome;
    botao.addEventListener("click", () => {
      state.itemModal.categoriaId = categoria.id;
      renderCorpoModalItem();
    });
    tabsEl.appendChild(botao);
  }

  const produtosEl = el("item-modal-produtos");
  produtosEl.innerHTML = "";
  const produtosDaCategoria = state.produtos.filter((p) => p.categoriaId === state.itemModal.categoriaId);
  for (const produto of produtosDaCategoria) {
    const div = document.createElement("div");
    div.className = "produto-picker-item" + (state.itemModal.produto?.id === produto.id ? " selecionado" : "");
    div.innerHTML = `<span>${produto.nome}</span><span>${formatarMoeda(produto.preco)}</span>`;
    div.addEventListener("click", () => {
      state.itemModal.produto = produto;
      renderCorpoModalItem();
    });
    produtosEl.appendChild(div);
  }
  if (produtosDaCategoria.length === 0) {
    produtosEl.innerHTML = `<p style="color:var(--texto-fraco)">Nenhum produto nesta categoria.</p>`;
  }

  const stepperEl = el("item-modal-stepper");
  stepperEl.innerHTML = `
    <button id="item-modal-menos" type="button">-</button>
    <span>${state.itemModal.quantidade}</span>
    <button id="item-modal-mais" type="button">+</button>
  `;
  el("item-modal-menos").addEventListener("click", () => {
    state.itemModal.quantidade = Math.max(1, state.itemModal.quantidade - 1);
    renderCorpoModalItem();
  });
  el("item-modal-mais").addEventListener("click", () => {
    state.itemModal.quantidade += 1;
    renderCorpoModalItem();
  });
}

async function confirmarAdicionarItem() {
  if (!state.itemModal.produto) {
    mostrarToast("Selecione um produto.", true);
    return;
  }
  try {
    await api("POST", `/api/comandas/${state.comandaSelecionadaId}/itens`, {
      produtoId: state.itemModal.produto.id,
      quantidade: state.itemModal.quantidade,
      observacao: el("campo-observacao").value || null,
    });
    fecharModal();
    await carregarItensDaComandaSelecionada();
  } catch (erro) {
    mostrarToast(erro.message, true);
  }
}

// ---------- Produtos ----------

async function carregarProdutos() {
  try {
    if (state.categorias.length === 0) state.categorias = await api("GET", "/api/categorias");
    const produtos = await api("GET", "/api/produtos");

    el("produtos-legenda").textContent = `${produtos.length} produto(s) ativo(s).`;

    const tbody = el("produtos-tbody");
    tbody.innerHTML = "";
    const ehGerente = state.sessao?.perfil === "GERENTE";

    for (const produto of produtos) {
      const tr = document.createElement("tr");
      tr.innerHTML = `
        <td>${produto.nome}</td>
        <td>${produto.categoriaNome}</td>
        <td>${formatarMoeda(produto.preco)}</td>
        <td><span class="badge">${produto.ativo ? "ativo" : "inativo"}</span></td>
        <td></td>
      `;
      if (ehGerente) {
        const tdAcoes = tr.lastElementChild;
        const btn = document.createElement("button");
        btn.className = "botao-link perigo";
        btn.textContent = "Desativar";
        btn.hidden = !produto.ativo;
        btn.addEventListener("click", async () => {
          try {
            await api("PATCH", `/api/produtos/${produto.id}/desativar`);
            await carregarProdutos();
          } catch (erro) {
            mostrarToast(erro.message, true);
          }
        });
        tdAcoes.appendChild(btn);
      }
      tbody.appendChild(tr);
    }
    if (produtos.length === 0) {
      tbody.innerHTML = `<tr class="tabela-vazia"><td colspan="5">Nenhum produto cadastrado.</td></tr>`;
    }
  } catch (erro) {
    mostrarToast(erro.message, true);
  }
}

el("btn-novo-produto").addEventListener("click", async () => {
  if (state.categorias.length === 0) state.categorias = await api("GET", "/api/categorias");
  if (state.categorias.length === 0) {
    mostrarToast("Cadastre uma categoria antes de criar um produto.", true);
    return;
  }
  abrirModalFormulario(
    "Novo produto",
    [
      { nome: "nome", label: "Nome", tipo: "text" },
      { nome: "preco", label: "Preço", tipo: "number", attrs: "min='0' step='0.01'" },
      { nome: "categoriaId", label: "Categoria", tipo: "select", opcoes: state.categorias.map((c) => ({ value: c.id, label: c.nome })) },
    ],
    "Criar",
    async (valores) => {
      await api("POST", "/api/produtos", {
        nome: valores.nome,
        preco: Number(valores.preco),
        categoriaId: Number(valores.categoriaId),
      });
      await carregarProdutos();
    },
  );
});

// ---------- Categorias ----------

async function carregarCategorias() {
  try {
    const [categorias, impressoras] = await Promise.all([
      api("GET", "/api/categorias"),
      api("GET", "/api/impressoras"),
    ]);
    state.categorias = categorias;

    const ehGerente = state.sessao?.perfil === "GERENTE";
    const tbody = el("categorias-tbody");
    tbody.innerHTML = "";

    for (const categoria of categorias) {
      const tr = document.createElement("tr");
      const tdNome = document.createElement("td");
      tdNome.textContent = categoria.nome;
      tr.appendChild(tdNome);

      const tdImpressora = document.createElement("td");
      if (ehGerente) {
        const select = document.createElement("select");
        select.innerHTML = `<option value="">Sem impressora</option>` +
          impressoras.map((i) => `<option value="${i.id}" ${categoria.impressoraId === i.id ? "selected" : ""}>${i.nome}</option>`).join("");
        select.addEventListener("change", async () => {
          if (!select.value) return;
          try {
            await api("PATCH", `/api/categorias/${categoria.id}/impressora`, { impressoraId: Number(select.value) });
            mostrarToast("Impressora associada.");
          } catch (erro) {
            mostrarToast(erro.message, true);
          }
        });
        tdImpressora.appendChild(select);
      } else {
        tdImpressora.textContent = categoria.impressoraNome ?? "—";
      }
      tr.appendChild(tdImpressora);
      tr.appendChild(document.createElement("td"));
      tbody.appendChild(tr);
    }
    if (categorias.length === 0) {
      tbody.innerHTML = `<tr class="tabela-vazia"><td colspan="3">Nenhuma categoria cadastrada.</td></tr>`;
    }
  } catch (erro) {
    mostrarToast(erro.message, true);
  }
}

el("btn-nova-categoria").addEventListener("click", () => {
  abrirModalFormulario(
    "Nova categoria",
    [{ nome: "nome", label: "Nome", tipo: "text" }],
    "Criar",
    async (valores) => {
      await api("POST", "/api/categorias", { nome: valores.nome });
      await carregarCategorias();
    },
  );
});

// ---------- Impressoras ----------

async function carregarImpressoras() {
  try {
    const impressoras = await api("GET", "/api/impressoras");
    const tbody = el("impressoras-tbody");
    tbody.innerHTML = impressoras.map((i) => `<tr><td>${i.nome}</td></tr>`).join("");
    if (impressoras.length === 0) {
      tbody.innerHTML = `<tr class="tabela-vazia"><td>Nenhuma impressora cadastrada.</td></tr>`;
    }
  } catch (erro) {
    mostrarToast(erro.message, true);
  }
}

el("btn-nova-impressora").addEventListener("click", () => {
  abrirModalFormulario(
    "Nova impressora",
    [{ nome: "nome", label: "Nome", tipo: "text" }],
    "Criar",
    async (valores) => {
      await api("POST", "/api/impressoras", { nome: valores.nome });
      await carregarImpressoras();
    },
  );
});

// ---------- Caixa ----------

async function carregarCaixa() {
  const cartao = el("caixa-cartao");
  const btnMovimento = el("btn-novo-movimento");

  try {
    const caixa = await api("GET", "/api/caixa/aberto");
    const saldo = await api("GET", `/api/caixa/${caixa.id}/saldo`);

    cartao.innerHTML = `
      <div class="caixa-valores">
        <div><div class="caixa-valor-label">Status</div><div class="caixa-valor">Aberto</div></div>
        <div><div class="caixa-valor-label">Abertura</div><div class="caixa-valor">${formatarMoeda(caixa.valorAbertura)}</div></div>
        <div><div class="caixa-valor-label">Saldo esperado</div><div class="caixa-valor">${formatarMoeda(saldo)}</div></div>
      </div>
      <div class="caixa-acoes"></div>
    `;
    const acoes = cartao.querySelector(".caixa-acoes");
    if (state.sessao?.perfil === "GERENTE") {
      const btnFechar = document.createElement("button");
      btnFechar.className = "botao botao-perigo";
      btnFechar.textContent = "Fechar caixa";
      btnFechar.addEventListener("click", () => abrirModalFecharCaixa(caixa.id));
      acoes.appendChild(btnFechar);
    }

    btnMovimento.hidden = state.sessao?.perfil !== "GERENTE";
    btnMovimento.onclick = () => abrirModalNovoMovimento(caixa.id);

    const movimentos = await api("GET", `/api/caixa/${caixa.id}/movimentos`);
    const tbody = el("movimentos-tbody");
    tbody.innerHTML = movimentos.map((m) => `
      <tr>
        <td>${m.tipo}</td>
        <td>${m.descricao}</td>
        <td>${formatarMoeda(m.valor)}</td>
        <td>${formatarDataHora(m.dataHora)}</td>
      </tr>
    `).join("");
    if (movimentos.length === 0) {
      tbody.innerHTML = `<tr class="tabela-vazia"><td colspan="4">Nenhum movimento registrado.</td></tr>`;
    }
  } catch (_) {
    cartao.innerHTML = `
      <div><div class="caixa-valor-label">Status</div><div class="caixa-valor">Fechado</div></div>
      <div class="caixa-acoes"></div>
    `;
    const acoes = cartao.querySelector(".caixa-acoes");
    if (state.sessao?.perfil === "GERENTE") {
      const btnAbrir = document.createElement("button");
      btnAbrir.className = "botao botao-primario";
      btnAbrir.textContent = "Abrir caixa";
      btnAbrir.addEventListener("click", abrirModalAbrirCaixa);
      acoes.appendChild(btnAbrir);
    }
    btnMovimento.hidden = true;
    el("movimentos-tbody").innerHTML = `<tr class="tabela-vazia"><td colspan="4">Nenhum caixa aberto.</td></tr>`;
  }
}

function abrirModalAbrirCaixa() {
  abrirModalFormulario(
    "Abrir caixa",
    [{ nome: "valorAbertura", label: "Valor de abertura", tipo: "number", attrs: "min='0' step='0.01'" }],
    "Abrir",
    async (valores) => {
      await api("POST", "/api/caixa/abrir", { valorAbertura: Number(valores.valorAbertura) });
      await carregarCaixa();
    },
  );
}

function abrirModalFecharCaixa(caixaId) {
  abrirModalFormulario(
    "Fechar caixa",
    [
      { nome: "valorFechamento", label: "Valor contado no fechamento", tipo: "number", attrs: "min='0' step='0.01'" },
      { nome: "observacao", label: "Observação (opcional)", tipo: "text" },
    ],
    "Fechar",
    async (valores) => {
      await api("POST", `/api/caixa/${caixaId}/fechar`, {
        valorFechamento: Number(valores.valorFechamento),
        observacao: valores.observacao || null,
      });
      await carregarCaixa();
    },
  );
}

function abrirModalNovoMovimento(caixaId) {
  abrirModalFormulario(
    "Novo movimento",
    [
      { nome: "tipo", label: "Tipo", tipo: "select", opcoes: [
        { value: "SANGRIA", label: "Sangria" },
        { value: "REFORCO", label: "Reforço" },
        { value: "DESPESA", label: "Despesa" },
        { value: "CONSUMO_FUNCIONARIO", label: "Consumo de funcionário" },
      ] },
      { nome: "valor", label: "Valor", tipo: "number", attrs: "min='0.01' step='0.01'" },
      { nome: "descricao", label: "Descrição", tipo: "text" },
    ],
    "Registrar",
    async (valores) => {
      await api("POST", "/api/caixa/movimentos", {
        tipo: valores.tipo,
        valor: Number(valores.valor),
        descricao: valores.descricao,
      });
      await carregarCaixa();
    },
  );
}

// ---------- Funcionários ----------

async function carregarFuncionarios() {
  try {
    const funcionarios = await api("GET", "/api/funcionarios");
    const tbody = el("funcionarios-tbody");
    tbody.innerHTML = "";

    for (const funcionario of funcionarios) {
      const tr = document.createElement("tr");
      tr.innerHTML = `
        <td>${funcionario.nome}</td>
        <td><span class="badge ${funcionario.perfil === "GERENTE" ? "gerente" : "atendente"}">${funcionario.perfil}</span></td>
        <td><span class="badge ${funcionario.ativo ? "" : "inativo"}">${funcionario.ativo ? "ativo" : "inativo"}</span></td>
        <td></td>
      `;
      const tdAcoes = tr.lastElementChild;
      const btn = document.createElement("button");
      btn.className = "botao-link perigo";
      btn.textContent = "Desativar";
      btn.hidden = !funcionario.ativo;
      btn.addEventListener("click", async () => {
        try {
          await api("PATCH", `/api/funcionarios/${funcionario.id}/desativar`);
          await carregarFuncionarios();
        } catch (erro) {
          mostrarToast(erro.message, true);
        }
      });
      tdAcoes.appendChild(btn);
      tbody.appendChild(tr);
    }
    if (funcionarios.length === 0) {
      tbody.innerHTML = `<tr class="tabela-vazia"><td colspan="4">Nenhum funcionário cadastrado.</td></tr>`;
    }
  } catch (erro) {
    mostrarToast(erro.message, true);
  }
}

el("btn-novo-funcionario").addEventListener("click", () => {
  abrirModalFormulario(
    "Novo funcionário",
    [
      { nome: "nome", label: "Nome", tipo: "text" },
      { nome: "pin", label: "PIN (4 a 6 dígitos)", tipo: "password", attrs: "maxlength='6' inputmode='numeric' autocomplete='new-password'" },
      { nome: "perfil", label: "Perfil", tipo: "select", opcoes: [
        { value: "ATENDENTE", label: "Atendente" },
        { value: "GERENTE", label: "Gerente" },
      ] },
    ],
    "Criar",
    async (valores) => {
      await api("POST", "/api/funcionarios", { nome: valores.nome, pin: valores.pin, perfil: valores.perfil });
      await carregarFuncionarios();
    },
  );
});

// ---------- Inicialização ----------

const sessaoSalva = carregarSessaoSalva();
if (sessaoSalva) {
  state.sessao = sessaoSalva;
  entrarNoApp();
} else {
  mostrarTelaLogin();
}
