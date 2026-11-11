package controller;

import daos.DashboardDAO;
import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/index")
public class IndexController extends HttpServlet {
    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Map<String, Integer> dados = new DashboardDAO().obterIndicadoresHoje();
            for (Map.Entry<String, Integer> e : dados.entrySet()) request.setAttribute(e.getKey(), e.getValue());
            request.setAttribute("bancoOnline", true);
            request.setAttribute("bancoMensagem", "Banco conectado");
        } catch (Exception ex) {
            getServletContext().log("Falha ao carregar indicadores do painel", ex);
            request.setAttribute("bancoOnline", false);
            request.setAttribute("bancoMensagem", "Banco indisponível");
            for (String k : new String[]{"clientes", "produtos", "posicoes", "osHoje", "entradasHoje", "saidasHoje", "movimentacoesHoje", "pendentes"}) request.setAttribute(k, 0);
        }
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}
