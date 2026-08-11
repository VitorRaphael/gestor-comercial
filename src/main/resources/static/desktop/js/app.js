const state = {
  sessao: null,
  viewAtual: "mesas",
  categorias: [],
  produtos: [],
  comandaSelecionadaId: null,
  itensComandaSelecionada: [],
  itemModal: { categoriaId: null, produto: null, quantidade: 1 },
  pagamentoModal: { comandaId: null, totalConta: 0, totalPago: 0, restante: 0, troco: null },
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
    const opcoes = campo.opcoes
      .map((o) => `<option value="${o.value}" ${String(o.value) === String(campo.valor) ? "selected" : ""}>${o.label}</option>`)
      .join("");
    return `<div><label>${campo.label}</label><select id="campo-${campo.nome}">${opcoes}</select></div>`;
  }
  const valor = campo.valor != null ? `value="${campo.valor}"` : "";
  return `<div><label>${campo.label}</label><input id="campo-${campo.nome}" type="${campo.tipo}" ${valor} ${campo.attrs || ""}></div>`;
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
    cardapio: "Cardápio",
    impressoras: "Impressoras",
    caixa: "Caixa",
    estoque: "Estoque",
    analytics: "Sales Analytics",
    funcionarios: "Funcionários",
  }[nome];

  if (nome === "mesas") carregarMesas();
  if (nome === "comandas") carregarComandasAbertas();
  if (nome === "cardapio") carregarCardapio();
  if (nome === "impressoras") carregarImpressoras();
  if (nome === "caixa") carregarCaixa();
  if (nome === "estoque") carregarEstoque();
  if (nome === "analytics") carregarAnalytics();
  if (nome === "funcionarios") { carregarFuncionarios(); carregarSaldoDevedor(); }
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
  badge.className = "badge" + (comanda.status === "FECHADA" ? " fechada" : comanda.status === "CANCELADA" ? " cancelada" : "");

  const tbody = el("detalhe-itens-tbody");
  tbody.innerHTML = "";
  let total = 0;

  for (const item of itens) {
    const subtotal = item.precoUnitario * item.quantidade;
    if (!item.cancelado) total += subtotal;
    const tr = document.createElement("tr");
    if (item.cancelado) tr.classList.add("item-comanda-cancelado");
    tr.innerHTML = `
      <td>${item.produtoNome}${item.observacao ? `<br><span style="color:var(--texto-fraco);font-size:0.8rem">${item.observacao}</span>` : ""}${item.cancelado ? `<br><span class="item-comanda-cancelado-motivo">Cancelado: ${item.motivoCancelamento || ""}</span>` : ""}</td>
      <td>${formatarMoeda(item.precoUnitario)}</td>
      <td>${item.quantidade}</td>
      <td>${formatarMoeda(subtotal)}</td>
      <td></td>
    `;
    const tdAcoes = tr.lastElementChild;
    if (!item.cancelado) {
      const btnRemover = document.createElement("button");
      btnRemover.className = "botao-link perigo";
      btnRemover.textContent = "Remover";
      btnRemover.addEventListener("click", () => removerItem(item.id));
      tdAcoes.appendChild(btnRemover);

      const btnCancelar = document.createElement("button");
      btnCancelar.className = "botao-link perigo";
      btnCancelar.textContent = "Cancelar";
      btnCancelar.addEventListener("click", () => abrirModalCancelarItem(item.id));
      tdAcoes.appendChild(btnCancelar);
    }
    tbody.appendChild(tr);
  }

  if (itens.length === 0) {
    tbody.innerHTML = `<tr class="tabela-vazia"><td colspan="5">Nenhum item lançado ainda.</td></tr>`;
  }

  el("detalhe-total-valor").textContent = formatarMoeda(total);

  const comandaFechada = comanda.status !== "ABERTA";
  el("btn-detalhe-add-item").hidden = comandaFechada;
  el("btn-detalhe-fechar").hidden = comandaFechada;
  el("btn-detalhe-cancelar-mesa").hidden = comandaFechada;
}

async function removerItem(itemId) {
  try {
    await api("DELETE", `/api/comandas/${state.comandaSelecionadaId}/itens/${itemId}`);
    await carregarItensDaComandaSelecionada();
  } catch (erro) {
    mostrarToast(erro.message, true);
  }
}

el("btn-detalhe-fechar").addEventListener("click", () => {
  abrirModalPagamento(state.comandaSelecionadaId);
});

// ---------- Cancelamento (item / mesa) ----------

function corpoModalCancelamento() {
  return `
    <div>
      <label>PIN do gerente</label>
      <input id="campo-pin-gerente" type="password" inputmode="numeric" autocomplete="off">
    </div>
    <div>
      <label>Motivo</label>
      <textarea id="campo-motivo-cancelamento" placeholder="ex: cliente saiu sem pagar"></textarea>
    </div>
  `;
}

function abrirModalCancelarItem(itemId) {
  abrirModalBase("Cancelar item", corpoModalCancelamento(), "Cancelar item", async () => {
    try {
      const pin = el("campo-pin-gerente").value.trim();
      const motivo = el("campo-motivo-cancelamento").value.trim();
      if (!pin || !motivo) { mostrarToast("Informe o PIN do gerente e o motivo.", true); return; }

      await api("POST", `/api/comandas/${state.comandaSelecionadaId}/itens/${itemId}/cancelar`, { pin, motivo });
      fecharModal();
      await carregarItensDaComandaSelecionada();
      mostrarToast("Item cancelado.");
    } catch (erro) {
      mostrarToast(erro.message, true);
    }
  });
}

