package daos;

import classes.OrdemServico;
import classes.OrdemServicoItem;
import database.DatabaseConnection;
import java.sql.*;
import java.util.*;

public class OrdemServicoDAO {
	private OrdemServico map(ResultSet r) throws SQLException {
		OrdemServico o = new OrdemServico();
		o.setOrdemServico(r.getInt("ORDEM_SERVICO"));
		o.setTipo(r.getString("TIPO"));
		o.setCodCliente(r.getString("COD_CLIENTE"));
		Timestamp ts = r.getTimestamp("EMISSAO");
		o.setEmissao(ts == null ? null : ts.toLocalDateTime());
		int d = r.getInt("DURACAO");
		o.setDuracao(r.wasNull() ? null : d);
		o.setStatus(r.getString("STATUS"));
		o.setObs(r.getString("OBS"));
		return o;
	}

	public void inserir(OrdemServico o) throws SQLException {
		boolean usarEmissaoDefault = (o.getEmissao() == null);
		String s = usarEmissaoDefault
				? "INSERT INTO ORDEM_SERVICO(TIPO,COD_CLIENTE,DURACAO,STATUS,OBS) VALUES(?,?,?,?,?)"
				: "INSERT INTO ORDEM_SERVICO(TIPO,COD_CLIENTE,EMISSAO,DURACAO,STATUS,OBS) VALUES(?,?,?,?,?,?)";
		try (Connection c = DatabaseConnection.getConnection();
				PreparedStatement p = c.prepareStatement(s, Statement.RETURN_GENERATED_KEYS)) {
			p.setString(1, o.getTipo());
			p.setString(2, o.getCodCliente());
			int i = 3;
			if (!usarEmissaoDefault)
				p.setTimestamp(i++, Timestamp.valueOf(o.getEmissao()));
			if (o.getDuracao() == null)
				p.setNull(i++, Types.INTEGER);
			else
				p.setInt(i++, o.getDuracao());
			p.setString(i++, o.getStatus() == null ? "PENDENTE" : o.getStatus());
			p.setString(i, o.getObs());
			p.executeUpdate();
			try (ResultSet k = p.getGeneratedKeys()) {
				if (k.next())
					o.setOrdemServico(k.getInt(1));
			}
		}
	}

	public OrdemServico buscarPorId(int id) throws SQLException {
		try (Connection c = DatabaseConnection.getConnection();
				PreparedStatement p = c.prepareStatement("SELECT * FROM ORDEM_SERVICO WHERE ORDEM_SERVICO=?")) {
			p.setInt(1, id);
			try (ResultSet r = p.executeQuery()) {
				return r.next() ? map(r) : null;
			}
		}
	}

	public List<OrdemServico> listarTodos() throws SQLException {
		List<OrdemServico> l = new ArrayList<>();
		try (Connection c = DatabaseConnection.getConnection();
				PreparedStatement p = c.prepareStatement("SELECT * FROM ORDEM_SERVICO ORDER BY EMISSAO DESC");
				ResultSet r = p.executeQuery()) {
			while (r.next())
				l.add(map(r));
		}
		return l;
	}

	public boolean atualizar(OrdemServico o) throws SQLException {
		String s = "UPDATE ORDEM_SERVICO SET TIPO=?,COD_CLIENTE=?,EMISSAO=?,DURACAO=?,STATUS=?,OBS=? WHERE ORDEM_SERVICO=?";
		try (Connection c = DatabaseConnection.getConnection(); PreparedStatement p = c.prepareStatement(s)) {
			p.setString(1, o.getTipo());
			p.setString(2, o.getCodCliente());
			if (o.getEmissao() == null)
				p.setNull(3, Types.TIMESTAMP);
			else
				p.setTimestamp(3, Timestamp.valueOf(o.getEmissao()));
			if (o.getDuracao() == null)
				p.setNull(4, Types.INTEGER);
			else
				p.setInt(4, o.getDuracao());
			p.setString(5, o.getStatus());
			p.setString(6, o.getObs());
			p.setInt(7, o.getOrdemServico());
			return p.executeUpdate() > 0;
		}
	}

	public boolean excluir(int id) throws SQLException {
		try (Connection c = DatabaseConnection.getConnection();
				PreparedStatement p = c.prepareStatement("DELETE FROM ORDEM_SERVICO WHERE ORDEM_SERVICO=?")) {
			p.setInt(1, id);
			return p.executeUpdate() > 0;
		}
	}


    /** Cria o cabeçalho da OS e seu primeiro item na mesma transação. */
    public void criarComItem(OrdemServico os, OrdemServicoItem item) throws SQLException {
        String sqlOs = os.getEmissao() == null
            ? "INSERT INTO ORDEM_SERVICO(TIPO, COD_CLIENTE, DURACAO, STATUS, OBS) VALUES (?, ?, ?, ?, ?)"
            : "INSERT INTO ORDEM_SERVICO(TIPO, COD_CLIENTE, EMISSAO, DURACAO, STATUS, OBS) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlItem = "INSERT INTO ORDEM_SERVICO_ITEM(ORDEM_SERVICO, SKU, DESC_PRODUTO, COD_BARRAS, QTDE, UNIDADE, TIPO) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection c = DatabaseConnection.getConnection()) {
            boolean autoCommitOriginal = c.getAutoCommit();
            c.setAutoCommit(false);
            try {
                try (PreparedStatement ps = c.prepareStatement(sqlOs, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, os.getTipo());
                    ps.setString(2, os.getCodCliente());
                    int i = 3;
                    if (os.getEmissao() != null) ps.setTimestamp(i++, Timestamp.valueOf(os.getEmissao()));
                    if (os.getDuracao() == null) ps.setNull(i++, Types.INTEGER); else ps.setInt(i++, os.getDuracao());
                    ps.setString(i++, os.getStatus() == null ? "PENDENTE" : os.getStatus());
                    ps.setString(i, os.getObs());
                    ps.executeUpdate();
                    try (ResultSet keys = ps.getGeneratedKeys()) {
                        if (!keys.next()) throw new SQLException("O banco não retornou o identificador da OS.");
                        os.setOrdemServico(keys.getInt(1));
                    }
                }
                try (PreparedStatement ps = c.prepareStatement(sqlItem, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setInt(1, os.getOrdemServico());
                    ps.setString(2, item.getSku());
                    ps.setString(3, item.getDescProduto());
                    ps.setString(4, item.getCodBarras());
                    ps.setBigDecimal(5, item.getQtde());
                    ps.setString(6, item.getUnidade());
                    ps.setString(7, item.getTipo());
                    ps.executeUpdate();
                    try (ResultSet keys = ps.getGeneratedKeys()) {
                        if (keys.next()) item.setId(keys.getInt(1));
                    }
                }
                c.commit();
            } catch (SQLException | RuntimeException ex) {
                try { c.rollback(); } catch (SQLException rollbackEx) { ex.addSuppressed(rollbackEx); }
                throw ex;
            } finally {
                try { c.setAutoCommit(autoCommitOriginal); } catch (SQLException ignored) { }
            }
        }
    }
}
