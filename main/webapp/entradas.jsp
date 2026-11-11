<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
<title>SIMUNDI | Emissão OS - Entrada</title>
<style>
:root {
	--indigo: #0F004F;
	--purple: #45057d;
	--pink: #ef174f;
	--bg: #f3f5f8;
	--line: #dfe3ea;
	--muted: #70798a
}

* {
	box-sizing: border-box
}

body {
	margin: 0;
	font-family: Arial, sans-serif;
	background: var(--bg);
	color: #202636
}

.top {
	height: 66px;
	background: linear-gradient(100deg, var(--indigo), #123f70);
	color: #fff;
	display: flex;
	align-items: center;
	padding: 0 24px;
	gap: 28px
}

.brand {
	display: flex;
	align-items: center
}

.brand-logo {
	height: 46px;
	width: auto;
	max-width: 210px;
	object-fit: contain;
	display: block
}

.top a {
	color: #fff;
	text-decoration: none;
	font-weight: 700;
	font-size: 13px
}

.top .active {
	border-bottom: 3px solid var(--pink);
	height: 66px;
	display: flex;
	align-items: center
}

.wrap {
	max-width: 1500px;
	margin: 22px auto;
	padding: 0 22px
}

.heading {
	display: flex;
	justify-content: space-between;
	align-items: end;
	margin-bottom: 16px
}

.heading h1 {
	margin: 0;
	color: var(--indigo);
	font-size: 25px
}

.heading p {
	margin: 5px 0 0;
	color: var(--muted);
	font-size: 13px
}

.status {
	background: #eee8f8;
	color: var(--purple);
	padding: 8px 12px;
	border-radius: 20px;
	font-size: 12px;
	font-weight: bold
}

.layout {
	display: grid;
	grid-template-columns: 1fr 330px;
	gap: 16px
}

.card {
	background: #fff;
	border: 1px solid var(--line);
	border-radius: 11px;
	box-shadow: 0 3px 12px #17233b12;
	margin-bottom: 14px;
	overflow: hidden
}

.ct {
	padding: 13px 16px;
	background: linear-gradient(105deg, var(--indigo), var(--purple));
	color: #fff;
	font-weight: 700
}

.body {
	padding: 16px
}

.grid {
	display: grid;
	grid-template-columns: repeat(4, 1fr);
	gap: 13px
}

.span2 {
	grid-column: span 2
}

.span4 {
	grid-column: span 4
}

label {
	display: block;
	font-size: 10px;
	text-transform: uppercase;
	font-weight: 800;
	color: #727b8c;
	margin-bottom: 6px;
	letter-spacing: .5px
}

input, select, textarea {
	width: 100%;
	border: 1px solid #ccd2dc;
	border-radius: 7px;
	padding: 10px 11px;
	font-size: 14px;
	outline: none;
	background: #fff
}

input:focus, select:focus, textarea:focus {
	border-color: var(--purple);
	box-shadow: 0 0 0 3px #45057d12
}

textarea {
	resize: vertical;
	min-height: 72px
}

.os {
	font-size: 20px;
	font-weight: 800;
	letter-spacing: 2px;
	color: var(--indigo)
}

.actions {
	display: flex;
	justify-content: flex-end;
	gap: 9px;
	padding-top: 4px
}

.btn {
	border: 0;
	border-radius: 7px;
	padding: 11px 17px;
	font-weight: 800;
	cursor: pointer
}

.primary {
	background: var(--purple);
	color: #fff
}

.secondary {
	background: #e9edf3;
	color: var(--indigo)
}

.summary {
	display: grid;
	gap: 9px
}

.row {
	display: flex;
	justify-content: space-between;
	padding: 10px 0;
	border-bottom: 1px solid #edf0f4;
	font-size: 13px
}

.row b {
	color: var(--indigo)
}

.notice {
	font-size: 12px;
	line-height: 1.5;
	background: #f8f6fb;
	border-left: 4px solid var(--purple);
	padding: 12px;
	border-radius: 6px;
	color: #596274
}

@media ( max-width :900px) {
	.layout {
		grid-template-columns: 1fr
	}
	.grid {
		grid-template-columns: 1fr 1fr
	}
	.span4 {
		grid-column: span 2
	}
}

@media ( max-width :560px) {
	.grid {
		grid-template-columns: 1fr
	}
	.span2, .span4 {
		grid-column: auto
	}
}
</style>
</head>
<body>
<header class="top">
    <div class="brand">SIMUNDI</div>
    <a href="<%= request.getContextPath() %>/index">Home</a>
    <a class="active" href="<%= request.getContextPath() %>/entradas">Entradas</a>
    <a href="<%= request.getContextPath() %>/saidas">Saídas</a>
    <a href="<%= request.getContextPath() %>/armazem3d">Armazém 3D</a>
    <a href="<%= request.getContextPath() %>/estoqueReal">Estoque Real</a>
    <a href="<%= request.getContextPath() %>/clientes">Clientes</a>
</header>

	<main class="wrap"><% if (request.getAttribute("mensagemSucesso") != null) { %><div class="notice" role="status"><%= request.getAttribute("mensagemSucesso") %></div><% } if (request.getAttribute("mensagemErro") != null) { %><div class="notice" role="alert"><%= request.getAttribute("mensagemErro") %></div><% } %>
		<div class="heading">
			<div>
				<h1>Emissão de OS — Entrada</h1>
				<p>Registro de recebimento e preparação da carga para entrada no
					armazém.</p>
			</div>
			<span class="status">● NOVA EMISSÃO</span>
		</div>
		<form id="osForm" method="post" action="<%= request.getContextPath() %>/entradas">
			<div class="layout">
				<section>
					<div class="card">
						<div class="ct">01 · Identificação da OS</div>
						<div class="body grid">
							<div class="span2"><label>Número da OS</label><div class="os" id="numeroOS">Gerado automaticamente</div></div>
							<div>
								<label>Data de emissão</label><input type="date" id="emissao" name="emissao">
							</div>
							<div><label>Tipo</label><input value="Entrada" readonly></div>
							<div class="span2"><label>Cliente</label><select name="codCliente" required><option value="">Selecione um cliente</option><% java.util.List<classes.Cliente> clientesDisponiveis = (java.util.List<classes.Cliente>) request.getAttribute("clientesDisponiveis"); if (clientesDisponiveis != null) { for (classes.Cliente cliente : clientesDisponiveis) { %><option value="<%= cliente.getCodCliente() %>"><%= cliente.getCodCliente() %> - <%= cliente.getNome() %></option><% } } %></select></div>
							<div class="span2">
								<label>Consignatário</label><input name="consignatario"
									placeholder="Destinatário da carga">
							</div>
						</div>
					</div>
					<div class="card">
						<div class="ct">02 · Rota e recebimento</div>
						<div class="body grid">
							<div>
								<label>Origem</label><input name="origem" placeholder="GRU">
							</div>
							<div>
								<label>Destino</label><input name="destino" placeholder="MAO">
							</div>
							<div>
								<label>Voo / Transporte</label><input name="transporte"
									placeholder="LA0000 / Terrestre">
							</div>
							<div>
								<label>Previsão chegada</label><input name="previsao" type="datetime-local">
							</div>
							<div class="span2">
								<label>Doca / Área de recebimento</label><input name="doca"
									placeholder="Doca 01">
							</div>
							<div class="span2">
								<label>Localização sugerida</label><input name="localizacao"
									placeholder="Rua / posição">
							</div>
						</div>
					</div>
					<div class="card">
						<div class="ct">03 · Dados da carga</div>
						<div class="body grid">
							<div>
								<label>Volumes</label><input type="number" min="0" id="volumes"
									value="1" name="quantidade" required>
							</div>
							<div>
								<label>Peso bruto (kg)</label><input name="peso" type="number" min="0"
									step="0.01" id="peso">
							</div>
							<div>
								<label>Tipo do produto</label><select name="tipoProduto" required><option value="PADRAO">Padrão</option><option value="PERECIVEL">Perecível</option><option value="CONSUMIVEL">Consumível</option></select>
							</div>
							<div>
								<label>Prioridade</label><select name="prioridade"><option>Normal</option>
									<option>Prioritária</option>
									<option>Crítica</option></select>
							</div>
							<div><label>Unidade</label><select name="unidade" required><option value="VOLUME">Volume</option><option value="UNIDADE">Unidade</option><option value="KG">Kg</option></select></div><div><label>SKU (opcional)</label><input name="sku" maxlength="50"></div><div><label>Código de barras</label><input name="codBarras" maxlength="50"></div><div class="span4">
								<label>Descrição da mercadoria</label>
								<textarea name="descricaoProduto" required
									 placeholder="Descrição, embalagem, observações e particularidades da carga"></textarea>
							</div>
						</div>
					</div>
					<div class="actions">
						<button class="btn secondary" type="reset">Limpar</button>
						<button class="btn secondary" type="button"
							onclick="window.print()">Imprimir prévia</button>
						<button class="btn primary" type="submit">Criar OS de Entrada</button>
					</div>
				</section>
				<aside>
					<div class="card">
						<div class="ct">Resumo da emissão</div>
						<div class="body summary">
							<div class="row">
								<span>Operação</span><b>ENTRADA</b>
							</div>
							<div class="row">
								<span>OS</span><b id="rOs"><%= request.getAttribute("osCriada") == null ? "—" : request.getAttribute("osCriada") %></b>
							</div>
							<div class="row">
								<span>Volumes</span><b id="rVol">1</b>
							</div>
							<div class="row">
								<span>Peso</span><b id="rPeso">—</b>
							</div>
						</div>
					</div>
					<div class="notice">
						<b>Orientações</b><br>Confira os dados da carga e do
						transporte antes de concluir a emissão da OS.
					</div>
				</aside>
			</div>
		</form>
	</main>
	<script>
const hoje = new Date();
const dataLocal = new Date(hoje.getTime() - hoje.getTimezoneOffset()*60000).toISOString().slice(0,10);
const campoEmissao = document.getElementById('emissao');
if (campoEmissao && !campoEmissao.value) campoEmissao.value = dataLocal;
function sincronizarResumo(){
 const qtd=document.getElementById('volumes'); const peso=document.getElementById('peso');
 const rVol=document.getElementById('rVol'); const rPeso=document.getElementById('rPeso');
 if(rVol && qtd) rVol.textContent=qtd.value||'0';
 if(rPeso && peso) rPeso.textContent=peso.value?Number(peso.value).toLocaleString('pt-BR')+' kg':'—';
}
document.querySelectorAll('#osForm input, #osForm select, #osForm textarea').forEach(e=>e.addEventListener('input',sincronizarResumo));
sincronizarResumo();
</script>
</body>
</html>
