<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List,java.net.URLEncoder,java.math.BigDecimal,classes.EstoqueReal,classes.TipoEstoque" %>
<%!
private String esc(String v) {
    if (v == null) return "";
    return v.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;").replace("'", "&#x27;");
}
private String dec(BigDecimal v) { return v == null ? "" : v.stripTrailingZeros().toPlainString(); }
%>
<%
String visao = (String) request.getAttribute("visao");
if (!"tipos".equals(visao)) visao = "mapa";
TipoEstoque tipoEdicao = (TipoEstoque) request.getAttribute("tipoEdicao");
EstoqueReal estoqueEdicao = (EstoqueReal) request.getAttribute("estoqueEdicao");
List<TipoEstoque> tipos = (List<TipoEstoque>) request.getAttribute("tiposEstoque");
List<EstoqueReal> estoques = (List<EstoqueReal>) request.getAttribute("estoquesReais");
boolean editandoTipo = tipoEdicao != null;
boolean editandoEstoque = estoqueEdicao != null;
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>SIMUNDI | Estoque Real</title>
<style>
:root{--indigo:#0f004f;--purple:#45057d;--pink:#ef174f;--bg:#f3f5f8;--line:#dfe3ea;--muted:#70798a;--ink:#202636;--green:#16834a;--red:#b42336}
*{box-sizing:border-box}body{margin:0;font-family:Arial,Helvetica,sans-serif;background:var(--bg);color:var(--ink)}
.top{min-height:66px;background:linear-gradient(100deg,var(--indigo),#123f70);color:#fff;display:flex;align-items:center;padding:0 24px;gap:28px;flex-wrap:wrap}.brand{font-size:21px;font-weight:800;letter-spacing:2px;margin-right:12px}.top a{color:#fff;text-decoration:none;font-weight:700;font-size:13px;padding:24px 0}.top a.active{border-bottom:3px solid var(--pink)}
.wrap{max-width:1500px;margin:28px auto;padding:0 22px}.heading{display:flex;justify-content:space-between;align-items:end;gap:16px;margin-bottom:18px}.heading h1{margin:0;color:var(--indigo);font-size:27px}.heading p{margin:6px 0 0;color:var(--muted);font-size:13px}.counter{background:#eee8f8;color:var(--purple);padding:9px 13px;border-radius:20px;font-size:12px;font-weight:800;white-space:nowrap}
.alert{padding:12px 14px;border-radius:8px;margin-bottom:14px;font-size:13px;line-height:1.4}.success{background:#eaf7ef;border:1px solid #b8e4c7;color:#176b3b}.error{background:#fff0f1;border:1px solid #f2c2c8;color:#9f2433}
.shell{display:grid;grid-template-columns:235px minmax(0,1fr);gap:17px;align-items:start}.sidebar,.card{background:#fff;border:1px solid var(--line);border-radius:11px;box-shadow:0 3px 12px #17233b12;overflow:hidden}.side-title{padding:15px 17px;background:linear-gradient(105deg,var(--indigo),var(--purple));color:#fff;font-weight:800}.side-nav{padding:10px;display:grid;gap:7px}.side-nav a{display:block;padding:12px;border-radius:8px;color:var(--ink);text-decoration:none;font-size:13px;font-weight:700}.side-nav a.active{background:#eee8f8;color:var(--purple);border-left:3px solid var(--pink)}.side-nav small{display:block;margin-top:5px;color:var(--muted);font-weight:400;line-height:1.4}.content{min-width:0;display:grid;gap:16px}.card-title{padding:14px 17px;background:linear-gradient(105deg,var(--indigo),var(--purple));color:#fff;font-weight:800}.card-body{padding:18px}
.form-grid{display:grid;grid-template-columns:repeat(2,minmax(0,1fr));gap:0 13px}.field{margin-bottom:13px}.field.full{grid-column:1/-1}label{display:block;margin-bottom:6px;font-size:10px;text-transform:uppercase;letter-spacing:.5px;font-weight:800;color:#727b8c}input,select{width:100%;border:1px solid #ccd2dc;border-radius:7px;padding:10px 11px;font-size:14px;background:#fff;color:var(--ink)}input:focus,select:focus{outline:none;border-color:var(--purple);box-shadow:0 0 0 3px #45057d12}.checks{display:flex;gap:18px;flex-wrap:wrap;margin:7px 0 17px}.check{display:flex;align-items:center;gap:7px;font-size:13px;color:#4b5565}.check input{width:auto}
.actions,.row-actions{display:flex;gap:7px;align-items:center;flex-wrap:wrap}.row-actions form{margin:0}.btn{border:0;border-radius:7px;padding:10px 14px;font-weight:800;font-size:12px;cursor:pointer;text-decoration:none;display:inline-flex;align-items:center;justify-content:center}.primary{background:var(--purple);color:#fff}.secondary{background:#e9edf3;color:var(--indigo)}.danger{background:#fff0f1;color:var(--red)}.small{padding:7px 9px;font-size:11px}
.table-tools{display:flex;justify-content:space-between;gap:12px;align-items:center;margin-bottom:13px}.search{max-width:340px}.table-wrap{overflow-x:auto}table{width:100%;border-collapse:collapse;min-width:700px}th{text-align:left;background:#f6f7fa;color:#667085;font-size:10px;text-transform:uppercase;letter-spacing:.5px;padding:12px 11px}td{padding:12px 11px;border-top:1px solid #edf0f4;font-size:13px;vertical-align:middle}tr:hover td{background:#fbfaff}.code{font-weight:800;color:var(--indigo)}.pill{display:inline-block;border-radius:20px;padding:5px 8px;font-size:10px;font-weight:800;background:#eaf7ef;color:var(--green)}.pill.off{background:#fff4e5;color:#9a5b00}.empty{padding:28px;text-align:center;color:var(--muted);font-size:13px}.hint{font-size:11px;color:var(--muted);line-height:1.5;margin-top:12px}.coords{display:grid;grid-template-columns:repeat(3,minmax(0,1fr));gap:9px}.section-label{grid-column:1/-1;font-size:11px;font-weight:800;color:var(--purple);margin:3px 0 0;text-transform:uppercase;letter-spacing:.4px}
@media(max-width:900px){.shell{grid-template-columns:1fr}.side-nav{grid-template-columns:repeat(2,minmax(0,1fr))}}@media(max-width:600px){.wrap{padding:0 12px}.heading{align-items:start;flex-direction:column}.top{gap:17px;padding:0 15px}.top a{padding:15px 0}.form-grid{grid-template-columns:1fr}.field.full{grid-column:auto}.side-nav{grid-template-columns:1fr}.coords{grid-template-columns:1fr}}
</style>
</head>
<body>
<header class="top">
  <div class="brand">SIMUNDI</div>
  <a href="<%= request.getContextPath() %>/index">Home</a>
  <a href="<%= request.getContextPath() %>/entradas">Entradas</a>
  <a href="<%= request.getContextPath() %>/saidas">Saídas</a>
  <a href="<%= request.getContextPath() %>/armazem3d">Armazém 3D</a>
  <a class="active" href="<%= request.getContextPath() %>/estoqueReal">Estoque Real</a>
  <a href="<%= request.getContextPath() %>/clientes">Clientes</a>
</header>
<main class="wrap">
  <div class="heading"><div><h1>Estoque Real</h1><p>Gerencie os conceitos de armazenamento e as estruturas físicas cadastradas no armazém.</p></div><span class="counter"><%= estoques == null ? 0 : estoques.size() %> ESTRUTURA(S) NO MAPA</span></div>
  <% if (request.getAttribute("mensagemSucesso") != null) { %><div class="alert success" role="status"><%= esc(String.valueOf(request.getAttribute("mensagemSucesso"))) %></div><% } %>
  <% if (request.getAttribute("mensagemErro") != null) { %><div class="alert error" role="alert"><%= esc(String.valueOf(request.getAttribute("mensagemErro"))) %></div><% } %>
  <div class="shell">
    <aside class="sidebar">
      <div class="side-title">Visualização</div>
      <nav class="side-nav">
        <a class="<%= "tipos".equals(visao) ? "active" : "" %>" href="<%= request.getContextPath() %>/estoqueReal?visao=tipos">Tipos de estoque<small>Cadastro dos conceitos e unidades de armazenamento</small></a>
        <a class="<%= "mapa".equals(visao) ? "active" : "" %>" href="<%= request.getContextPath() %>/estoqueReal?visao=mapa">Mapa do estoque<small>Cadastro administrativo das estruturas físicas</small></a>
      </nav>
    </aside>
    <section class="content">
    <% if ("tipos".equals(visao)) { %>
      <section class="card">
        <div class="card-title"><%= editandoTipo ? "Editar tipo de estoque" : "Cadastrar tipo de estoque" %></div>
        <div class="card-body">
          <form method="post" action="<%= request.getContextPath() %>/estoqueReal">
            <input type="hidden" name="acao" value="<%= editandoTipo ? "atualizarTipo" : "salvarTipo" %>">
            <input type="hidden" name="visao" value="tipos">
            <% if (editandoTipo) { %><input type="hidden" name="codTipoEstoque" value="<%= tipoEdicao.getCodTipoEstoque() %>"><% } %>
            <div class="form-grid">
              <div class="field"><label for="descTipoEstoque">Descrição do tipo *</label><input id="descTipoEstoque" name="descTipoEstoque" maxlength="150" required value="<%= tipoEdicao == null ? "" : esc(tipoEdicao.getDescTipoEstoque()) %>"></div>
              <div class="field"><label for="unidadeEstoque">Unidade de estoque *</label><input id="unidadeEstoque" name="unidadeEstoque" maxlength="30" required placeholder="Ex.: unidade, kg, m³" value="<%= tipoEdicao == null ? "" : esc(tipoEdicao.getUnidadeEstoque()) %>"></div>
            </div>
            <div class="actions"><button class="btn primary" type="submit"><%= editandoTipo ? "Salvar alterações" : "Cadastrar tipo" %></button><% if (editandoTipo) { %><a class="btn secondary" href="<%= request.getContextPath() %>/estoqueReal?visao=tipos">Cancelar</a><% } else { %><button class="btn secondary" type="reset">Limpar</button><% } %></div>
          </form>
        </div>
      </section>
      <section class="card"><div class="card-title">Tipos de estoque cadastrados</div><div class="card-body">
        <div class="table-tools"><input class="search" id="filtroTipos" type="search" placeholder="Pesquisar tipo ou unidade..."></div><div class="table-wrap"><table id="tabelaTipos"><thead><tr><th>Código</th><th>Descrição</th><th>Unidade</th><th>Ações</th></tr></thead><tbody>
        <% if (tipos != null && !tipos.isEmpty()) { for (TipoEstoque t : tipos) { %>
          <tr><td class="code"><%= t.getCodTipoEstoque() %></td><td><%= esc(t.getDescTipoEstoque()) %></td><td><%= esc(t.getUnidadeEstoque()) %></td><td><div class="row-actions"><a class="btn secondary small" href="<%= request.getContextPath() %>/estoqueReal?visao=tipos&editarTipo=<%= t.getCodTipoEstoque() %>">Editar</a><form method="post" action="<%= request.getContextPath() %>/estoqueReal" onsubmit="return confirm('Deseja realmente remover este tipo de estoque?');"><input type="hidden" name="acao" value="excluirTipo"><input type="hidden" name="visao" value="tipos"><input type="hidden" name="codTipoEstoque" value="<%= t.getCodTipoEstoque() %>"><button class="btn danger small" type="submit">Remover</button></form></div></td></tr>
        <% } } else { %><tr><td colspan="4" class="empty">Nenhum tipo de estoque cadastrado.</td></tr><% } %>
        </tbody></table></div>
      </div></section>
    <% } else { %>
      <section class="card">
        <div class="card-title"><%= editandoEstoque ? "Editar estrutura física" : "Adicionar estrutura ao mapa" %></div>
        <div class="card-body">
          <form method="post" action="<%= request.getContextPath() %>/estoqueReal">
            <input type="hidden" name="acao" value="<%= editandoEstoque ? "atualizarEstoque" : "salvarEstoque" %>"><input type="hidden" name="visao" value="mapa">
            <% if (editandoEstoque) { %><input type="hidden" name="id" value="<%= estoqueEdicao.getId() %>"><% } %>
            <div class="form-grid">
              <div class="field"><label for="identificacao">Identificação *</label><input id="identificacao" name="identificacao" maxlength="100" required value="<%= estoqueEdicao == null ? "" : esc(estoqueEdicao.getIdentificacao()) %>"></div>
              <div class="field"><label for="codTipoEstoque">Tipo de estoque *</label><select id="codTipoEstoque" name="codTipoEstoque" required><option value="">Selecione</option><% if (tipos != null) { for (TipoEstoque t : tipos) { boolean selecionado = estoqueEdicao != null && estoqueEdicao.getCodTipoEstoque() != null && estoqueEdicao.getCodTipoEstoque().equals(t.getCodTipoEstoque()); %><option value="<%= t.getCodTipoEstoque() %>" <%= selecionado ? "selected" : "" %>><%= esc(t.getDescTipoEstoque()) %> (<%= esc(t.getUnidadeEstoque()) %>)</option><% } } %></select></div>
              <div class="field"><label for="armazenamentoMax">Armazenamento máximo *</label><input id="armazenamentoMax" name="armazenamentoMax" type="number" min="0" step="0.01" required value="<%= estoqueEdicao == null ? "" : dec(estoqueEdicao.getArmazenamentoMax()) %>"></div>
              <div class="field"><label>Identificação de coordenadas</label><div class="hint">Informe os limites espaciais da estrutura. Os campos podem ficar vazios quando não aplicáveis.</div></div>
              <div class="field full"><div class="coords">
                <div class="section-label">Coordenadas iniciais</div>
                <div class="field"><label for="posXInicial">X inicial</label><input type="number" id="posXInicial" name="posXInicial" value="<%= estoqueEdicao == null || estoqueEdicao.getPosXInicial() == null ? "" : estoqueEdicao.getPosXInicial() %>"></div>
                <div class="field"><label for="posYInicial">Y inicial</label><input type="number" id="posYInicial" name="posYInicial" value="<%= estoqueEdicao == null || estoqueEdicao.getPosYInicial() == null ? "" : estoqueEdicao.getPosYInicial() %>"></div>
                <div class="field"><label for="posZInicial">Z inicial</label><input type="number" id="posZInicial" name="posZInicial" value="<%= estoqueEdicao == null || estoqueEdicao.getPosZInicial() == null ? "" : estoqueEdicao.getPosZInicial() %>"></div>
                <div class="section-label">Coordenadas finais</div>
                <div class="field"><label for="posXFinal">X final</label><input type="number" id="posXFinal" name="posXFinal" value="<%= estoqueEdicao == null || estoqueEdicao.getPosXFinal() == null ? "" : estoqueEdicao.getPosXFinal() %>"></div>
                <div class="field"><label for="posYFinal">Y final</label><input type="number" id="posYFinal" name="posYFinal" value="<%= estoqueEdicao == null || estoqueEdicao.getPosYFinal() == null ? "" : estoqueEdicao.getPosYFinal() %>"></div>
                <div class="field"><label for="posZFinal">Z final</label><input type="number" id="posZFinal" name="posZFinal" value="<%= estoqueEdicao == null || estoqueEdicao.getPosZFinal() == null ? "" : estoqueEdicao.getPosZFinal() %>"></div>
              </div></div>
            </div>
            <div class="checks"><label class="check"><input type="checkbox" name="virtual" value="on" <%= estoqueEdicao != null && estoqueEdicao.isVirtual() ? "checked" : "" %>> Virtual</label><label class="check"><input type="checkbox" name="ativa" value="on" <%= estoqueEdicao == null || estoqueEdicao.isAtiva() ? "checked" : "" %>> Ativa</label></div>
            <div class="actions"><button class="btn primary" type="submit"><%= editandoEstoque ? "Salvar alterações" : "Adicionar ao mapa" %></button><% if (editandoEstoque) { %><a class="btn secondary" href="<%= request.getContextPath() %>/estoqueReal?visao=mapa">Cancelar</a><% } else { %><button class="btn secondary" type="reset">Limpar</button><% } %></div>
          </form>
        </div>
      </section>
      <section class="card"><div class="card-title">Estruturas físicas cadastradas</div><div class="card-body">
        <div class="table-tools"><input class="search" id="filtroEstoque" type="search" placeholder="Pesquisar por identificação ou tipo..."></div><div class="table-wrap"><table id="tabelaEstoque"><thead><tr><th>ID</th><th>Identificação</th><th>Tipo</th><th>Capacidade</th><th>Coordenadas</th><th>Status</th><th>Ações</th></tr></thead><tbody>
        <% if (estoques != null && !estoques.isEmpty()) { for (EstoqueReal e : estoques) { String nomeTipo = "Não encontrado"; if (tipos != null) { for (TipoEstoque t : tipos) if (e.getCodTipoEstoque() != null && e.getCodTipoEstoque().equals(t.getCodTipoEstoque())) { nomeTipo = t.getDescTipoEstoque(); break; } } %>
          <tr><td class="code"><%= e.getId() %></td><td><%= esc(e.getIdentificacao()) %></td><td><%= esc(nomeTipo) %></td><td><%= dec(e.getArmazenamentoMax()) %></td><td><%= e.getPosXInicial() == null ? "—" : e.getPosXInicial() %>, <%= e.getPosYInicial() == null ? "—" : e.getPosYInicial() %>, <%= e.getPosZInicial() == null ? "—" : e.getPosZInicial() %> → <%= e.getPosXFinal() == null ? "—" : e.getPosXFinal() %>, <%= e.getPosYFinal() == null ? "—" : e.getPosYFinal() %>, <%= e.getPosZFinal() == null ? "—" : e.getPosZFinal() %></td><td><span class="pill <%= e.isAtiva() ? "" : "off" %>"><%= e.isAtiva() ? "Ativa" : "Inativa" %></span><%= e.isVirtual() ? " · Virtual" : "" %></td><td><div class="row-actions"><a class="btn secondary small" href="<%= request.getContextPath() %>/estoqueReal?visao=mapa&editarEstoque=<%= e.getId() %>">Editar</a><form method="post" action="<%= request.getContextPath() %>/estoqueReal" onsubmit="return confirm('Deseja realmente remover esta estrutura?');"><input type="hidden" name="acao" value="excluirEstoque"><input type="hidden" name="visao" value="mapa"><input type="hidden" name="id" value="<%= e.getId() %>"><button class="btn danger small" type="submit">Remover</button></form></div></td></tr>
        <% } } else { %><tr><td colspan="7" class="empty">Nenhuma estrutura física cadastrada.</td></tr><% } %>
        </tbody></table></div>
      </div></section>
    <% } %>
    </section>
  </div>
</main>
<script>
function configurarFiltro(id, tabela) { var campo=document.getElementById(id), alvo=document.getElementById(tabela); if(!campo||!alvo)return; campo.addEventListener('input',function(){var termo=this.value.toLocaleLowerCase('pt-BR');alvo.querySelectorAll('tbody tr').forEach(function(l){l.style.display=l.textContent.toLocaleLowerCase('pt-BR').includes(termo)?'':'none';});}); }
configurarFiltro('filtroTipos','tabelaTipos'); configurarFiltro('filtroEstoque','tabelaEstoque');
</script>
</body>
</html>
