package controller;
import javax.servlet.annotation.WebServlet;
@WebServlet("/saidas")
public class SaidasController extends BaseOrdemServicoController {
    public SaidasController() { super("SAIDA", "/saidas.jsp"); }
}
