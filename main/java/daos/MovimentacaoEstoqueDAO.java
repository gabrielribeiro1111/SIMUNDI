package daos;

import classes.MovimentacaoEstoque;
import database.DatabaseConnection;
import java.sql.*;
import java.util.*;

public class MovimentacaoEstoqueDAO {
	private MovimentacaoEstoque map(ResultSet r) throws SQLException {
		MovimentacaoEstoque m = new MovimentacaoEstoque();
		m.setId(r.getInt("ID"));
		m.setIdItem(r.getInt("ID_ITEM"));
		int v = r.getInt("ID_CONTROLE_VALIDADE");
		m.setIdControleValidade(r.wasNull() ? null : v);
		m.setIdEstoqueOrigem(r.getInt("ID_ESTOQUE_ORIGEM"));
		m.setIdEstoqueDestino(r.getInt("ID_ESTOQUE_DESTINO"));
		m.setQuantidade(r.getBigDecimal("QUANTIDADE"));
		Timestamp t = r.getTimestamp("DATA_HORA");
		m.setDataHora(t == null ? null : t.toLocalDateTime());
		return m;
	}

	public void inserir(MovimentacaoEstoque m) throws SQLException {
		String s = "INSERT INTO MOVIMENTACAO_ESTOQUE(ID_ITEM,ID_CONTROLE_VALIDADE,ID_ESTOQUE_ORIGEM,ID_ESTOQUE_DESTINO,QUANTIDADE,DATA_HORA) VALUES(?,?,?,?,?,?)";
		try (Connection c = DatabaseConnection.getConnection();
				PreparedStatement p = c.prepareStatement(s, Statement.RETURN_GENERATED_KEYS)) {
			p.setInt(1, m.getIdItem());
			if (m.getIdControleValidade() == null)
				p.setNull(2, Types.INTEGER);
			else
				p.setInt(2, m.getIdControleValidade());
			p.setInt(3, m.getIdEstoqueOrigem());
			p.setInt(4, m.getIdEstoqueDestino());
			p.setBigDecimal(5, m.getQuantidade());
			if (m.getDataHora() == null)
				p.setNull(6, Types.TIMESTAMP);
			else
				p.setTimestamp(6, Timestamp.valueOf(m.getDataHora()));
			p.executeUpdate();
			try (ResultSet k = p.getGeneratedKeys()) {
				if (k.next())
					m.setId(k.getInt(1));
			}
		}
	}

	public MovimentacaoEstoque buscarPorId(int id) throws SQLException {
		try (Connection c = DatabaseConnection.getConnection();
				PreparedStatement p = c.prepareStatement("SELECT * FROM MOVIMENTACAO_ESTOQUE WHERE ID=?")) {
			p.setInt(1, id);
			try (ResultSet r = p.executeQuery()) {
				return r.next() ? map(r) : null;
			}
		}
	}

	public List<MovimentacaoEstoque> listarPorItem(int idItem) throws SQLException {
		List<MovimentacaoEstoque> l = new ArrayList<>();
		try (Connection c = DatabaseConnection.getConnection();
				PreparedStatement p = c.prepareStatement(
						"SELECT * FROM MOVIMENTACAO_ESTOQUE WHERE ID_ITEM=? ORDER BY DATA_HORA DESC")) {
			p.setInt(1, idItem);
			try (ResultSet r = p.executeQuery()) {
				while (r.next())
					l.add(map(r));
			}
		}
		return l;
	}

	public List<MovimentacaoEstoque> listarTodos() throws SQLException {
		List<MovimentacaoEstoque> l = new ArrayList<>();
		try (Connection c = DatabaseConnection.getConnection();
				PreparedStatement p = c.prepareStatement("SELECT * FROM MOVIMENTACAO_ESTOQUE ORDER BY DATA_HORA DESC");
				ResultSet r = p.executeQuery()) {
			while (r.next())
				l.add(map(r));
		}
		return l;
	}
	/**
	 * Movimentacoes sao historico: por padrao nao oferecemos UPDATE/DELETE para
	 * preservar auditoria.
	 */
}
