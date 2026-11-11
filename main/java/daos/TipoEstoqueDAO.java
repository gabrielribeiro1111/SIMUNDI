package daos;

import classes.TipoEstoque;
import database.DatabaseConnection;
import java.sql.*;
import java.util.*;

public class TipoEstoqueDAO {
	private TipoEstoque map(ResultSet r) throws SQLException {
		TipoEstoque t = new TipoEstoque();
		t.setCodTipoEstoque(r.getInt("COD_TIPO_ESTOQUE"));
		t.setDescTipoEstoque(r.getString("DESC_TIPO_ESTOQUE"));
		t.setUnidadeEstoque(r.getString("UNIDADE_ESTOQUE"));
		return t;
	}

	public void inserir(TipoEstoque t) throws SQLException {
		String s = "INSERT INTO TIPO_ESTOQUE(DESC_TIPO_ESTOQUE,UNIDADE_ESTOQUE) VALUES(?,?)";
		try (Connection c = DatabaseConnection.getConnection();
				PreparedStatement p = c.prepareStatement(s, Statement.RETURN_GENERATED_KEYS)) {
			p.setString(1, t.getDescTipoEstoque());
			p.setString(2, t.getUnidadeEstoque());
			p.executeUpdate();
			try (ResultSet k = p.getGeneratedKeys()) {
				if (k.next())
					t.setCodTipoEstoque(k.getInt(1));
			}
		}
	}

	public TipoEstoque buscarPorId(int id) throws SQLException {
		try (Connection c = DatabaseConnection.getConnection();
				PreparedStatement p = c.prepareStatement("SELECT * FROM TIPO_ESTOQUE WHERE COD_TIPO_ESTOQUE=?")) {
			p.setInt(1, id);
			try (ResultSet r = p.executeQuery()) {
				return r.next() ? map(r) : null;
			}
		}
	}

	public List<TipoEstoque> listarTodos() throws SQLException {
		List<TipoEstoque> l = new ArrayList<>();
		try (Connection c = DatabaseConnection.getConnection();
				PreparedStatement p = c.prepareStatement("SELECT * FROM TIPO_ESTOQUE ORDER BY DESC_TIPO_ESTOQUE");
				ResultSet r = p.executeQuery()) {
			while (r.next())
				l.add(map(r));
		}
		return l;
	}

	public boolean atualizar(TipoEstoque t) throws SQLException {
		String s = "UPDATE TIPO_ESTOQUE SET DESC_TIPO_ESTOQUE=?,UNIDADE_ESTOQUE=? WHERE COD_TIPO_ESTOQUE=?";
		try (Connection c = DatabaseConnection.getConnection(); PreparedStatement p = c.prepareStatement(s)) {
			p.setString(1, t.getDescTipoEstoque());
			p.setString(2, t.getUnidadeEstoque());
			p.setInt(3, t.getCodTipoEstoque());
			return p.executeUpdate() > 0;
		}
	}

	public boolean excluir(int id) throws SQLException {
		try (Connection c = DatabaseConnection.getConnection();
				PreparedStatement p = c.prepareStatement("DELETE FROM TIPO_ESTOQUE WHERE COD_TIPO_ESTOQUE=?")) {
			p.setInt(1, id);
			return p.executeUpdate() > 0;
		}
	}
}