el("btn-detalhe-cancelar-mesa").addEventListener("click", () => {
  abrirModalBase("Cancelar mesa", corpoModalCancelamento(), "Cancelar mesa", async () => {
    try {
      const pin = el("campo-pin-gerente").value.trim();
      const motivo = el("campo-motivo-cancelamento").value.trim();
      if (!pin || !motivo) { mostrarToast("Informe o PIN do gerente e o motivo.", true); return; }

      await api("POST", `/api/comandas/${state.comandaSelecionadaId}/cancelar`, { pin, motivo });
      fecharModal();
      state.comandaSelecionadaId = null;
      el("comanda-detalhe").hidden = true;
      el("comanda-vazia").hidden = false;
      await carregarComandasAbertas();
      await carregarMesas();
      mostrarToast("Mesa cancelada.");
    } catch (erro) {
      mostrarToast(erro.message, true);
    }
  });
});

function corpoModalPagamento() {
  const pg = state.pagamentoModal;
  return `
    <div class="pagamento-resumo">
      <div><span>Total da conta</span><strong>${formatarMoeda(pg.totalConta)}</strong></div>
      <div><span>Total pago</span><strong>${formatarMoeda(pg.totalPago)}</strong></div>
      <div><span>Restante</span><strong>${formatarMoeda(pg.restante)}</strong></div>
      ${pg.troco != null ? `<div><span>Troco</span><strong>${formatarMoeda(pg.troco)}</strong></div>` : ""}
    </div>
    <div>
      <label>Forma de pagamento</label>
      <select id="campo-forma-pagamento">
        <option value="CREDITO">Crédito</option>
        <option value="DEBITO">Débito</option>
        <option value="DINHEIRO">Dinheiro</option>
        <option value="PIX">Pix</option>
        <option value="CONSUMO_INTERNO">Consumo Interno</option>
      </select>
    </div>
    <div>
      <label>Valor</label>
      <input id="campo-valor-pagamento" type="number" min="0.01" step="0.01" value="${pg.restante.toFixed(2)}">
    </div>
    <div id="campos-consumo-interno" hidden>
      <div>
        <label>PIN do gerente</label>
        <input id="campo-pin-consumo-interno" type="password" inputmode="numeric" autocomplete="off">
      </div>
      <div>
        <label>Funcionário consumidor</label>
        <select id="campo-funcionario-consumidor"></select>
      </div>
    </div>
  `;
}

async function popularFuncionarioConsumidor() {
  try {
    const funcionarios = await api("GET", "/api/funcionarios");
    const select = el("campo-funcionario-consumidor");
    select.innerHTML = funcionarios
      .filter((f) => f.ativo)
      .map((f) => `<option value="${f.id}">${f.nome}</option>`)
      .join("");
  } catch (erro) {
    mostrarToast(erro.message, true);
  }
}

function abrirModalPagamento(comandaId) {
  const totalConta = state.itensComandaSelecionada
    .filter((item) => !item.cancelado)
    .reduce((soma, item) => soma + item.precoUnitario * item.quantidade, 0);
  state.pagamentoModal = { comandaId, totalConta, totalPago: 0, restante: totalConta, troco: null };
  abrirModalBase("Pagamento da comanda", corpoModalPagamento(), "Registrar pagamento", confirmarPagamento);
  el("campo-forma-pagamento").addEventListener("change", (ev) => {
    const consumoInterno = ev.target.value === "CONSUMO_INTERNO";
    el("campos-consumo-interno").hidden = !consumoInterno;
    if (consumoInterno) popularFuncionarioConsumidor();
  });
}

async function confirmarPagamento() {
  const formaPagamento = el("campo-forma-pagamento").value;
  const valor = Number(el("campo-valor-pagamento").value);
  const corpo = { formaPagamento, valor };
  if (formaPagamento === "CONSUMO_INTERNO") {
    corpo.pin = el("campo-pin-consumo-interno").value.trim();
    corpo.funcionarioConsumidorId = Number(el("campo-funcionario-consumidor").value);
    if (!corpo.pin || !corpo.funcionarioConsumidorId) {
      mostrarToast("Informe o PIN do gerente e o funcionário consumidor.", true);
      return;
    }
  }
  try {
    const resposta = await api("POST", `/api/comandas/${state.pagamentoModal.comandaId}/pagamentos`, corpo);
    state.pagamentoModal.totalConta = resposta.totalConta;
    state.pagamentoModal.totalPago = resposta.totalPago;
    state.pagamentoModal.restante = resposta.restante;
    state.pagamentoModal.troco = resposta.troco;

    if (resposta.comandaFechada) {
      fecharModal();
      state.comandaSelecionadaId = null;
      el("comanda-detalhe").hidden = true;
      el("comanda-vazia").hidden = false;
      await carregarComandasAbertas();
      await carregarMesas();
      const trocoFinal = resposta.troco;
      mostrarToast(trocoFinal ? `Comanda fechada. Troco: ${formatarMoeda(trocoFinal)}` : "Comanda fechada.");
    } else {
      el("modal-corpo").innerHTML = corpoModalPagamento();
      el("campo-forma-pagamento").addEventListener("change", (ev) => {
        const consumoInterno = ev.target.value === "CONSUMO_INTERNO";
        el("campos-consumo-interno").hidden = !consumoInterno;
        if (consumoInterno) popularFuncionarioConsumidor();
      });
    }
  } catch (erro) {
    mostrarToast(erro.message, true);
  }
}

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

    state.itemModal = { busca: "", produto: null, quantidade: 1 };

    abrirModalBase("Adicionar item", corpoModalItem(), "Adicionar", confirmarAdicionarItem);
    renderCorpoModalItem();
  } catch (erro) {
    mostrarToast(erro.message, true);
  }
}

