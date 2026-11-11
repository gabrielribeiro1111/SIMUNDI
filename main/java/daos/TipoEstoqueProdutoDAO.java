package daos;

import classes.TipoEstoqueProduto;
import database.DatabaseConnection;
import java.sql.*;
import java.util.*;

public class TipoEstoqueProdutoDAO {
	private TipoEstoqueProduto map(ResultSet r) throws SQLException {
		TipoEstoqueProduto x = new TipoEstoqueProduto();
		x.setCodTipoEstoque(r.getInt("COD_TIPO_ESTOQUE"));
		x.setTipoProduto(r.getString("TIPO_PRODUTO"));
		return x;
	}

	public void inserir(TipoEstoqueProduto x) throws SQLException {
		String s = "INSERT INTO TIPO_ESTOQUE_PRODUTO(COD_TIPO_ESTOQUE,TIPO_PRODUTO) VALUES(?,?)";
		try (Connection c = DatabaseConnection.getConnection(); PreparedStatement p = c.prepareStatement(s)) {
			p.setInt(1, x.getCodTipoEstoque());
			p.setString(2, x.getTipoProduto());
			p.executeUpdate();
		}
	}

	public TipoEstoqueProduto buscarPorChave(int cod, String tipo) throws SQLException {
		String s = "SELECT * FROM TIPO_ESTOQUE_PRODUTO WHERE COD_TIPO_ESTOQUE=? AND TIPO_PRODUTO=?";
		try (Connection c = DatabaseConnection.getConnection(); PreparedStatement p = c.prepareStatement(s)) {
			p.setInt(1, cod);
			p.setString(2, tipo);
			try (ResultSet r = p.executeQuery()) {
				return r.next() ? map(r) : null;
			}
		}
	}

	public List<TipoEstoqueProduto> listarTodos() throws SQLException {
		List<TipoEstoqueProduto> l = new ArrayList<>();
		try (Connection c = DatabaseConnection.getConnection();
				PreparedStatement p = c
						.prepareStatement("SELECT * FROM TIPO_ESTOQUE_PRODUTO ORDER BY COD_TIPO_ESTOQUE,TIPO_PRODUTO");
				ResultSet r = p.executeQuery()) {
			while (r.next())
				l.add(map(r));
		}
		return l;
	}

	public boolean atualizar(TipoEstoqueProduto x, int codOriginal, String tipoOriginal) throws SQLException {
		String s = "UPDATE TIPO_ESTOQUE_PRODUTO SET COD_TIPO_ESTOQUE=?,TIPO_PRODUTO=? WHERE COD_TIPO_ESTOQUE=? AND TIPO_PRODUTO=?";
		try (Connection c = DatabaseConnection.getConnection(); PreparedStatement p = c.prepareStatement(s)) {
			p.setInt(1, x.getCodTipoEstoque());
			p.setString(2, x.getTipoProduto());
			p.setInt(3, codOriginal);
			p.setString(4, tipoOriginal);
			return p.executeUpdate() > 0;
		}
	}

	public boolean excluir(int cod, String tipo) throws SQLException {
		String s = "DELETE FROM TIPO_ESTOQUE_PRODUTO WHERE COD_TIPO_ESTOQUE=? AND TIPO_PRODUTO=?";
		try (Connection c = DatabaseConnection.getConnection(); PreparedStatement p = c.prepareStatement(s)) {
			p.setInt(1, cod);
			p.setString(2, tipo);
			return p.executeUpdate() > 0;
		}
	}
}
