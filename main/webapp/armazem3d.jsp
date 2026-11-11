<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0">
<title>SIMUNDI | Armazém 3D</title>
<style>
:root {
	--indigo: #0f004f;
	--purple: #45057d;
	--pink: #e60046;
	--bg: #eef1f5;
	--line: #dce1e8;
	--ink: #17233b;
	--green: #20b568;
	--amber: #f4aa27;
	--red: #e34b5f
}

* {
	box-sizing: border-box
}

html, body {
	margin: 0;
	width: 100%;
	height: 100%;
	overflow: hidden;
	font-family: Arial, Helvetica, sans-serif;
	background: var(--bg);
	color: var(--ink)
}

button, input {
	font: inherit
}

.topbar {
	height: 64px;
	background: linear-gradient(100deg, var(--indigo), #123f70);
	color: #fff;
	display: flex;
	align-items: center;
	padding: 0 18px;
	box-shadow: 0 2px 10px #0004;
	position: relative;
	z-index: 20
}

.brand {
	display: flex;
	align-items: center;
	min-width: 260px
}

.brand-logo {
	height: 45px;
	width: auto;
	max-width: 215px;
	object-fit: contain;
	display: block
}

.brand-mark {
	width: 34px;
	height: 34px;
	position: relative
}

.brand-mark:before, .brand-mark:after {
	content: "";
	position: absolute;
	left: 2px;
	width: 28px;
	height: 7px;
	transform: skewY(19deg);
	border-radius: 2px
}

.brand-mark:before {
	top: 6px;
	background: #ef174f;
	box-shadow: 0 10px 0 #fff
}

.brand-mark:after {
	top: 26px;
	background: #7551c9
}

.brand strong {
	font-size: 23px;
	letter-spacing: 2px
}

.brand small {
	display: block;
	font-size: 9px;
	letter-spacing: 2px;
	opacity: .72
}

.nav {
	display: flex;
	height: 100%;
	align-items: stretch
}

.nav a {
	color: #fff;
	text-decoration: none;
	display: flex;
	align-items: center;
	padding: 0 16px;
	font-size: 13px;
	font-weight: bold;
	border-bottom: 3px solid transparent
}

.nav a:hover, .nav a.active {
	background: #ffffff10;
	border-color: var(--pink)
}

.spacer {
	flex: 1
}

.top-actions {
	display: flex;
	gap: 8px
}

.top-actions button {
	border: 1px solid #ffffff38;
	background: #ffffff12;
	color: #fff;
	border-radius: 7px;
	padding: 8px 11px;
	cursor: pointer
}

.top-actions button:hover {
	background: #ffffff25
}

.app {
	height: calc(100% - 64px);
	display: grid;
	grid-template-columns: 300px 1fr;
	position: relative
}

.sidebar {
	background: #fff;
	border-right: 1px solid var(--line);
	padding: 14px;
	overflow: auto;
	z-index: 12;
	box-shadow: 3px 0 12px #17233b12
}

.title {
	display: flex;
	align-items: center;
	gap: 10px;
	margin-bottom: 14px
}

.title-icon {
	width: 39px;
	height: 39px;
	border-radius: 10px;
	background: #eee8f8;
	color: var(--purple);
	display: grid;
	place-items: center;
	font-size: 21px
}

.title h1 {
	font-size: 17px;
	margin: 0;
	color: var(--indigo)
}

.title small {
	color: #7a8495
}

.search {
	position: relative;
	margin-bottom: 13px
}

.search input {
	width: 100%;
	height: 40px;
	border: 1px solid #cfd5df;
	border-radius: 8px;
	padding: 0 38px 0 11px;
	outline: none
}

.search input:focus {
	border-color: var(--purple);
	box-shadow: 0 0 0 3px #45057d12
}

.search button {
	position: absolute;
	right: 3px;
	top: 3px;
	width: 34px;
	height: 34px;
	border: 0;
	background: var(--purple);
	color: #fff;
	border-radius: 6px;
	cursor: pointer
}

.section-label {
	font-size: 10px;
	color: #8a93a3;
	text-transform: uppercase;
	letter-spacing: 1.1px;
	font-weight: bold;
	margin: 17px 2px 8px
}

.stats {
	display: grid;
	grid-template-columns: 1fr 1fr;
	gap: 8px
}

.stat {
	border: 1px solid #e1e5eb;
	border-radius: 9px;
	padding: 10px;
	background: #fafbfc
}

.stat span {
	font-size: 10px;
	color: #778091;
	text-transform: uppercase
}

.stat b {
	display: block;
	font-size: 22px;
	color: var(--indigo);
	margin-top: 3px
}

.legend {
	display: grid;
	gap: 7px
}

.legend-row {
	display: flex;
	align-items: center;
	gap: 9px;
	font-size: 12px
}

.sw {
	width: 12px;
	height: 12px;
	border-radius: 3px
}

.rack-list {
	display: grid;
	gap: 6px
}

.rack {
	display: flex;
	align-items: center;
	border: 1px solid #e1e5eb;
	border-radius: 8px;
	padding: 9px 10px;
	cursor: pointer;
	background: #fff
}

.rack:hover {
	background: #f6f2fb;
	border-color: #cbb8df
}

.rack b {
	font-size: 12px
}

.rack small {
	margin-left: auto;
	color: #7a8495
}

.details {
	border: 1px solid #ddd5ea;
	background: #f9f7fc;
	border-radius: 9px;
	padding: 11px;
	font-size: 12px;
	line-height: 1.55
}

.details b {
	color: var(--indigo)
}

.viewport {
	position: relative;
	overflow: hidden;
	background: linear-gradient(#dbe7f0, #f4f6f8)
}

#scene {
	width: 100%;
	height: 100%;
	display: block
}

.hud {
	position: absolute;
	left: 14px;
	top: 14px;
	display: flex;
	gap: 8px;
	pointer-events: none
}

.pill {
	background: #0f004fdf;
	color: #fff;
	border: 1px solid #ffffff30;
	border-radius: 7px;
	padding: 7px 10px;
	font-size: 11px;
	box-shadow: 0 3px 10px #0002;
	backdrop-filter: blur(6px)
}

.controls {
	position: absolute;
	right: 14px;
	top: 14px;
	display: grid;
	gap: 7px
}

.controls button {
	width: 39px;
	height: 39px;
	border: 1px solid #d6dbe4;
	background: #fff;
	border-radius: 8px;
	box-shadow: 0 3px 10px #17233b20;
	cursor: pointer;
	color: var(--indigo);
	font-size: 17px
}

.controls button:hover {
	background: #f4eff9
}

.help {
	position: absolute;
	left: 50%;
	bottom: 16px;
	transform: translateX(-50%);
	background: #0f004fdc;
	color: #fff;
	border-radius: 8px;
	padding: 8px 14px;
	font-size: 11px;
	box-shadow: 0 3px 12px #0003;
	white-space: nowrap
}

.toast {
	position: absolute;
	right: 16px;
	bottom: 16px;
	background: #fff;
	border-left: 4px solid var(--purple);
	border-radius: 8px;
	padding: 11px 14px;
	box-shadow: 0 5px 20px #17233b30;
	font-size: 12px;
	display: none;
	max-width: 320px
}

.toast.show {
	display: block
}

.collapse {
	display: none
}

@media ( max-width :800px) {
	.app {
		grid-template-columns: 1fr
	}
	.sidebar {
		position: absolute;
		left: 0;
		top: 0;
		bottom: 0;
		width: 285px;
		transform: translateX(-100%);
		transition: .2s
	}
	.sidebar.open {
		transform: none
	}
	.collapse {
		display: block
	}
	.nav {
		display: none
	}
	.brand {
		min-width: 0
	}
	.help {
		white-space: normal;
		width: 85%;
		text-align: center
	}
}
</style>
</head>
<body>

	<header class="topbar">
		<div class="brand">
			<img class="brand-logo" src="assets/simundi-logo.png" alt="SIMUNDI">
		</div>
		<nav class="nav">
		    <div class="brand">SIMUNDI</div>
		    <a href="<%= request.getContextPath() %>/index">Home</a>
		    <a href="<%= request.getContextPath() %>/entradas">Entradas</a>
		    <a href="<%= request.getContextPath() %>/saidas">Saídas</a>
		    <a class="active" href="<%= request.getContextPath() %>/armazem3d">Armazém 3D</a>
    		<a href="<%= request.getContextPath() %>/estoqueReal">Estoque Real</a>
		    <a href="<%= request.getContextPath() %>/clientes">Clientes</a>
		</nav>
		<div class="spacer"></div>
		<div class="top-actions">
			<button class="collapse" onclick="toggleSidebar()">☰</button>
			<button onclick="resetCamera()">⌂ Centralizar</button>
			<button onclick="toggleFullscreen()">⛶ Tela cheia</button>
		</div>
	</header>
	
	<div class="app">
		<aside class="sidebar" id="sidebar">
			<div class="title">
				<div class="title-icon">▦</div>
				<div>
					<h1>Mapa do Armazém</h1>
					<small>Visão operacional tridimensional</small>
				</div>
			</div>
			<div class="search">
				<input id="search" placeholder="Buscar objeto do modelo">
				<button onclick="searchLocation()">⌕</button>
			</div>
			<div class="section-label">Modelo 3D</div>
			<div class="stats">
				<div class="stat">
					<span>Status</span><b id="modelStatus" style="font-size: 14px">Carregando</b>
				</div>
				<div class="stat">
					<span>Progresso</span><b id="percent">0%</b>
				</div>
				<div class="stat">
					<span>Objetos</span><b id="objects">0</b>
				</div>
				<div class="stat">
					<span>Malhas</span><b id="meshes">0</b>
				</div>
			</div>
			<div class="section-label">Modelo carregado</div>
			<div class="details" id="details">
				<b>Seg.glb</b><br>Armazém logístico SIMUNDI.<br>
				<br>Use o mouse para girar, zoom e consultar objetos do modelo.
			</div>
			<div class="section-label">Ações</div>
			<div class="rack-list">
				<div class="rack" onclick="resetCamera()">
					<b>⌂ Enquadrar modelo</b><small>Visão geral</small>
				</div>
				<div class="rack" onclick="topView()">
					<b>⌃ Vista superior</b><small>Topo</small>
				</div>
				<div class="rack" onclick="frontView()">
					<b>▣ Vista frontal</b><small>Frente</small>
				</div>
			</div>
		</aside>
		<main class="viewport" id="viewport">
			<canvas id="scene"></canvas>
			<div class="hud">
				<div class="pill">● SIMUNDI 3D</div>
				<div class="pill" id="fps">FPS: --</div>
				<div class="pill" id="cameraMode">Câmera: orbital</div>
			</div>
			<div class="controls">
				<button title="Vista superior" onclick="topView()">⌃</button>
				<button title="Vista frontal" onclick="frontView()">▣</button>
				<button title="Aproximar" onclick="zoomCamera(.85)">＋</button>
				<button title="Afastar" onclick="zoomCamera(1.18)">−</button>
			</div>
			<div class="help">Arraste para girar • Scroll para zoom •
				Clique no modelo para consultar</div>
			<div class="toast" id="toast"></div>
		</main>
	</div>
	<script type="importmap">{"imports":{"three":"https://cdn.jsdelivr.net/npm/three@0.160.0/build/three.module.js","three/addons/":"https://cdn.jsdelivr.net/npm/three@0.160.0/examples/jsm/"}}</script>
	<script type="module">
import * as THREE from 'three';
import { GLTFLoader } from 'three/addons/loaders/GLTFLoader.js';
import { OrbitControls } from 'three/addons/controls/OrbitControls.js';

const MODEL_URL = '<%=request.getContextPath()%>/assets/models/Seg.glb';
const canvas=document.getElementById('scene'), viewport=document.getElementById('viewport');
const scene=new THREE.Scene(); scene.background=new THREE.Color(0xdce8f0);
const camera=new THREE.PerspectiveCamera(48,1,0.05,10000);
const renderer=new THREE.WebGLRenderer({canvas,antialias:false,powerPreference:'high-performance',alpha:false,stencil:false});
const PERFORMANCE_PIXEL_RATIO=Math.min(devicePixelRatio,1.15); renderer.setPixelRatio(PERFORMANCE_PIXEL_RATIO); renderer.shadowMap.enabled=false;
renderer.outputColorSpace=THREE.SRGBColorSpace; renderer.toneMapping=THREE.ACESFilmicToneMapping; renderer.toneMappingExposure=1.05;
const controls=new OrbitControls(camera,renderer.domElement); controls.enableDamping=false; controls.screenSpacePanning=true; controls.zoomToCursor=true;
const raycaster=new THREE.Raycaster(), pointer=new THREE.Vector2();
let model=null, modelBox=null, modelCenter=new THREE.Vector3(), modelSize=new THREE.Vector3(), selectable=[], selected=null;
let frame=0,lastFps=performance.now(), pointerDown={x:0,y:0};

scene.add(new THREE.HemisphereLight(0xffffff,0x657080,2.5));
const key=new THREE.DirectionalLight(0xffffff,2.4); key.position.set(25,45,25); key.castShadow=false; scene.add(key);
const fill=new THREE.DirectionalLight(0xbfd5ff,1.4); fill.position.set(-30,20,-20); scene.add(fill);

let renderQueued=false;function requestRender(){if(renderQueued)return;renderQueued=true;requestAnimationFrame(()=>{renderQueued=false;renderer.render(scene,camera);countFrame()})}
function resize(){const w=Math.max(1,viewport.clientWidth),h=Math.max(1,viewport.clientHeight);camera.aspect=w/h;camera.updateProjectionMatrix();renderer.setSize(w,h,false);requestRender()}
window.addEventListener('resize',resize); resize();

function fitModel(mult=1.35, direction='iso'){
 if(!modelBox)return; modelBox.getCenter(modelCenter); modelBox.getSize(modelSize);
 const maxDim=Math.max(modelSize.x,modelSize.y,modelSize.z)||10;
 const fov=THREE.MathUtils.degToRad(camera.fov); let dist=(maxDim/2)/Math.tan(fov/2)*mult;
 if(direction==='top') camera.position.set(modelCenter.x,modelCenter.y+dist,modelCenter.z+.001);
 else if(direction==='front') camera.position.set(modelCenter.x,modelCenter.y+modelSize.y*.18,modelCenter.z+dist);
 else camera.position.set(modelCenter.x+dist*.72,modelCenter.y+dist*.48,modelCenter.z+dist*.72);
 camera.near=Math.max(.01,maxDim/10000); camera.far=Math.max(1000,maxDim*30); camera.updateProjectionMatrix();
 controls.target.copy(modelCenter); controls.minDistance=Math.max(.1,maxDim*.015); controls.maxDistance=maxDim*8; controls.update();requestRender();
}

function setStatus(text,pct){document.getElementById('modelStatus').textContent=text;if(pct!=null)document.getElementById('percent').textContent=pct+'%'}
const loader=new GLTFLoader();
loader.load(MODEL_URL,gltf=>{
 model=gltf.scene; scene.add(model); let objects=0,meshes=0;
 model.traverse(o=>{objects++; if(o.isMesh){meshes++;selectable.push(o);o.castShadow=false;o.receiveShadow=false;o.matrixAutoUpdate=false;o.updateMatrix();if(o.material){const mats=Array.isArray(o.material)?o.material:[o.material];mats.forEach(m=>{if(m.map)m.map.colorSpace=THREE.SRGBColorSpace;m.needsUpdate=true})}}});
 modelBox=new THREE.Box3().setFromObject(model); fitModel();
 document.getElementById('objects').textContent=objects.toLocaleString('pt-BR');document.getElementById('meshes').textContent=meshes.toLocaleString('pt-BR');
 setStatus('Pronto • modo desempenho',100);requestRender();document.getElementById('details').innerHTML='<b>Seg.glb carregado</b><br>Dimensões: '+modelSize.x.toFixed(1)+' × '+modelSize.y.toFixed(1)+' × '+modelSize.z.toFixed(1)+'<br>Malhas: '+meshes.toLocaleString('pt-BR')+'<br><br>Clique em uma parte do modelo para identificá-la.';showToast('Armazém 3D carregado com sucesso.');
},xhr=>{if(xhr.total){const p=Math.min(99,Math.round(xhr.loaded/xhr.total*100));setStatus('Carregando',p)}},err=>{console.error(err);setStatus('Erro',0);document.getElementById('details').innerHTML='<b>Não foi possível carregar Seg.glb.</b><br>Confirme se o arquivo está em assets/models/Seg.glb.';showToast('Erro ao carregar o modelo 3D.');});

function pick(e){if(Math.hypot(e.clientX-pointerDown.x,e.clientY-pointerDown.y)>5)return;const r=canvas.getBoundingClientRect();pointer.x=((e.clientX-r.left)/r.width)*2-1;pointer.y=-((e.clientY-r.top)/r.height)*2+1;raycaster.setFromCamera(pointer,camera);const hit=raycaster.intersectObjects(selectable,false)[0];if(!hit)return;selected=hit.object;const name=selected.name||selected.parent?.name||'Objeto sem nome';document.getElementById('details').innerHTML='<b>'+escapeHtml(name)+'</b><br>Tipo: '+escapeHtml(selected.type)+'<br>Posição: X '+hit.point.x.toFixed(2)+' • Y '+hit.point.y.toFixed(2)+' • Z '+hit.point.z.toFixed(2);showToast('Objeto selecionado: '+name)}
canvas.addEventListener('pointerdown',e=>pointerDown={x:e.clientX,y:e.clientY});canvas.addEventListener('click',pick);controls.addEventListener('change',requestRender);
function escapeHtml(v){return String(v).replace(/[&<>'"]/g,c=>({'&':'&amp;','<':'&lt;','>':'&gt;',"'":'&#39;','"':'&quot;'}[c]))}
function showToast(t){const x=document.getElementById('toast');x.textContent=t;x.classList.add('show');clearTimeout(x._t);x._t=setTimeout(()=>x.classList.remove('show'),2600)}
function searchLocation(){const q=document.getElementById('search').value.trim().toLowerCase();if(!q)return showToast('Digite o nome de um objeto.');const o=selectable.find(x=>(x.name||'').toLowerCase().includes(q)||(x.parent?.name||'').toLowerCase().includes(q));if(!o)return showToast('Nenhum objeto encontrado para "'+q+'".');const b=new THREE.Box3().setFromObject(o),c=b.getCenter(new THREE.Vector3()),sz=b.getSize(new THREE.Vector3()),d=Math.max(sz.x,sz.y,sz.z,modelSize.length()*.02)*4;controls.target.copy(c);camera.position.set(c.x+d,c.y+d*.6,c.z+d);controls.update();document.getElementById('details').innerHTML='<b>'+escapeHtml(o.name||o.parent?.name||'Objeto')+'</b><br>Objeto localizado no modelo.';showToast('Objeto localizado.')}
window.searchLocation=searchLocation; window.resetCamera=()=>fitModel(1.35,'iso');window.topView=()=>fitModel(1.25,'top');window.frontView=()=>fitModel(1.3,'front');window.zoomCamera=f=>{const d=camera.position.clone().sub(controls.target).multiplyScalar(f);camera.position.copy(controls.target).add(d);controls.update()};window.toggleFullscreen=()=>{if(!document.fullscreenElement)document.documentElement.requestFullscreen?.();else document.exitFullscreen?.()};window.toggleSidebar=()=>document.getElementById('sidebar').classList.toggle('open');
document.getElementById('search').addEventListener('keydown',e=>{if(e.key==='Enter')searchLocation()});
function countFrame(){frame++;const now=performance.now();if(now-lastFps>1000){document.getElementById('fps').textContent='FPS ativo: '+frame;frame=0;lastFps=now}}
// Renderização sob demanda: quando a câmera está parada, a GPU deixa de redesenhar o GLB inteiro.
requestRender();
</script>
</body>
</html>
