package controller;

import classes.Cliente;
import classes.OrdemServico;
import classes.OrdemServicoItem;
import daos.ClienteDAO;
import daos.OrdemServicoDAO;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

abstract class BaseOrdemServicoController extends HttpServlet {
    private final String tipoOS;
    private final String view;
    BaseOrdemServicoController(String tipoOS, String view) { this.tipoOS = tipoOS; this.view = view; }

    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        carregarClientes(request);
        request.getRequestDispatcher(view).forward(request, response);
    }

    @Override protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        try {
            String codCliente = obrigatorio(request, "codCliente");
            String descricao = obrigatorio(request, "descricaoProduto");
            String unidade = obrigatorio(request, "unidade");
            String tipoItem = obrigatorio(request, "tipoProduto");
            BigDecimal quantidade = new BigDecimal(obrigatorio(request, "quantidade").replace(',', '.'));
            if (quantidade.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
            if (quantidade.scale() > 4 || quantidade.precision() > 18) throw new IllegalArgumentException("A quantidade deve respeitar até 14 dígitos inteiros e 4 casas decimais.");
            if (descricao.length() > 255) throw new IllegalArgumentException("A descrição da mercadoria deve ter no máximo 255 caracteres.");
            String sku = vazioParaNull(request.getParameter("sku"));
            String codBarras = vazioParaNull(request.getParameter("codBarras"));
            if (sku != null && sku.length() > 50) throw new IllegalArgumentException("O SKU deve ter no máximo 50 caracteres.");
            if (codBarras != null && codBarras.length() > 50) throw new IllegalArgumentException("O código de barras deve ter no máximo 50 caracteres.");
            if (!tipoItem.equals("PERECIVEL") && !tipoItem.equals("CONSUMIVEL") && !tipoItem.equals("PADRAO")) throw new IllegalArgumentException("Tipo de produto inválido.");
            Cliente cliente = new ClienteDAO().buscarPorCodigo(codCliente);
            if (cliente == null || cliente.isInativo() || cliente.isBloqueado()) throw new IllegalArgumentException("Selecione um cliente cadastrado, ativo e desbloqueado.");

            OrdemServico os = new OrdemServico();
            os.setTipo(tipoOS);
            os.setCodCliente(codCliente);
            os.setStatus("PENDENTE");
            String emissao = request.getParameter("emissao");
            if (emissao != null && !emissao.trim().isEmpty()) os.setEmissao(LocalDate.parse(emissao).atStartOfDay());
            os.setObs(montarObservacoes(request));

            OrdemServicoItem item = new OrdemServicoItem();
            item.setSku(sku);
            item.setDescProduto(descricao);
            item.setCodBarras(codBarras);
            item.setQtde(quantidade);
            item.setUnidade(unidade);
            item.setTipo(tipoItem);
            new OrdemServicoDAO().criarComItem(os, item);
            request.setAttribute("mensagemSucesso", "OS " + os.getOrdemServico() + " criada com sucesso.");
            request.setAttribute("osCriada", os.getOrdemServico());
        } catch (IllegalArgumentException ex) {
            request.setAttribute("mensagemErro", ex.getMessage());
        } catch (Exception ex) {
            getServletContext().log("Falha ao criar OS de " + tipoOS, ex);
            request.setAttribute("mensagemErro", "Não foi possível criar a OS. Verifique a conexão e os dados do banco.");
        }
        carregarClientes(request);
        request.getRequestDispatcher(view).forward(request, response);
    }

    private void carregarClientes(HttpServletRequest request) {
        try {
            List<Cliente> ativos = new ArrayList<>();
            for (Cliente c : new ClienteDAO().listarTodos()) if (!c.isInativo() && !c.isBloqueado()) ativos.add(c);
            request.setAttribute("clientesDisponiveis", ativos);
        } catch (SQLException ex) {
            getServletContext().log("Falha ao carregar clientes", ex);
            request.setAttribute("mensagemErro", "Não foi possível carregar os clientes. Verifique a conexão com o banco.");
            request.setAttribute("clientesDisponiveis", new ArrayList<Cliente>());
        }
    }

    private static String obrigatorio(HttpServletRequest request, String nome) {
        String valor = request.getParameter(nome);
        if (valor == null || valor.trim().isEmpty()) throw new IllegalArgumentException("Preencha o campo " + nome + ".");
        return valor.trim();
    }
    private static String vazioParaNull(String valor) { return valor == null || valor.trim().isEmpty() ? null : valor.trim(); }
    private static String montarObservacoes(HttpServletRequest r) {
        StringBuilder b = new StringBuilder();
        adicionar(b, "Consignatário", r.getParameter("consignatario"));
        adicionar(b, "Origem", r.getParameter("origem"));
        adicionar(b, "Destino", r.getParameter("destino"));
        adicionar(b, "Transporte", r.getParameter("transporte"));
        adicionar(b, "Previsão", r.getParameter("previsao"));
        adicionar(b, "Doca/Área", r.getParameter("doca"));
        adicionar(b, "Localização", r.getParameter("localizacao"));
        adicionar(b, "Peso bruto (kg)", r.getParameter("peso"));
        adicionar(b, "Prioridade", r.getParameter("prioridade"));
        adicionar(b, "Observações da carga", r.getParameter("observacoesCarga"));
        return b.length() == 0 ? null : b.toString();
    }
    private static void adicionar(StringBuilder b, String rotulo, String valor) {
        if (valor != null && !valor.trim().isEmpty()) { if (b.length() > 0) b.append(" | "); b.append(rotulo).append(": ").append(valor.trim()); }
    }
}
