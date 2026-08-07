const CACHE_NOME = "gestor-comercial-shell";
const ARQUIVOS_SHELL = [
  "/",
  "/index.html",
  "/css/tokens.css",
  "/css/style.css",
  "/js/app.js",
  "/manifest.json",
  "/icons/icon.svg",
];

self.addEventListener("install", (evento) => {
  evento.waitUntil(
    caches.open(CACHE_NOME).then((cache) => cache.addAll(ARQUIVOS_SHELL))
  );
  self.skipWaiting();
});

self.addEventListener("activate", (evento) => {
  evento.waitUntil(
    caches.keys().then((chaves) =>
      Promise.all(chaves.filter((chave) => chave !== CACHE_NOME).map((chave) => caches.delete(chave)))
    )
  );
  self.clients.claim();
});

// Network-first, não cache-first: o servidor está sempre na mesma rede
// local (nunca é lento pra alcançar), então sempre busca a versão mais
// nova primeiro. O cache só entra como rede de segurança se a rede cair —
// isso evita o app ficar preso numa versão antiga esperando alguém lembrar
// de trocar o nome do cache a cada deploy.
self.addEventListener("fetch", (evento) => {
  const url = new URL(evento.request.url);

  // Chamadas à API nunca são cacheadas: os dados de mesas/comandas precisam
  // sempre estar atualizados, nunca vindos de um cache desatualizado.
  if (url.pathname.startsWith("/api/")) return;

  evento.respondWith(
    fetch(evento.request)
      .then((resposta) => {
        const copia = resposta.clone();
        caches.open(CACHE_NOME).then((cache) => cache.put(evento.request, copia));
        return resposta;
      })
      .catch(() => caches.match(evento.request))
  );
});