function corpoModalItem() {
  return `
    <div>
      <input type="text" id="item-modal-busca" placeholder="Buscar produto..." autocomplete="off" />
    </div>
    <div id="item-modal-produtos" class="produto-picker"></div>
    <div id="item-modal-stepper" class="stepper"></div>
    <div>
      <label>Observação</label>
      <textarea id="campo-observacao" placeholder="ex: sem cebola"></textarea>
    </div>
  `;
}

function renderCorpoModalItem() {
  const buscaEl = el("item-modal-busca");
  if (buscaEl.value !== state.itemModal.busca) buscaEl.value = state.itemModal.busca;
  if (!buscaEl.dataset.ligado) {
    buscaEl.dataset.ligado = "1";
    buscaEl.addEventListener("input", () => {
      state.itemModal.busca = buscaEl.value;
      renderCorpoModalItem();
    });
  }

  const termo = state.itemModal.busca.trim().toLowerCase();
  const produtosEl = el("item-modal-produtos");
  produtosEl.innerHTML = "";
  const produtosFiltrados = termo
    ? state.produtos.filter((p) => p.nome.toLowerCase().includes(termo))
    : state.produtos;
  for (const produto of produtosFiltrados) {
    const div = document.createElement("div");
    div.className = "produto-picker-item" + (state.itemModal.produto?.id === produto.id ? " selecionado" : "");
    div.innerHTML = `<span>${produto.nome}</span><span>${formatarMoeda(produto.preco)}</span>`;
    div.addEventListener("click", () => {
      state.itemModal.produto = produto;
      renderCorpoModalItem();
    });
    produtosEl.appendChild(div);
  }
  if (produtosFiltrados.length === 0) {
    produtosEl.innerHTML = `<p style="color:var(--texto-fraco)">Nenhum produto encontrado.</p>`;
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

// ---------- Cardápio (Categorias + Produtos) ----------

async function carregarCardapio() {
  try {
    const [categorias, produtos] = await Promise.all([
      api("GET", "/api/categorias"),
      api("GET", "/api/produtos"),
    ]);
    state.categorias = categorias;
    state.produtos = produtos;

    const ehGerente = state.sessao?.perfil === "GERENTE";
    const lista = el("cardapio-lista");
    lista.innerHTML = "";

    if (categorias.length === 0) {
      lista.innerHTML = `<p class="secao-legenda">Nenhuma categoria cadastrada ainda.</p>`;
      return;
    }

    for (const categoria of categorias) {
      const produtosDaCategoria = produtos.filter((p) => p.categoriaId === categoria.id);

      const secao = document.createElement("div");
      secao.className = "cardapio-categoria";

      const cabecalho = document.createElement("div");
      cabecalho.className = "cardapio-categoria-header";
      cabecalho.innerHTML = `
        <div class="cardapio-categoria-titulo">
          <strong>${categoria.nome}</strong>
          <span class="badge">${categoria.impressoraNome ?? "sem impressora"}</span>
        </div>
      `;
      if (ehGerente) {
        const btnNovoProduto = document.createElement("button");
        btnNovoProduto.className = "botao botao-secundario";
        btnNovoProduto.textContent = "+ Produto";
        btnNovoProduto.addEventListener("click", () => abrirModalNovoProduto(categoria.id));
        cabecalho.appendChild(btnNovoProduto);
      }
      secao.appendChild(cabecalho);

      const tabela = document.createElement("table");
      tabela.className = "tabela";
      tabela.innerHTML = `
        <thead><tr><th>Nome</th><th>Preço</th><th>Status</th><th data-gerente></th></tr></thead>
        <tbody></tbody>
      `;
      const tbody = tabela.querySelector("tbody");

      for (const produto of produtosDaCategoria) {
        const tr = document.createElement("tr");
        tr.innerHTML = `
          <td>${produto.nome}</td>
          <td>${formatarMoeda(produto.preco)}</td>
          <td><span class="badge">${produto.ativo ? "ativo" : "inativo"}</span></td>
          <td></td>
        `;
        if (ehGerente) {
          const tdAcoes = tr.lastElementChild;

          const btnEditar = document.createElement("button");
          btnEditar.className = "botao-link";
          btnEditar.textContent = "Editar";
          btnEditar.addEventListener("click", () => abrirModalEditarProduto(produto));
          tdAcoes.appendChild(btnEditar);

          const btnDesativar = document.createElement("button");
          btnDesativar.className = "botao-link perigo";
          btnDesativar.textContent = "Desativar";
          btnDesativar.hidden = !produto.ativo;
          btnDesativar.addEventListener("click", async () => {
            try {
              await api("PATCH", `/api/produtos/${produto.id}/desativar`);
              await carregarCardapio();
            } catch (erro) {
              mostrarToast(erro.message, true);
            }
          });
          tdAcoes.appendChild(btnDesativar);
        }
        tbody.appendChild(tr);
      }
      if (produtosDaCategoria.length === 0) {
        tbody.innerHTML = `<tr class="tabela-vazia"><td colspan="4">Nenhum produto nesta categoria.</td></tr>`;
      }

      secao.appendChild(tabela);
      lista.appendChild(secao);
    }

    aplicarPermissoesDeInterface();
  } catch (erro) {
    mostrarToast(erro.message, true);
  }
}

function abrirModalNovoProduto(categoriaIdPreSelecionada) {
  abrirModalFormulario(
    "Novo produto",
    [
      { nome: "nome", label: "Nome", tipo: "text" },
      { nome: "preco", label: "Preço", tipo: "number", attrs: "min='0' step='0.01'" },
      { nome: "custo", label: "Custo (opcional)", tipo: "number", attrs: "min='0' step='0.01'" },
      {
        nome: "categoriaId",
        label: "Categoria",
        tipo: "select",
        opcoes: state.categorias.map((c) => ({ value: c.id, label: c.nome })),
        valor: categoriaIdPreSelecionada,
      },
    ],
    "Criar",
    async (valores) => {
      await api("POST", "/api/produtos", {
        nome: valores.nome,
        preco: Number(valores.preco),
        custo: valores.custo ? Number(valores.custo) : null,
        categoriaId: Number(valores.categoriaId),
      });
      await carregarCardapio();
    },
  );
}

function abrirModalEditarProduto(produto) {
  abrirModalFormulario(
    "Editar produto",
    [
      { nome: "nome", label: "Nome", tipo: "text", valor: produto.nome },
      { nome: "preco", label: "Preço", tipo: "number", attrs: "min='0' step='0.01'", valor: produto.preco },
      { nome: "custo", label: "Custo (opcional)", tipo: "number", attrs: "min='0' step='0.01'", valor: produto.custo },
      {
        nome: "categoriaId",
        label: "Categoria",
        tipo: "select",
        opcoes: state.categorias.map((c) => ({ value: c.id, label: c.nome })),
        valor: produto.categoriaId,
      },
    ],
    "Salvar",
    async (valores) => {
      await api("PUT", `/api/produtos/${produto.id}`, {
        nome: valores.nome,
        preco: Number(valores.preco),
        custo: valores.custo ? Number(valores.custo) : null,
        categoriaId: Number(valores.categoriaId),
      });
      await carregarCardapio();
    },
  );
}

el("btn-nova-categoria").addEventListener("click", () => {
  abrirModalFormulario(
    "Nova categoria",
    [{ nome: "nome", label: "Nome", tipo: "text" }],
    "Criar",
    async (valores) => {
      await api("POST", "/api/categorias", { nome: valores.nome });
      await carregarCardapio();
    },
  );
});

// ---------- Impressoras ----------

async function carregarImpressoras() {
  try {
    const [impressoras, categorias] = await Promise.all([
      api("GET", "/api/impressoras"),
      api("GET", "/api/categorias"),
    ]);
    state.categorias = categorias;

    const tbody = el("impressoras-tbody");
    tbody.innerHTML = impressoras.map((i) => {
      const vinculadas = categorias.filter((c) => c.impressoraId === i.id).map((c) => c.nome);
      return `<tr><td>${i.nome}</td><td>${vinculadas.length > 0 ? vinculadas.join(", ") : "—"}</td></tr>`;
    }).join("");
    if (impressoras.length === 0) {
      tbody.innerHTML = `<tr class="tabela-vazia"><td colspan="2">Nenhuma impressora cadastrada.</td></tr>`;
    }
  } catch (erro) {
    mostrarToast(erro.message, true);
  }
}

el("btn-nova-impressora").addEventListener("click", async () => {
  if (state.categorias.length === 0) state.categorias = await api("GET", "/api/categorias");
  abrirModalNovaImpressora();
});

function abrirModalNovaImpressora() {
  const corpoHtml = `
    <div><label>Nome</label><input id="campo-nome-impressora" type="text"></div>
    <div>
      <label>Categorias vinculadas a esta impressora</label>
      <div id="impressora-categorias-checkboxes" class="checkbox-lista">
        ${state.categorias.length === 0
          ? `<p class="secao-legenda">Nenhuma categoria cadastrada ainda — crie categorias no Cardápio.</p>`
          : state.categorias.map((c) => `
              <label class="checkbox-item">
                <input type="checkbox" value="${c.id}">
                ${c.nome}${c.impressoraNome ? ` <span class="secao-legenda">(hoje: ${c.impressoraNome})</span>` : ""}
              </label>
            `).join("")}
      </div>
    </div>
  `;

  abrirModalBase("Nova impressora", corpoHtml, "Criar", async () => {
    const nome = el("campo-nome-impressora").value.trim();
    if (!nome) {
      mostrarToast("Informe o nome da impressora.", true);
      return;
    }
    const categoriaIdsSelecionadas = [...document.querySelectorAll("#impressora-categorias-checkboxes input:checked")]
      .map((input) => Number(input.value));

    try {
      const impressora = await api("POST", "/api/impressoras", { nome });
      for (const categoriaId of categoriaIdsSelecionadas) {
        await api("PATCH", `/api/categorias/${categoriaId}/impressora`, { impressoraId: impressora.id });
      }
      fecharModal();
      await carregarImpressoras();
    } catch (erro) {
      mostrarToast(erro.message, true);
    }
  });
}

// ---------- Caixa ----------

async function carregarCaixa() {
  const cartao = el("caixa-cartao");
  const btnMovimento = el("btn-novo-movimento");

  try {
    const caixa = await api("GET", "/api/caixa/aberto");
    const saldo = await api("GET", `/api/caixa/${caixa.id}/saldo`);

    cartao.innerHTML = `
      <div class="caixa-valores">
        <div><div class="caixa-valor-label">Status</div><div class="caixa-valor caixa-status-aberto"><span class="luz-status luz-verde"></span> Aberto</div></div>
        <div><div class="caixa-valor-label">Aberto desde</div><div class="caixa-valor">${formatarDataHora(caixa.dataAbertura)}</div></div>
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
    let auditoriaHtml = "";
    try {
      const ultimoFechado = await api("GET", "/api/caixa/ultimo-fechado");
      auditoriaHtml = `
        <div><div class="caixa-valor-label">Aberto em</div><div class="caixa-valor">${formatarDataHora(ultimoFechado.dataAbertura)}</div></div>
        <div><div class="caixa-valor-label">Fechado em</div><div class="caixa-valor">${formatarDataHora(ultimoFechado.dataFechamento)}</div></div>
      `;
    } catch (_) { /* nenhum caixa foi fechado ainda */ }

    cartao.innerHTML = `
      <div class="caixa-valores">
        <div><div class="caixa-valor-label">Status</div><div class="caixa-valor caixa-status-fechado"><span class="luz-status luz-vermelha"></span> Fechado</div></div>
        ${auditoriaHtml}
      </div>
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
  const corpoHtml = `
    <div><label>Valor contado no fechamento</label><input id="campo-valorFechamento" type="number" min="0" step="0.01"></div>
    <div><label>Vendido nas maquininhas</label><input id="campo-vendidoMaquininha" type="number" min="0" step="0.01"></div>
    <div><label>Observação (opcional)</label><input id="campo-observacao" type="text"></div>
    <button id="btn-completar-fechamento" type="button" class="botao-link">Completar</button>
  `;

  abrirModalBase("Fechar caixa", corpoHtml, "Fechar", async () => {
    const valorFechamento = Number(el("campo-valorFechamento").value);
    const observacao = el("campo-observacao").value;
    try {
      await api("POST", `/api/caixa/${caixaId}/fechar`, {
        valorFechamento,
        observacao: observacao || null,
      });
      fecharModal();
      await carregarCaixa();
    } catch (erro) {
      mostrarToast(erro.message, true);
    }
  });

  // TODO remover em produção
  el("btn-completar-fechamento").addEventListener("click", async () => {
    try {
      const [saldo, vendidoMaquininha] = await Promise.all([
        api("GET", `/api/caixa/${caixaId}/saldo`),
        api("GET", `/api/caixa/${caixaId}/vendas-maquininha`),
      ]);
      el("campo-valorFechamento").value = Number(saldo).toFixed(2);
      el("campo-vendidoMaquininha").value = Number(vendidoMaquininha).toFixed(2);
    } catch (erro) {
      mostrarToast(erro.message, true);
    }
  });
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

// ---------- Saldo devedor (consumo interno) ----------

async function carregarSaldoDevedor() {
  try {
    const lista = await api("GET", "/api/funcionarios/saldo-devedor");
    const tbody = el("saldo-devedor-tbody");
    tbody.innerHTML = "";

    for (const item of lista) {
      const tr = document.createElement("tr");
      tr.innerHTML = `
        <td>${item.nome}</td>
        <td>${formatarMoeda(item.saldoDevedor)}</td>
        <td></td>
      `;
      const tdAcoes = tr.lastElementChild;

      const btnDetalhe = document.createElement("button");
      btnDetalhe.className = "botao-link";
      btnDetalhe.textContent = "Detalhes";
      btnDetalhe.addEventListener("click", () => alternarDetalheConsumo(item.funcionarioId, trDetalhe));
      tdAcoes.appendChild(btnDetalhe);

      const btnQuitar = document.createElement("button");
      btnQuitar.className = "botao-link";
      btnQuitar.textContent = "Quitar";
      btnQuitar.addEventListener("click", () => abrirModalQuitarConsumo(item.funcionarioId, item.nome, item.saldoDevedor));
      tdAcoes.appendChild(btnQuitar);

      tbody.appendChild(tr);

      const trDetalhe = document.createElement("tr");
      trDetalhe.hidden = true;
      trDetalhe.innerHTML = `<td colspan="3"></td>`;
      tbody.appendChild(trDetalhe);
    }

    if (lista.length === 0) {
      tbody.innerHTML = `<tr class="tabela-vazia"><td colspan="3">Nenhum funcionário com saldo devedor.</td></tr>`;
    }
  } catch (erro) {
    mostrarToast(erro.message, true);
  }
}

async function alternarDetalheConsumo(funcionarioId, trDetalhe) {
  if (!trDetalhe.hidden) {
    trDetalhe.hidden = true;
    return;
  }
  try {
    const dados = await api("GET", `/api/funcionarios/${funcionarioId}/consumos`);
    const linhasConsumo = dados.consumos
      .map((c) => `<tr><td>${new Date(c.dataHora).toLocaleString("pt-BR")}</td><td>${c.mesa}</td><td>${formatarMoeda(c.valor)}</td><td>${formatarMoeda(c.valorQuitado)}</td></tr>`)
      .join("");
    const linhasQuitacao = dados.quitacoes
      .map((q) => `<tr><td>${new Date(q.dataHora).toLocaleString("pt-BR")}</td><td>${formatarMoeda(q.valor)}</td><td>${q.autorizadoPor}</td></tr>`)
      .join("");
    trDetalhe.querySelector("td").innerHTML = `
      <div class="detalhe-consumo">
        <strong>Consumos</strong>
        <table class="tabela">
          <thead><tr><th>Data</th><th>Origem</th><th>Valor</th><th>Quitado</th></tr></thead>
          <tbody>${linhasConsumo || `<tr class="tabela-vazia"><td colspan="4">Nenhum consumo.</td></tr>`}</tbody>
        </table>
        <strong>Quitações</strong>
        <table class="tabela">
          <thead><tr><th>Data</th><th>Valor</th><th>Autorizado por</th></tr></thead>
          <tbody>${linhasQuitacao || `<tr class="tabela-vazia"><td colspan="3">Nenhuma quitação.</td></tr>`}</tbody>
        </table>
      </div>
    `;
    trDetalhe.hidden = false;
  } catch (erro) {
    mostrarToast(erro.message, true);
  }
}

function abrirModalQuitarConsumo(funcionarioId, nome, saldoDevedor) {
  const corpoHtml = `
    <div class="pagamento-resumo">
      <div><span>Funcionário</span><strong>${nome}</strong></div>
      <div><span>Saldo devedor</span><strong>${formatarMoeda(saldoDevedor)}</strong></div>
    </div>
    <div>
      <label>Valor a quitar</label>
      <input id="campo-valor-quitar" type="number" min="0.01" step="0.01" value="${saldoDevedor.toFixed(2)}">
    </div>
    <div>
      <label>PIN do gerente</label>
      <input id="campo-pin-quitar" type="password" inputmode="numeric" autocomplete="off">
    </div>
  `;
  abrirModalBase("Quitar consumo interno", corpoHtml, "Quitar", async () => {
    try {
      const valor = Number(el("campo-valor-quitar").value);
      const pin = el("campo-pin-quitar").value.trim();
      if (!valor || !pin) { mostrarToast("Informe o valor e o PIN do gerente.", true); return; }

      await api("POST", `/api/funcionarios/${funcionarioId}/quitar`, { valor, pin });
      fecharModal();
      await carregarSaldoDevedor();
      mostrarToast("Consumo quitado.");
    } catch (erro) {
      mostrarToast(erro.message, true);
    }
  });
}

// ---------- Estoque ----------

state.materiasPrimas = [];

async function carregarEstoque() {
  try {
    const [materiasPrimas, produtos] = await Promise.all([
      api("GET", "/api/materias-primas"),
      state.produtos.length ? Promise.resolve(state.produtos) : api("GET", "/api/produtos"),
    ]);
    state.materiasPrimas = materiasPrimas;
    state.produtos = produtos;

    const tbody = el("materias-primas-tbody");
    tbody.innerHTML = "";
    for (const materiaPrima of materiasPrimas) {
      const tr = document.createElement("tr");
      tr.innerHTML = `
        <td>${materiaPrima.nome}</td>
        <td>${materiaPrima.unidadeMedida}</td>
        <td>${materiaPrima.quantidadeEstoque}${materiaPrima.abaixoDoMinimo ? ' <span class="badge perigo">abaixo do mínimo</span>' : ""}</td>
        <td>${materiaPrima.quantidadeMinima}</td>
        <td></td>
        <td></td>
      `;
      const [tdCompra, tdEditar] = [tr.children[4], tr.children[5]];

      const btnCompra = document.createElement("button");
      btnCompra.className = "botao-link";
      btnCompra.textContent = "+ Compra";
      btnCompra.addEventListener("click", () => abrirModalCompraEstoque(materiaPrima));
      tdCompra.appendChild(btnCompra);

      const btnEditar = document.createElement("button");
      btnEditar.className = "botao-link";
      btnEditar.textContent = "Editar";
      btnEditar.addEventListener("click", () => abrirModalEditarMateriaPrima(materiaPrima));
      tdEditar.appendChild(btnEditar);

      tbody.appendChild(tr);
    }
    if (materiasPrimas.length === 0) {
      tbody.innerHTML = `<tr class="tabela-vazia"><td colspan="6">Nenhuma matéria-prima cadastrada.</td></tr>`;
    }

    const select = el("ficha-tecnica-produto-select");
    const produtoSelecionadoAnterior = select.value;
    select.innerHTML = produtos.map((p) => `<option value="${p.id}">${p.nome}</option>`).join("");
    if (produtoSelecionadoAnterior) select.value = produtoSelecionadoAnterior;

    await carregarFichaTecnica();
  } catch (erro) {
    mostrarToast(erro.message, true);
  }
}

async function carregarFichaTecnica() {
  const produtoId = el("ficha-tecnica-produto-select").value;
  if (!produtoId) return;

  try {
    const ficha = await api("GET", `/api/fichas-tecnicas?produtoId=${produtoId}`);
    const tbody = el("ficha-tecnica-tbody");
    tbody.innerHTML = "";
    for (const item of ficha) {
      const tr = document.createElement("tr");
      tr.innerHTML = `
        <td>${item.materiaPrimaNome}</td>
        <td>${item.quantidadeUsada}</td>
        <td></td>
      `;
      const btnRemover = document.createElement("button");
      btnRemover.className = "botao-link perigo";
      btnRemover.textContent = "Remover";
      btnRemover.addEventListener("click", async () => {
        try {
          await api("DELETE", `/api/fichas-tecnicas/${item.id}`);
          await carregarFichaTecnica();
        } catch (erro) {
          mostrarToast(erro.message, true);
        }
      });
      tr.lastElementChild.appendChild(btnRemover);
      tbody.appendChild(tr);
    }
    if (ficha.length === 0) {
      tbody.innerHTML = `<tr class="tabela-vazia"><td colspan="3">Nenhum insumo associado a este produto.</td></tr>`;
    }
  } catch (erro) {
    mostrarToast(erro.message, true);
  }
}

el("ficha-tecnica-produto-select").addEventListener("change", carregarFichaTecnica);

el("btn-add-insumo-ficha").addEventListener("click", () => {
  const produtoId = el("ficha-tecnica-produto-select").value;
  if (!produtoId) {
    mostrarToast("Cadastre um produto primeiro.", true);
    return;
  }
  abrirModalFormulario(
    "Adicionar insumo à ficha técnica",
    [
      {
        nome: "materiaPrimaId",
        label: "Matéria-prima",
        tipo: "select",
        opcoes: state.materiasPrimas.map((m) => ({ value: m.id, label: `${m.nome} (${m.unidadeMedida})` })),
      },
      { nome: "quantidadeUsada", label: "Quantidade usada por unidade vendida", tipo: "number", attrs: "min='0' step='0.001'" },
    ],
    "Adicionar",
    async (valores) => {
      await api("POST", "/api/fichas-tecnicas", {
        produtoId: Number(produtoId),
        materiaPrimaId: Number(valores.materiaPrimaId),
        quantidadeUsada: Number(valores.quantidadeUsada),
      });
      await carregarFichaTecnica();
    },
  );
});

function abrirModalCompraEstoque(materiaPrima) {
  abrirModalFormulario(
    `Entrada de compra — ${materiaPrima.nome}`,
    [{ nome: "quantidade", label: `Quantidade (${materiaPrima.unidadeMedida})`, tipo: "number", attrs: "min='0' step='0.001'" }],
    "Registrar",
    async (valores) => {
      await api("POST", `/api/materias-primas/${materiaPrima.id}/movimentos/compra`, {
        quantidade: Number(valores.quantidade),
      });
      await carregarEstoque();
    },
  );
}

function abrirModalEditarMateriaPrima(materiaPrima) {
  abrirModalFormulario(
    "Editar matéria-prima",
    [
      { nome: "nome", label: "Nome", tipo: "text", valor: materiaPrima.nome },
      {
        nome: "unidadeMedida",
        label: "Unidade",
        tipo: "select",
        opcoes: ["KG", "G", "L", "UN"].map((u) => ({ value: u, label: u })),
        valor: materiaPrima.unidadeMedida,
      },
      { nome: "quantidadeMinima", label: "Quantidade mínima (alerta)", tipo: "number", attrs: "min='0' step='0.001'", valor: materiaPrima.quantidadeMinima },
    ],
    "Salvar",
    async (valores) => {
      await api("PUT", `/api/materias-primas/${materiaPrima.id}`, {
        nome: valores.nome,
        unidadeMedida: valores.unidadeMedida,
        quantidadeMinima: Number(valores.quantidadeMinima),
      });
      await carregarEstoque();
    },
  );
}

el("btn-nova-materia-prima").addEventListener("click", () => {
  abrirModalFormulario(
    "Nova matéria-prima",
    [
      { nome: "nome", label: "Nome", tipo: "text" },
      {
        nome: "unidadeMedida",
        label: "Unidade",
        tipo: "select",
        opcoes: ["KG", "G", "L", "UN"].map((u) => ({ value: u, label: u })),
      },
      { nome: "quantidadeMinima", label: "Quantidade mínima (alerta)", tipo: "number", attrs: "min='0' step='0.001'" },
    ],
    "Criar",
    async (valores) => {
      await api("POST", "/api/materias-primas", {
        nome: valores.nome,
        unidadeMedida: valores.unidadeMedida,
        quantidadeMinima: valores.quantidadeMinima ? Number(valores.quantidadeMinima) : 0,
      });
      await carregarEstoque();
    },
  );
});

// ---------- Sales Analytics ----------

state.graficosAnalytics = {};

function destruirGraficosAnalytics() {
  for (const chave of Object.keys(state.graficosAnalytics)) {
    state.graficosAnalytics[chave]?.destroy();
  }
  state.graficosAnalytics = {};
}

async function carregarAnalytics() {
  try {
    const dias = el("analytics-periodo-select").value;
    const [dados, cancelamentos] = await Promise.all([
      api("GET", `/api/analytics/dashboard?dias=${dias}`),
      api("GET", `/api/analytics/cancelamentos?dias=${dias}`),
    ]);
    renderizarAnalytics(dados);
    renderizarCancelamentos(cancelamentos);
  } catch (erro) {
    mostrarToast(erro.message, true);
  }
}

function renderizarCancelamentos(cancelamentos) {
  const tbody = el("analytics-cancelamentos-tbody");
  tbody.innerHTML = "";

  if (cancelamentos.length === 0) {
    tbody.innerHTML = `<tr class="tabela-vazia"><td colspan="6">Nenhum cancelamento no período.</td></tr>`;
    return;
  }

  for (const c of cancelamentos) {
    const tr = document.createElement("tr");
    tr.innerHTML = `
      <td>${formatarDataHora(c.dataHora)}</td>
      <td>${c.tipo === "MESA" ? "Mesa inteira" : "Item"}</td>
      <td>${c.descricao}</td>
      <td>${formatarMoeda(c.valor)}</td>
      <td>${c.motivo || ""}</td>
      <td>${c.canceladoPorNome || ""}</td>
    `;
    tbody.appendChild(tr);
  }
}

el("analytics-periodo-select").addEventListener("change", carregarAnalytics);

function renderizarAnalytics(dados) {
  el("analytics-cards").innerHTML = `
    <div class="analytics-card">
      <span class="analytics-card-label">Faturamento</span>
      <strong>${formatarMoeda(dados.faturamentoTotal)}</strong>
    </div>
    <div class="analytics-card">
      <span class="analytics-card-label">Ticket médio</span>
      <strong>${formatarMoeda(dados.ticketMedio)}</strong>
    </div>
    <div class="analytics-card">
      <span class="analytics-card-label">Nº de comandas</span>
      <strong>${dados.numeroComandas}</strong>
    </div>
    <div class="analytics-card">
      <span class="analytics-card-label">CMV %</span>
      <strong>${dados.cmvPercentual != null ? dados.cmvPercentual.toFixed(1) + "%" : "—"}</strong>
    </div>
  `;

  const alertasDiv = el("analytics-alertas");
  if (dados.alertas.length === 0) {
    alertasDiv.innerHTML = "";
  } else {
    alertasDiv.innerHTML = dados.alertas
      .map((a) => `<div class="analytics-alerta">⚠️ ${a}</div>`)
      .join("");
  }

  destruirGraficosAnalytics();

  state.graficosAnalytics.faturamento = new Chart(el("grafico-faturamento"), {
    type: "line",
    data: {
      labels: dados.faturamentoPorDia.map((p) => p.data),
      datasets: [{ label: "Faturamento", data: dados.faturamentoPorDia.map((p) => p.valor), borderColor: "#a855f7", tension: 0.3 }],
    },
    options: { responsive: true, plugins: { legend: { display: false } } },
  });

  state.graficosAnalytics.abc = new Chart(el("grafico-abc"), {
    type: "bar",
    data: {
      labels: dados.curvaAbc.map((p) => p.produtoNome),
      datasets: [{ label: "Receita", data: dados.curvaAbc.map((p) => p.receita), backgroundColor: "#ec4899" }],
    },
    options: { indexAxis: "y", responsive: true, plugins: { legend: { display: false } } },
  });

  const horas = Array.from({ length: 24 }, (_, h) => h);
  const dias7 = ["Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sáb"];
  const pontosHeatmap = dados.mapaCalor.map((p) => ({ x: p.hora, y: p.diaSemana, v: p.quantidade }));
  state.graficosAnalytics.heatmap = new Chart(el("grafico-heatmap"), {
    type: "bubble",
    data: {
      datasets: [{
        label: "Pedidos",
        data: pontosHeatmap.map((p) => ({ x: p.x, y: p.y, r: Math.min(20, 3 + p.v * 2) })),
        backgroundColor: "rgba(168, 85, 247, 0.6)",
      }],
    },
    options: {
      responsive: true,
      plugins: { legend: { display: false } },
      scales: {
        x: { min: 0, max: 23, title: { display: true, text: "Hora" } },
        y: { min: 0, max: 6, ticks: { callback: (v) => dias7[v] ?? "" }, title: { display: true, text: "Dia da semana" } },
      },
    },
  });

  state.graficosAnalytics.categoria = new Chart(el("grafico-categoria"), {
    type: "doughnut",
    data: {
      labels: dados.mixPorCategoria.map((p) => p.categoriaNome),
      datasets: [{ data: dados.mixPorCategoria.map((p) => p.receita), backgroundColor: ["#a855f7", "#ec4899", "#3b82f6", "#f59e0b", "#10b981", "#ef4444"] }],
    },
    options: {
      responsive: true,
      plugins: {
        tooltip: {
          callbacks: {
            label: (ctx) => {
              const total = ctx.dataset.data.reduce((soma, v) => soma + v, 0);
              const percentual = total > 0 ? (ctx.parsed / total) * 100 : 0;
              return `${ctx.label}: ${percentual.toFixed(1)}%`;
            },
          },
        },
      },
    },
  });
}

// ---------- Inicialização ----------

const sessaoSalva = carregarSessaoSalva();
if (sessaoSalva) {
  state.sessao = sessaoSalva;
  entrarNoApp();
} else {
  mostrarTelaLogin();
}
