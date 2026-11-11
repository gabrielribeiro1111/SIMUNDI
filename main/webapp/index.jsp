<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    int clientes = (Integer) request.getAttribute("clientes");
    int produtos = (Integer) request.getAttribute("produtos");
    int posicoes = (Integer) request.getAttribute("posicoes");
    int osHoje = (Integer) request.getAttribute("osHoje");
    int entradasHoje = (Integer) request.getAttribute("entradasHoje");
    int saidasHoje = (Integer) request.getAttribute("saidasHoje");
    int movimentacoesHoje = (Integer) request.getAttribute("movimentacoesHoje");
    int pendentes = (Integer) request.getAttribute("pendentes");
    boolean bancoOnline = Boolean.TRUE.equals(request.getAttribute("bancoOnline"));
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>SIMUNDI | Gestão Operacional e Logística</title>
<style>
:root{--indigo:#0f004f;--purple:#45057d;--pink:#e60046;--blue:#0b4779;--ink:#17233b;--muted:#667085;--line:#d9dee8;--bg:#f4f6f9;--green:#18a957;--red:#e64158;--amber:#f4a62a}
*{box-sizing:border-box}html,body{margin:0;min-height:100%;font-family:Arial,Helvetica,sans-serif;color:var(--ink);background:var(--bg)}a{text-decoration:none;color:inherit}
.topbar{height:70px;background:linear-gradient(100deg,var(--indigo),#123f70);color:#fff;display:flex;align-items:center;padding:0 26px;box-shadow:0 2px 8px #0003;position:relative;z-index:5}.brand{display:flex;align-items:center;min-width:245px}.brand-logo{height:48px;width:auto;max-width:220px;object-fit:contain;display:block}.brand-mark{width:39px;height:39px;position:relative}.brand-mark:before,.brand-mark:after{content:"";position:absolute;left:3px;width:31px;height:8px;transform:skewY(19deg);border-radius:2px}.brand-mark:before{top:8px;background:#ef174f;box-shadow:0 11px 0 #fff}.brand-mark:after{top:30px;background:#7551c9}.brand strong{font-size:27px;letter-spacing:2px}.brand small{display:block;font-size:10px;letter-spacing:3px;opacity:.78}.topnav{height:100%;display:flex;align-items:stretch;gap:2px;flex:1}.topnav a{display:flex;align-items:center;padding:0 18px;font-weight:700;font-size:14px;border-bottom:4px solid transparent;transition:.2s}.topnav a:hover,.topnav a.active{background:#ffffff10;border-bottom-color:#ef174f}.user{display:flex;align-items:center;gap:12px;font-size:13px}.avatar{width:37px;height:37px;border-radius:50%;display:grid;place-items:center;background:#fff2;font-size:18px}.logout{padding:8px 12px;border-left:1px solid #ffffff38}
.hero{height:226px;position:relative;background:linear-gradient(90deg,rgba(15,0,79,.92),rgba(15,0,79,.46) 48%,rgba(15,0,79,.12)),url('assets/simundi-home-reference.png') center 16%/cover no-repeat;color:#fff;overflow:hidden}.hero:after{content:"";position:absolute;inset:0;background:linear-gradient(0deg,#0f004f38,transparent 65%)}.hero-content{position:relative;z-index:2;padding:43px 6vw;max-width:800px}.hero h1{font-size:40px;margin:0 0 7px;letter-spacing:.5px}.hero h1 span{font-weight:300}.hero p{font-size:18px;margin:0 0 23px;opacity:.92}.hero-tags{display:flex;gap:10px;flex-wrap:wrap}.tag{background:#ffffff20;border:1px solid #ffffff4a;padding:8px 13px;border-radius:18px;font-size:12px;backdrop-filter:blur(5px)}
.workspace{padding:0 24px 24px;max-width:1800px;margin:-28px auto 0;position:relative;z-index:3}.modules{display:grid;grid-template-columns:repeat(5,1fr);gap:14px}.module{background:#fff;border:1px solid #e1e4ea;border-radius:11px;min-height:126px;padding:20px 18px;box-shadow:0 4px 13px #17233b1b;transition:.18s}.module:hover{transform:translateY(-3px);box-shadow:0 8px 18px #17233b28;border-color:#c8bbdc}.module .icon{width:43px;height:43px;border-radius:10px;display:grid;place-items:center;background:#f0eafa;color:var(--purple);font-size:23px;margin-bottom:10px}.module h3{margin:0 0 5px;font-size:17px;color:var(--indigo)}.module p{margin:0;color:var(--muted);font-size:12px;line-height:1.35}
.grid{display:grid;grid-template-columns:1.05fr 1.65fr 1.05fr;gap:16px;margin-top:18px}.panel{background:#fff;border:1px solid #e0e4eb;border-radius:10px;box-shadow:0 3px 10px #17233b12;overflow:hidden}.panel-title{height:49px;padding:0 17px;display:flex;align-items:center;gap:10px;background:linear-gradient(105deg,var(--indigo),var(--purple));color:#fff;font-weight:700}.panel-body{padding:13px}.quick a{display:flex;align-items:center;gap:12px;padding:12px 10px;border-bottom:1px solid #eceef3;font-size:13px;font-weight:700}.quick a:last-child{border-bottom:0}.quick a:hover{background:#f6f2fb;color:var(--purple)}.qicon{width:29px;height:29px;border-radius:7px;background:#eee8f8;display:grid;place-items:center;color:var(--purple)}.arrow{margin-left:auto;color:#98a0ae;font-size:18px}
.kpis{display:grid;grid-template-columns:repeat(4,1fr);gap:10px}.kpi{border:1px solid #e2e5eb;border-radius:9px;padding:14px 10px;text-align:center;background:linear-gradient(#fff,#fafbfc);min-height:117px}.kpi .ki{font-size:23px}.kpi b{display:block;font-size:25px;margin:8px 0 3px;color:var(--indigo)}.kpi span{font-size:11px;color:var(--muted);text-transform:uppercase;font-weight:700}.kpi small{display:block;margin-top:7px;color:var(--green);font-weight:700}.movement{display:grid;gap:8px}.movement-row{display:flex;align-items:center;padding:13px;border-radius:8px;background:#f8f9fb;border:1px solid #eceef2}.movement-row .mi{font-size:20px;margin-right:11px}.movement-row b{margin-left:auto;font-size:18px;color:var(--indigo)}.movement-row span{font-size:12px;font-weight:700}.alerts{margin-top:16px}.alert{display:flex;gap:10px;padding:10px 3px;border-bottom:1px solid #eceef2;font-size:12px;line-height:1.35}.alert:last-child{border:0}.alert i{font-style:normal;font-size:17px}.statusline{margin-top:18px;background:var(--indigo);color:#fff;border-radius:8px;display:flex;align-items:center;padding:12px 16px;font-size:12px;gap:14px}.dot{width:10px;height:10px;border-radius:50%;background:<%= bancoOnline ? "#2ad36b" : "#ef4c5f" %>;box-shadow:0 0 0 4px #ffffff18}.statusline .clock{margin-left:auto}.footer{padding:17px 25px;text-align:center;color:#7b8494;font-size:11px}
@media(max-width:1100px){.topnav{display:none}.user{margin-left:auto}.modules{grid-template-columns:repeat(3,1fr)}.grid{grid-template-columns:1fr 1fr}.grid .panel:nth-child(2){grid-column:span 2}.kpis{grid-template-columns:repeat(2,1fr)}}@media(max-width:700px){.brand strong{font-size:21px}.user .uname{display:none}.hero{height:200px}.hero-content{padding:34px 24px}.hero h1{font-size:30px}.workspace{padding:0 12px 15px}.modules,.grid{grid-template-columns:1fr}.grid .panel:nth-child(2){grid-column:auto}.kpis{grid-template-columns:1fr 1fr}.statusline{flex-wrap:wrap}.clock{width:100%;margin-left:24px!important}}
</style>
</head>
<body>
<header class="topbar">
  <div class="brand"><img class="brand-logo" src="assets/simundi-logo.png" alt="SIMUNDI"></div>
  <nav class="topnav"><a class="active" href="<%= request.getContextPath() %>/index">Home</a><a href="<%= request.getContextPath() %>/entradas">Entradas</a><a href="<%= request.getContextPath() %>/saidas">Saídas</a><a href="<%= request.getContextPath() %>/armazem3d">Armazém 3D</a><a href="estoque.jsp">Estoque</a><a href="<%= request.getContextPath() %>/clientes">Clientes</a><a href="dashboard.jsp">Dashboard</a></nav>
  <div class="user"><div class="avatar">👤</div><div class="uname"><b>Operador</b><br><span style="opacity:.7">Centro Logístico</span></div><span class="logout">Sair</span></div>
</header>
<section class="hero"><div class="hero-content"><h1>Gestão Operacional <span>e Logística</span></h1><p>Controle integrado do armazém, recebimentos, expedições e estoque físico.</p><div class="hero-tags"><span class="tag">▣ Operação terrestre</span><span class="tag">⌖ Armazém e posições</span><span class="tag">↔ Movimentações rastreáveis</span></div></div></section>
<main class="workspace">
 <section class="modules">
  <a class="module" href="dashboard.jsp"><div class="icon">▥</div><h3>Dashboard</h3><p>Visão consolidada de ordens, movimentações e desempenho.</p></a>
  <a class="module" href="<%= request.getContextPath() %>/entradas"><div class="icon">⇥</div><h3>Entradas</h3><p>Emissão de OS de entrada, recebimento e identificação da carga.</p></a>
  <a class="module" href="<%= request.getContextPath() %>/saidas"><div class="icon">⇤</div><h3>Saídas</h3><p>Emissão de OS de saída, expedição e liberação da carga.</p></a>
  <a class="module" href="<%= request.getContextPath() %>/armazem3d"><div class="icon">▦</div><h3>Armazém 3D</h3><p>Visualize ruas, estruturas, posições e ocupação em ambiente tridimensional.</p></a>
  <a href="<%= request.getContextPath() %>/estoqueReal">Estoque Real</a>
  <a class="module" href="clientes.jsp"><div class="icon">♟</div><h3>Clientes</h3><p>Cadastro e consulta dos clientes vinculados às operações.</p></a>
 </section>
 <section class="grid">
  <article class="panel"><div class="panel-title">⚡ Acesso rápido</div><div class="panel-body quick">
    <a href="<%= request.getContextPath() %>/entradas"><span class="qicon">＋</span>Emitir OS de entrada<span class="arrow">›</span></a><a href="<%= request.getContextPath() %>/saidas"><span class="qicon">−</span>Emitir OS de saída<span class="arrow">›</span></a><a href="<%= request.getContextPath() %>/armazem3d"><span class="qicon">◈</span>Abrir Armazém 3D<span class="arrow">›</span></a><a href="estoque.jsp"><span class="qicon">▦</span>Consultar estoque físico<span class="arrow">›</span></a><a href="clientes.jsp"><span class="qicon">♟</span>Consultar clientes<span class="arrow">›</span></a><a href="dashboard.jsp"><span class="qicon">▥</span>Abrir dashboard<span class="arrow">›</span></a>
  </div></article>
  <article class="panel"><div class="panel-title">▥ Visão do dia</div><div class="panel-body"><div class="kpis">
    <div class="kpi"><div class="ki">▤</div><b><%= osHoje %></b><span>Ordens hoje</span><small><%= pendentes %> em aberto</small></div>
    <div class="kpi"><div class="ki">⇥</div><b><%= entradasHoje %></b><span>Entradas hoje</span><small>Recebimentos</small></div>
    <div class="kpi"><div class="ki">⇤</div><b><%= saidasHoje %></b><span>Saídas hoje</span><small>Expedições</small></div>
    <div class="kpi"><div class="ki">↔</div><b><%= movimentacoesHoje %></b><span>Movimentações</span><small>Registradas hoje</small></div>
  </div></div></article>
  <article class="panel"><div class="panel-title">▣ Estrutura atual</div><div class="panel-body movement">
    <div class="movement-row"><span class="mi">▦</span><span>Posições físicas ativas</span><b><%= posicoes %></b></div><div class="movement-row"><span class="mi">▤</span><span>Produtos cadastrados</span><b><%= produtos %></b></div><div class="movement-row"><span class="mi">♟</span><span>Clientes ativos</span><b><%= clientes %></b></div>
    <div class="alerts"><div class="alert"><i>ℹ️</i><span><b>SIMUNDI</b><br>Operação baseada em ordens de serviço de entrada e saída.</span></div><div class="alert"><i>📦</i><span><b>Estoque</b><br>Posições físicas e virtuais são controladas pelo cadastro ESTOQUE_REAL.</span></div></div>
  </div></article>
 </section>
 <div class="statusline"><span class="dot"></span><b><%= bancoOnline ? "Sistema Online" : "Sistema indisponível" %></b><span class="clock" id="clock"></span></div>
</main>
<footer class="footer">SIMUNDI • Sistema de Gestão de Armazém e Logística Terrestre</footer>
<script>function tick(){const d=new Date();document.getElementById('clock').textContent=d.toLocaleDateString('pt-BR',{weekday:'short',day:'2-digit',month:'short',year:'numeric'})+' — '+d.toLocaleTimeString('pt-BR',{hour:'2-digit',minute:'2-digit'});}tick();setInterval(tick,30000);</script>
</body></html>
