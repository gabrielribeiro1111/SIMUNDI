package controller;

import classes.Cliente;
import daos.ClienteDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Controller responsável pelo cadastro, edição, listagem e remoção de clientes.
 * Toda a persistência é delegada ao ClienteDAO.
 */
@WebServlet("/clientes")
public class ClientesController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final ClienteDAO clienteDAO = new ClienteDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        consumirMensagemFlash(request);
        String codigoEdicao = request.getParameter("editar");

        if (codigoEdicao != null && !codigoEdicao.trim().isEmpty()) {
            try {
                Cliente cliente = clienteDAO.buscarPorCodigo(codigoEdicao.trim());
                if (cliente == null) {
                    request.setAttribute("mensagemErro", "Cliente não encontrado.");
                } else {
                    request.setAttribute("clienteEdicao", cliente);
                }
            } catch (SQLException e) {
                getServletContext().log("Erro ao consultar cliente para edição.", e);
                request.setAttribute("mensagemErro", "Não foi possível carregar o cliente para edição.");
            }
        }

        carregarLista(request);
        request.getRequestDispatcher("/clientes.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String acao = valor(request.getParameter("acao"));

        try {
            switch (acao) {
                case "salvar":
                    inserir(request);
                    break;
                case "atualizar":
                    atualizar(request);
                    break;
                case "excluir":
                    excluir(request);
                    break;
                default:
                    throw new IllegalArgumentException("Operação não reconhecida.");
            }

            String mensagem = "salvar".equals(acao) ? "Cliente cadastrado com sucesso."
                    : "atualizar".equals(acao) ? "Cliente atualizado com sucesso."
                    : "Cliente removido com sucesso.";
            redirecionarComMensagem(request, response, mensagem, false);

        } catch (IllegalArgumentException e) {
            request.setAttribute("mensagemErro", e.getMessage());
            preservarFormulario(request);
            carregarLista(request);
            request.getRequestDispatcher("/clientes.jsp").forward(request, response);

        } catch (SQLException e) {
            getServletContext().log("Erro de banco na manutenção de clientes. Ação: " + acao, e);

            String mensagem = "Não foi possível concluir a operação no banco de dados.";
            if ("excluir".equals(acao)) {
                mensagem = "Não foi possível remover o cliente. Verifique se existem ordens de serviço vinculadas a ele.";
            } else if ("salvar".equals(acao)) {
                mensagem = "Não foi possível cadastrar o cliente. Verifique se o código ou documento já está cadastrado.";
            }

            request.setAttribute("mensagemErro", mensagem);
            preservarFormulario(request);
            carregarLista(request);
            request.getRequestDispatcher("/clientes.jsp").forward(request, response);
        }
    }

    private void inserir(HttpServletRequest request) throws SQLException {
        Cliente cliente = lerCliente(request, true);
        clienteDAO.inserir(cliente);
    }

    private void atualizar(HttpServletRequest request) throws SQLException {
        Cliente cliente = lerCliente(request, false);

        if (clienteDAO.buscarPorCodigo(cliente.getCodCliente()) == null) {
            throw new IllegalArgumentException("O cliente que você tentou atualizar não existe mais.");
        }

        if (!clienteDAO.atualizar(cliente)) {
            throw new IllegalArgumentException("Nenhum cliente foi atualizado.");
        }

    }

    private void excluir(HttpServletRequest request) throws SQLException {
        String codigo = obrigatorio(request.getParameter("codCliente"), "Código do cliente");

        if (!clienteDAO.excluir(codigo)) {
            throw new IllegalArgumentException("Cliente não encontrado ou já removido.");
        }

    }

    private Cliente lerCliente(HttpServletRequest request, boolean novo) {
        Cliente cliente = new Cliente();
        String codigo = novo
                ? null
                : obrigatorio(request.getParameter("codCliente"), "Código");
        String nome = obrigatorio(request.getParameter("nome"), "Nome");
        String tipo = obrigatorio(request.getParameter("tipo"), "Tipo");
        String documento = obrigatorio(request.getParameter("documento"), "Documento");
        String contato = vazioParaNull(request.getParameter("contato"));
        
        if (!novo) {
            validarTamanho(codigo, 30, "Código");
        }
        validarTamanho(nome, 150, "Nome");
        validarTamanho(documento, 20, "Documento");
        validarTamanho(contato, 255, "Contato");

        if (!"PF".equals(tipo) && !"PJ".equals(tipo)) {
            throw new IllegalArgumentException("Tipo de cliente inválido. Selecione Pessoa Física ou Pessoa Jurídica.");
        }

        cliente.setCodCliente(codigo);
        cliente.setNome(nome);
        cliente.setTipo(tipo);
        cliente.setDocumento(documento);
        cliente.setContato(contato);
        cliente.setInativo(request.getParameter("inativo") != null);
        cliente.setBloqueado(request.getParameter("bloqueado") != null);

        return cliente;
    }

    private void preservarFormulario(HttpServletRequest request) {
        String acao = valor(request.getParameter("acao"));

        try {
            if ("excluir".equals(acao)) {
                return;
            }

            Cliente cliente = lerCliente(request, "salvar".equals(acao));
            request.setAttribute("clienteEdicao", cliente);
            request.setAttribute("modoEdicao", "atualizar".equals(acao));

        } catch (IllegalArgumentException ignored) {
            Cliente cliente = new Cliente();
            cliente.setCodCliente(valor(request.getParameter("codCliente")));
            cliente.setNome(valor(request.getParameter("nome")));
            cliente.setTipo(valor(request.getParameter("tipo")));
            cliente.setDocumento(valor(request.getParameter("documento")));
            cliente.setContato(valor(request.getParameter("contato")));
            cliente.setInativo(request.getParameter("inativo") != null);
            cliente.setBloqueado(request.getParameter("bloqueado") != null);

            request.setAttribute("clienteEdicao", cliente);
            request.setAttribute("modoEdicao", "atualizar".equals(acao));
        }
    }

    private void carregarLista(HttpServletRequest request) {
        try {
            List<Cliente> clientes = clienteDAO.listarTodos();
            request.setAttribute("clientes", clientes);
        } catch (SQLException e) {
            getServletContext().log("Erro ao listar clientes.", e);
            request.setAttribute("mensagemErro", "Não foi possível carregar a lista de clientes.");
        }
    }

    private void consumirMensagemFlash(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        Object sucesso = session.getAttribute("clientesFlashSucesso");
        Object erro = session.getAttribute("clientesFlashErro");
        if (sucesso != null) {
            request.setAttribute("mensagemSucesso", sucesso);
            session.removeAttribute("clientesFlashSucesso");
        }
        if (erro != null) {
            request.setAttribute("mensagemErro", erro);
            session.removeAttribute("clientesFlashErro");
        }
    }

    private void redirecionarComMensagem(HttpServletRequest request, HttpServletResponse response,
            String mensagem, boolean erro) throws IOException {
        setFlash(request.getSession(), mensagem, erro);
        response.sendRedirect(request.getContextPath() + "/clientes");
    }

    private void setFlash(HttpSession session, String mensagem, boolean erro) {
        session.setAttribute(erro ? "clientesFlashErro" : "clientesFlashSucesso", mensagem);
    }

    private static String obrigatorio(String valor, String campo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("Preencha o campo " + campo + ".");
        }
        return valor.trim();
    }

    private static String valor(String valor) {
        return valor == null ? "" : valor.trim();
    }

    private static String vazioParaNull(String valor) {
        String tratado = valor(valor);
        return tratado.isEmpty() ? null : tratado;
    }

    private static void validarTamanho(String valor, int maximo, String campo) {
        if (valor != null && valor.length() > maximo) {
            throw new IllegalArgumentException(campo + " deve ter no máximo " + maximo + " caracteres.");
        }
    }
}
