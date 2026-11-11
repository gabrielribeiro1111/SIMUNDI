package daos;

import database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

/** Consultas agregadas utilizadas pelo painel inicial. */
public class DashboardDAO {
    public Map<String, Integer> obterIndicadoresHoje() throws SQLException {
        String sql = "SELECT "
            + "(SELECT COUNT(*) FROM CLIENTE WHERE INATIVO = 0) AS CLIENTES, "
            + "(SELECT COUNT(*) FROM PRODUTO) AS PRODUTOS, "
            + "(SELECT COUNT(*) FROM ESTOQUE_REAL WHERE ATIVA = 1 AND VIRTUAL = 0) AS POSICOES, "
            + "(SELECT COUNT(*) FROM ORDEM_SERVICO WHERE EMISSAO >= CONVERT(date, GETDATE()) AND EMISSAO < DATEADD(day, 1, CONVERT(date, GETDATE()))) AS OS_HOJE, "
            + "(SELECT COUNT(*) FROM ORDEM_SERVICO WHERE TIPO = 'ENTRADA' AND EMISSAO >= CONVERT(date, GETDATE()) AND EMISSAO < DATEADD(day, 1, CONVERT(date, GETDATE()))) AS ENTRADAS_HOJE, "
            + "(SELECT COUNT(*) FROM ORDEM_SERVICO WHERE TIPO = 'SAIDA' AND EMISSAO >= CONVERT(date, GETDATE()) AND EMISSAO < DATEADD(day, 1, CONVERT(date, GETDATE()))) AS SAIDAS_HOJE, "
            + "(SELECT COUNT(*) FROM MOVIMENTACAO_ESTOQUE WHERE DATA_HORA >= CONVERT(date, GETDATE()) AND DATA_HORA < DATEADD(day, 1, CONVERT(date, GETDATE()))) AS MOVIMENTACOES_HOJE, "
            + "(SELECT COUNT(*) FROM ORDEM_SERVICO WHERE STATUS IN ('PENDENTE', 'EM_ANDAMENTO')) AS PENDENTES";
        Map<String, Integer> dados = new LinkedHashMap<>();
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                dados.put("clientes", rs.getInt("CLIENTES"));
                dados.put("produtos", rs.getInt("PRODUTOS"));
                dados.put("posicoes", rs.getInt("POSICOES"));
                dados.put("osHoje", rs.getInt("OS_HOJE"));
                dados.put("entradasHoje", rs.getInt("ENTRADAS_HOJE"));
                dados.put("saidasHoje", rs.getInt("SAIDAS_HOJE"));
                dados.put("movimentacoesHoje", rs.getInt("MOVIMENTACOES_HOJE"));
                dados.put("pendentes", rs.getInt("PENDENTES"));
            }
        }
        return dados;
    }
}
