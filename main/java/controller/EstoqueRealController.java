package controller;

import classes.EstoqueReal;
import classes.TipoEstoque;
import daos.EstoqueRealDAO;
import daos.TipoEstoqueDAO;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/** Controller das duas visualizações do módulo Estoque Real. */
@WebServlet("/estoqueReal")
public class EstoqueRealController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final EstoqueRealDAO estoqueDAO = new EstoqueRealDAO();
    private final TipoEstoqueDAO tipoDAO = new TipoEstoqueDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        consumirFlash(request);
        String visao = normalizarVisao(request.getParameter("visao"));
        request.setAttribute("visao", visao);

        String editarTipo = request.getParameter("editarTipo");
        String editarEstoque = request.getParameter("editarEstoque");
        try {
            if (editarTipo != null && !editarTipo.trim().isEmpty()) {
                TipoEstoque tipo = tipoDAO.buscarPorId(parseId(editarTipo, "tipo de estoque"));
                if (tipo == null) request.setAttribute("mensagemErro", "Tipo de estoque não encontrado.");
                else request.setAttribute("tipoEdicao", tipo);
                visao = "tipos";
            }
            if (editarEstoque != null && !editarEstoque.trim().isEmpty()) {
                EstoqueReal estoque = estoqueDAO.buscarPorId(parseId(editarEstoque, "estoque real"));
                if (estoque == null) request.setAttribute("mensagemErro", "Estrutura física não encontrada.");
                else request.setAttribute("estoqueEdicao", estoque);
                visao = "mapa";
            }
        } catch (IllegalArgumentException e) {
            request.setAttribute("mensagemErro", e.getMessage());
        } catch (SQLException e) {
            getServletContext().log("Erro ao carregar registro para edição no Estoque Real.", e);
            request.setAttribute("mensagemErro", "Não foi possível carregar o registro para edição.");
        }
        request.setAttribute("visao", visao);
        carregarListas(request);
        request.getRequestDispatcher("/estoqueReal.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String acao = valor(request.getParameter("acao"));
        String visao = normalizarVisao(request.getParameter("visao"));
        try {
            switch (acao) {
                case "salvarTipo": tipoDAO.inserir(lerTipo(request)); visao = "tipos"; break;
                case "atualizarTipo":
                    TipoEstoque tipo = lerTipo(request);
                    tipo.setCodTipoEstoque(parseId(request.getParameter("codTipoEstoque"), "tipo de estoque"));
                    if (!tipoDAO.atualizar(tipo)) throw new IllegalArgumentException("Tipo de estoque não encontrado ou não atualizado.");
                    visao = "tipos"; break;
                case "excluirTipo":
                    if (!tipoDAO.excluir(parseId(request.getParameter("codTipoEstoque"), "tipo de estoque")))
                        throw new IllegalArgumentException("Tipo de estoque não encontrado ou já removido.");
                    visao = "tipos"; break;
                case "salvarEstoque": estoqueDAO.inserir(lerEstoque(request)); visao = "mapa"; break;
                case "atualizarEstoque":
                    EstoqueReal estoque = lerEstoque(request);
                    estoque.setId(parseId(request.getParameter("id"), "estoque real"));
                    if (!estoqueDAO.atualizar(estoque)) throw new IllegalArgumentException("Estrutura não encontrada ou não atualizada.");
                    visao = "mapa"; break;
                case "excluirEstoque":
                    if (!estoqueDAO.excluir(parseId(request.getParameter("id"), "estoque real")))
                        throw new IllegalArgumentException("Estrutura não encontrada ou já removida.");
                    visao = "mapa"; break;
                default: throw new IllegalArgumentException("Operação não reconhecida.");
            }
            String msg = acao.endsWith("Tipo") ? "Operação de tipo de estoque realizada com sucesso."
                    : acao.endsWith("Estoque") ? "Operação de estrutura realizada com sucesso."
                    : "Registro removido com sucesso.";
            setFlash(request.getSession(), msg, false);
            response.sendRedirect(request.getContextPath() + "/estoqueReal?visao=" + visao);
        } catch (IllegalArgumentException e) {
            request.setAttribute("mensagemErro", e.getMessage());
            request.setAttribute("visao", visao);
            preservarFormulario(request, acao);
            carregarListas(request);
            request.getRequestDispatcher("/estoqueReal.jsp").forward(request, response);
        } catch (SQLException e) {
            getServletContext().log("Erro de banco no módulo Estoque Real. Ação: " + acao, e);
            request.setAttribute("mensagemErro", "Não foi possível concluir a operação. Verifique os vínculos e os dados informados.");
            request.setAttribute("visao", visao);
            preservarFormulario(request, acao);
            carregarListas(request);
            request.getRequestDispatcher("/estoqueReal.jsp").forward(request, response);
        }
    }

    private TipoEstoque lerTipo(HttpServletRequest request) {
        TipoEstoque t = new TipoEstoque();
        String descricao = obrigatorio(request.getParameter("descTipoEstoque"), "Descrição");
        String unidade = obrigatorio(request.getParameter("unidadeEstoque"), "Unidade de estoque");
        validarTamanho(descricao, 150, "Descrição");
        validarTamanho(unidade, 30, "Unidade de estoque");
        t.setDescTipoEstoque(descricao);
        t.setUnidadeEstoque(unidade);
        return t;
    }

    private EstoqueReal lerEstoque(HttpServletRequest request) {
        EstoqueReal e = new EstoqueReal();
        e.setIdentificacao(obrigatorio(request.getParameter("identificacao"), "Identificação"));
        validarTamanho(e.getIdentificacao(), 100, "Identificação");
        e.setPosXInicial(nullableInt(request.getParameter("posXInicial"), "X inicial"));
        e.setPosYInicial(nullableInt(request.getParameter("posYInicial"), "Y inicial"));
        e.setPosZInicial(nullableInt(request.getParameter("posZInicial"), "Z inicial"));
        e.setPosXFinal(nullableInt(request.getParameter("posXFinal"), "X final"));
        e.setPosYFinal(nullableInt(request.getParameter("posYFinal"), "Y final"));
        e.setPosZFinal(nullableInt(request.getParameter("posZFinal"), "Z final"));
        e.setCodTipoEstoque(parseId(request.getParameter("codTipoEstoque"), "tipo de estoque"));
        String capacidade = obrigatorio(request.getParameter("armazenamentoMax"), "Armazenamento máximo").replace(',', '.');
        try {
            BigDecimal valor = new BigDecimal(capacidade);
            if (valor.signum() < 0) throw new IllegalArgumentException("Armazenamento máximo não pode ser negativo.");
            e.setArmazenamentoMax(valor);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Armazenamento máximo inválido.");
        }
        e.setVirtual(request.getParameter("virtual") != null);
        e.setAtiva(request.getParameter("ativa") != null);
        return e;
    }

    private Integer nullableInt(String raw, String campo) {
        String v = valor(raw);
        if (v.isEmpty()) return null;
        try { return Integer.valueOf(v); }
        catch (NumberFormatException e) { throw new IllegalArgumentException(campo + " deve ser um número inteiro."); }
    }

    private void preservarFormulario(HttpServletRequest request, String acao) {
        try {
            if (acao.toLowerCase().contains("tipo")) {
                TipoEstoque t = lerTipo(request);
                if ("atualizarTipo".equals(acao)) t.setCodTipoEstoque(parseId(request.getParameter("codTipoEstoque"), "tipo de estoque"));
                request.setAttribute("tipoEdicao", t);
            } else if (acao.toLowerCase().contains("estoque")) {
                EstoqueReal e = lerEstoque(request);
                if ("atualizarEstoque".equals(acao)) e.setId(parseId(request.getParameter("id"), "estoque real"));
                request.setAttribute("estoqueEdicao", e);
            }
        } catch (IllegalArgumentException ignored) {
            // Os dados brutos são preservados pela própria requisição quando possível.
        }
    }

    private void carregarListas(HttpServletRequest request) {
        try { request.setAttribute("tiposEstoque", tipoDAO.listarTodos()); }
        catch (SQLException e) { getServletContext().log("Erro ao listar tipos de estoque.", e); request.setAttribute("mensagemErro", "Não foi possível carregar os tipos de estoque."); }
        try { request.setAttribute("estoquesReais", estoqueDAO.listarTodos()); }
        catch (SQLException e) { getServletContext().log("Erro ao listar estruturas físicas.", e); request.setAttribute("mensagemErro", "Não foi possível carregar as estruturas físicas."); }
    }

    private void consumirFlash(HttpServletRequest request) {
        HttpSession s = request.getSession(false);
        if (s == null) return;
        Object ok = s.getAttribute("estoqueRealFlashSucesso");
        Object erro = s.getAttribute("estoqueRealFlashErro");
        if (ok != null) { request.setAttribute("mensagemSucesso", ok); s.removeAttribute("estoqueRealFlashSucesso"); }
        if (erro != null) { request.setAttribute("mensagemErro", erro); s.removeAttribute("estoqueRealFlashErro"); }
    }

    private void setFlash(HttpSession s, String msg, boolean erro) {
        s.setAttribute(erro ? "estoqueRealFlashErro" : "estoqueRealFlashSucesso", msg);
    }
    private static String normalizarVisao(String v) { return "tipos".equals(v) ? "tipos" : "mapa"; }
    private static String valor(String v) { return v == null ? "" : v.trim(); }
    private static String obrigatorio(String v, String campo) {
        if (v == null || v.trim().isEmpty()) throw new IllegalArgumentException("Preencha o campo " + campo + ".");
        return v.trim();
    }
    private static int parseId(String v, String campo) {
        try { return Integer.parseInt(obrigatorio(v, "Código do " + campo)); }
        catch (NumberFormatException e) { throw new IllegalArgumentException("Código do " + campo + " inválido."); }
    }
    private static void validarTamanho(String v, int max, String campo) {
        if (v != null && v.length() > max) throw new IllegalArgumentException(campo + " deve ter no máximo " + max + " caracteres.");
    }
}
