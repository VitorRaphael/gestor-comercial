const state = {
  mesas: [],
  comandaAtual: null,
  itens: [],
  categorias: [],
  produtos: [],
  categoriaSelecionadaId: null,
  produtoSelecionado: null,
  quantidade: 1,
  sessao: null, // { token, funcionarioId, nome, perfil }
};

const el = (id) => document.getElementById(id);

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
      mostrarViewLogin();
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

function fecharTodosModais() {
  for (const modal of document.querySelectorAll(".modal")) {
    modal.hidden = true;
  }
}

function formatarMoeda(valor) {
  return Number(valor).toLocaleString("pt-BR", { style: "currency", currency: "BRL" });
}

// ---------- Navegação entre telas ----------

function mostrarView(nome) {
  el("view-login").hidden = nome !== "login";
  el("view-mesas").hidden = nome !== "mesas";
  el("view-comanda").hidden = nome !== "comanda";
  el("topbar").hidden = nome === "login";
  el("btn-voltar").hidden = nome === "mesas";
  el("titulo-topo").textContent = nome === "mesas" ? "Mesas" : `Mesa ${state.comandaAtual?.mesaNumero ?? ""}`;
}

function mostrarViewLogin() {
  el("input-pin").value = "";
  mostrarView("login");
}

el("btn-voltar").addEventListener("click", () => {
  state.comandaAtual = null;
  mostrarView("mesas");
  carregarMesas();
});

// ---------- Autenticação ----------

function aplicarPermissoesDeInterface() {
  const ehGerente = state.sessao?.perfil === "GERENTE";
  el("btn-nova-mesa").hidden = !ehGerente;
}

async function entrar() {
  const pin = el("input-pin").value.trim();
  if (!pin) { mostrarToast("Informe o PIN.", true); return; }

  try {
    const resposta = await api("POST", "/api/auth/login", { pin });
    salvarSessao(resposta);
    el("topbar-usuario").textContent = `${resposta.nome} (${resposta.perfil === "GERENTE" ? "gerente" : "atendente"})`;
    aplicarPermissoesDeInterface();
    mostrarView("mesas");
    await carregarMesas();
  } catch (erro) {
    mostrarToast(erro.message, true);
  }
}

el("btn-entrar").addEventListener("click", entrar);
el("input-pin").addEventListener("keydown", (evento) => {
  if (evento.key === "Enter") entrar();
});

el("btn-sair").addEventListener("click", async () => {
  try {
    await api("POST", "/api/auth/logout");
  } catch (_) { /* mesmo se falhar no servidor, encerra localmente */ }
  limparSessao();
  mostrarViewLogin();
});

// ---------- Mesas ----------

async function carregarMesas() {
  try {
    state.mesas = await api("GET", "/api/mesas");
    renderizarMesas();
  } catch (erro) {
    mostrarToast(erro.message, true);
  }
}

function renderizarMesas() {
  const lista = el("lista-mesas");
  lista.innerHTML = "";
  for (const mesa of state.mesas) {
    const ocupada = mesa.status === "OCUPADA";
    const botao = document.createElement("button");
    botao.className = "mesa-card" + (ocupada ? " ocupada" : "");
    botao.innerHTML = `<span>${mesa.numero}</span><span class="mesa-status">${ocupada ? "ocupada" : "livre"}</span>`;
    botao.addEventListener("click", () => abrirMesa(mesa));
    lista.appendChild(botao);
  }
}

async function abrirMesa(mesa) {
  try {
    let comanda;
    if (mesa.status === "OCUPADA") {
      const abertas = await api("GET", "/api/comandas/abertas");
      comanda = abertas.find((c) => c.mesaId === mesa.id);
      if (!comanda) throw new Error("Mesa ocupada mas nenhuma comanda aberta foi encontrada.");
    } else {
      comanda = await api("POST", `/api/mesas/${mesa.id}/comandas`);
    }
    state.comandaAtual = comanda;
    mostrarView("comanda");
    await carregarItens();
  } catch (erro) {
    mostrarToast(erro.message, true);
  }
}

el("btn-nova-mesa").addEventListener("click", () => {
  fecharTodosModais();
  el("input-numero-mesa").value = "";
  el("modal-nova-mesa").hidden = false;
});
el("btn-fechar-modal-mesa").addEventListener("click", () => { el("modal-nova-mesa").hidden = true; });
el("btn-confirmar-mesa").addEventListener("click", async () => {
  const numero = Number(el("input-numero-mesa").value);
  if (!numero || numero <= 0) { mostrarToast("Informe um número de mesa válido.", true); return; }
  try {
    await api("POST", "/api/mesas", { numero });
    el("modal-nova-mesa").hidden = true;
    await carregarMesas();
  } catch (erro) {
    mostrarToast(erro.message, true);
  }
});

// ---------- Comanda ----------

async function carregarItens() {
  try {
    state.itens = await api("GET", `/api/comandas/${state.comandaAtual.id}/itens`);
    renderizarComanda();
  } catch (erro) {
    mostrarToast(erro.message, true);
  }
}

