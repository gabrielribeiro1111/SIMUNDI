package controller;
import javax.servlet.annotation.WebServlet;
@WebServlet("/entradas")
public class EntradasController extends BaseOrdemServicoController {
    public EntradasController() { super("ENTRADA", "/entradas.jsp"); }
}
