package daos;

import classes.ControleValidade;
import database.DatabaseConnection;
import java.sql.*;
import java.util.*;

public class ControleValidadeDAO {
	private ControleValidade map(ResultSet r) throws SQLException {
		ControleValidade v = new ControleValidade();
		v.setId(r.getInt("ID"));
		v.setIdItem(r.getInt("ID_ITEM"));
		v.setLote(r.getString("LOTE"));
		java.sql.Date d = r.getDate("VALIDADE");
		v.setValidade(d == null ? null : d.toLocalDate());
		v.setQuantidade(r.getBigDecimal("QUANTIDADE"));
		return v;
	}

	public void inserir(ControleValidade v) throws SQLException {
		String s = "INSERT INTO CONTROLE_VALIDADE(ID_ITEM,LOTE,VALIDADE,QUANTIDADE) VALUES(?,?,?,?)";
		try (Connection c = DatabaseConnection.getConnection();
				PreparedStatement p = c.prepareStatement(s, Statement.RETURN_GENERATED_KEYS)) {
			p.setInt(1, v.getIdItem());
			p.setString(2, v.getLote());
			p.setDate(3, java.sql.Date.valueOf(v.getValidade()));
			p.setBigDecimal(4, v.getQuantidade());
			p.executeUpdate();
			try (ResultSet k = p.getGeneratedKeys()) {
				if (k.next())
					v.setId(k.getInt(1));
			}
		}
	}

	public ControleValidade buscarPorId(int id) throws SQLException {
		try (Connection c = DatabaseConnection.getConnection();
				PreparedStatement p = c.prepareStatement("SELECT * FROM CONTROLE_VALIDADE WHERE ID=?")) {
			p.setInt(1, id);
			try (ResultSet r = p.executeQuery()) {
				return r.next() ? map(r) : null;
			}
		}
	}

	public List<ControleValidade> listarPorItem(int idItem) throws SQLException {
		List<ControleValidade> l = new ArrayList<>();
		try (Connection c = DatabaseConnection.getConnection();
				PreparedStatement p = c
						.prepareStatement("SELECT * FROM CONTROLE_VALIDADE WHERE ID_ITEM=? ORDER BY VALIDADE")) {
			p.setInt(1, idItem);
			try (ResultSet r = p.executeQuery()) {
				while (r.next())
					l.add(map(r));
			}
		}
		return l;
	}

	public List<ControleValidade> listarTodos() throws SQLException {
		List<ControleValidade> l = new ArrayList<>();
		try (Connection c = DatabaseConnection.getConnection();
				PreparedStatement p = c.prepareStatement("SELECT * FROM CONTROLE_VALIDADE ORDER BY VALIDADE");
				ResultSet r = p.executeQuery()) {
			while (r.next())
				l.add(map(r));
		}
		return l;
	}

	public boolean atualizar(ControleValidade v) throws SQLException {
		String s = "UPDATE CONTROLE_VALIDADE SET ID_ITEM=?,LOTE=?,VALIDADE=?,QUANTIDADE=? WHERE ID=?";
		try (Connection c = DatabaseConnection.getConnection(); PreparedStatement p = c.prepareStatement(s)) {
			p.setInt(1, v.getIdItem());
			p.setString(2, v.getLote());
			p.setDate(3, java.sql.Date.valueOf(v.getValidade()));
			p.setBigDecimal(4, v.getQuantidade());
			p.setInt(5, v.getId());
			return p.executeUpdate() > 0;
		}
	}

	public boolean excluir(int id) throws SQLException {
		try (Connection c = DatabaseConnection.getConnection();
				PreparedStatement p = c.prepareStatement("DELETE FROM CONTROLE_VALIDADE WHERE ID=?")) {
			p.setInt(1, id);
			return p.executeUpdate() > 0;
		}
	}
}