function renderizarComanda() {
  el("comanda-mesa-numero").textContent = `Mesa ${state.comandaAtual.mesaNumero}`;
  const status = el("comanda-status");
  status.textContent = state.comandaAtual.status;
  status.className = "badge" + (state.comandaAtual.status === "FECHADA" ? " fechada" : "");

  const lista = el("lista-itens");
  lista.innerHTML = "";
  let total = 0;

  for (const item of state.itens) {
    const subtotal = item.precoUnitario * item.quantidade;
    total += subtotal;

    const li = document.createElement("li");
    li.className = "item-card";
    li.innerHTML = `
      <div class="item-info">
        <div class="item-nome">${item.quantidade}x ${item.produtoNome}</div>
        ${item.observacao ? `<div class="item-obs">${item.observacao}</div>` : ""}
        <div class="item-preco">${formatarMoeda(subtotal)}</div>
      </div>
      <button class="btn-remover-item" title="Remover">&times;</button>
    `;
    li.querySelector(".btn-remover-item").addEventListener("click", () => removerItem(item.id));
    lista.appendChild(li);
  }

  el("comanda-total-valor").textContent = formatarMoeda(total);

  const comandaFechada = state.comandaAtual.status === "FECHADA";
  el("btn-add-item").hidden = comandaFechada;
  el("btn-fechar-comanda").hidden = comandaFechada;
}

async function removerItem(itemId) {
  try {
    await api("DELETE", `/api/comandas/${state.comandaAtual.id}/itens/${itemId}`);
    await carregarItens();
  } catch (erro) {
    mostrarToast(erro.message, true);
  }
}

el("btn-fechar-comanda").addEventListener("click", async () => {
  if (!confirm("Fechar esta comanda?")) return;
  try {
    state.comandaAtual = await api("POST", `/api/comandas/${state.comandaAtual.id}/fechar`);
    mostrarView("mesas");
    await carregarMesas();
  } catch (erro) {
    mostrarToast(erro.message, true);
  }
});

el("btn-imprimir").addEventListener("click", async () => {
  try {
    await api("POST", `/api/comandas/${state.comandaAtual.id}/imprimir`);
    mostrarToast("Enviado para impressão.");
  } catch (erro) {
    mostrarToast(erro.message, true);
  }
});

// ---------- Modal de adicionar item (categorias + produtos) ----------

el("btn-add-item").addEventListener("click", abrirModalProduto);
el("btn-fechar-modal-produto").addEventListener("click", () => { el("modal-produto").hidden = true; });

async function abrirModalProduto() {
  try {
    if (state.categorias.length === 0) {
      state.categorias = await api("GET", "/api/categorias");
    }
    state.produtos = await api("GET", "/api/produtos");
    state.categoriaSelecionadaId = state.categorias[0]?.id ?? null;
    renderizarCategoriaTabs();
    renderizarProdutos();
    el("modal-produto").hidden = false;
  } catch (erro) {
    mostrarToast(erro.message, true);
  }
}

function renderizarCategoriaTabs() {
  const tabs = el("categorias-tabs");
  tabs.innerHTML = "";
  for (const categoria of state.categorias) {
    const botao = document.createElement("button");
    botao.className = "categoria-tab" + (categoria.id === state.categoriaSelecionadaId ? " ativa" : "");
    botao.textContent = categoria.nome;
    botao.addEventListener("click", () => {
      state.categoriaSelecionadaId = categoria.id;
      renderizarCategoriaTabs();
      renderizarProdutos();
    });
    tabs.appendChild(botao);
  }
}

function renderizarProdutos() {
  const lista = el("lista-produtos");
  lista.innerHTML = "";
  const produtosDaCategoria = state.produtos.filter((p) => p.categoriaId === state.categoriaSelecionadaId);

  for (const produto of produtosDaCategoria) {
    const botao = document.createElement("button");
    botao.className = "produto-card";
    botao.innerHTML = `<span>${produto.nome}</span><span class="produto-preco">${formatarMoeda(produto.preco)}</span>`;
    botao.addEventListener("click", () => abrirModalQuantidade(produto));
    lista.appendChild(botao);
  }

  if (produtosDaCategoria.length === 0) {
    lista.innerHTML = `<p style="color:var(--cor-texto-fraco)">Nenhum produto nesta categoria.</p>`;
  }
}

// ---------- Modal de quantidade/observação ----------

function abrirModalQuantidade(produto) {
  state.produtoSelecionado = produto;
  state.quantidade = 1;
  el("qtd-produto-nome").textContent = produto.nome;
  el("qtd-valor").textContent = "1";
  el("qtd-observacao").value = "";
  el("modal-quantidade").hidden = false;
}

el("btn-fechar-modal-qtd").addEventListener("click", () => { el("modal-quantidade").hidden = true; });
el("btn-qtd-menos").addEventListener("click", () => {
  state.quantidade = Math.max(1, state.quantidade - 1);
  el("qtd-valor").textContent = state.quantidade;
});
el("btn-qtd-mais").addEventListener("click", () => {
  state.quantidade += 1;
  el("qtd-valor").textContent = state.quantidade;
});

el("btn-confirmar-item").addEventListener("click", async () => {
  try {
    await api("POST", `/api/comandas/${state.comandaAtual.id}/itens`, {
      produtoId: state.produtoSelecionado.id,
      quantidade: state.quantidade,
      observacao: el("qtd-observacao").value || null,
    });
    el("modal-quantidade").hidden = true;
    el("modal-produto").hidden = true;
    await carregarItens();
  } catch (erro) {
    mostrarToast(erro.message, true);
  }
});

// ---------- Inicialização ----------

const sessaoSalva = carregarSessaoSalva();
if (sessaoSalva) {
  state.sessao = sessaoSalva;
  el("topbar-usuario").textContent = `${sessaoSalva.nome} (${sessaoSalva.perfil === "GERENTE" ? "gerente" : "atendente"})`;
  aplicarPermissoesDeInterface();
  mostrarView("mesas");
  carregarMesas();
} else {
  mostrarViewLogin();
}

if ("serviceWorker" in navigator) {
  window.addEventListener("load", () => {
    navigator.serviceWorker.register("/sw.js").catch(() => { /* offline shell é best-effort */ });
  });
}
