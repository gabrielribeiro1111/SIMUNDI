package daos;

import classes.Produto;
import database.DatabaseConnection;
import java.sql.*;
import java.util.*;

public class ProdutoDAO {
	private Produto map(ResultSet rs) throws SQLException {
		Produto p = new Produto();
		p.setSku(rs.getString("SKU"));
		p.setCodBarras(rs.getString("COD_BARRAS"));
		p.setDescProduto(rs.getString("DESC_PRODUTO"));
		p.setTipoProduto(rs.getString("TIPO_PRODUTO"));
		int n = rs.getInt("NIVEL_AREA_VERMELHA");
		p.setNivelAreaVermelha(rs.wasNull() ? null : n);
		n = rs.getInt("NIVEL_CRITICO");
		p.setNivelCritico(rs.wasNull() ? null : n);
		p.setUnidade(rs.getString("UNIDADE"));
		return p;
	}

	public void inserir(Produto p) throws SQLException {
		String s = "INSERT INTO PRODUTO(SKU,COD_BARRAS,DESC_PRODUTO,TIPO_PRODUTO,NIVEL_AREA_VERMELHA,NIVEL_CRITICO,UNIDADE) VALUES(?,?,?,?,?,?,?)";
		try (Connection c = DatabaseConnection.getConnection(); PreparedStatement ps = c.prepareStatement(s)) {
			ps.setString(1, p.getSku());
			ps.setString(2, p.getCodBarras());
			ps.setString(3, p.getDescProduto());
			ps.setString(4, p.getTipoProduto());
			if (p.getNivelAreaVermelha() == null)
				ps.setNull(5, Types.INTEGER);
			else
				ps.setInt(5, p.getNivelAreaVermelha());
			if (p.getNivelCritico() == null)
				ps.setNull(6, Types.INTEGER);
			else
				ps.setInt(6, p.getNivelCritico());
			ps.setString(7, p.getUnidade());
			ps.executeUpdate();
		}
	}

	public Produto buscarPorSku(String sku) throws SQLException {
		try (Connection c = DatabaseConnection.getConnection();
				PreparedStatement ps = c.prepareStatement("SELECT * FROM PRODUTO WHERE SKU=?")) {
			ps.setString(1, sku);
			try (ResultSet rs = ps.executeQuery()) {
				return rs.next() ? map(rs) : null;
			}
		}
	}

	public List<Produto> listarTodos() throws SQLException {
		List<Produto> l = new ArrayList<>();
		try (Connection c = DatabaseConnection.getConnection();
				PreparedStatement ps = c.prepareStatement("SELECT * FROM PRODUTO ORDER BY DESC_PRODUTO");
				ResultSet rs = ps.executeQuery()) {
			while (rs.next())
				l.add(map(rs));
		}
		return l;
	}

	public boolean atualizar(Produto p) throws SQLException {
		String s = "UPDATE PRODUTO SET COD_BARRAS=?,DESC_PRODUTO=?,TIPO_PRODUTO=?,NIVEL_AREA_VERMELHA=?,NIVEL_CRITICO=?,UNIDADE=? WHERE SKU=?";
		try (Connection c = DatabaseConnection.getConnection(); PreparedStatement ps = c.prepareStatement(s)) {
			ps.setString(1, p.getCodBarras());
			ps.setString(2, p.getDescProduto());
			ps.setString(3, p.getTipoProduto());
			if (p.getNivelAreaVermelha() == null)
				ps.setNull(4, Types.INTEGER);
			else
				ps.setInt(4, p.getNivelAreaVermelha());
			if (p.getNivelCritico() == null)
				ps.setNull(5, Types.INTEGER);
			else
				ps.setInt(5, p.getNivelCritico());
			ps.setString(6, p.getUnidade());
			ps.setString(7, p.getSku());
			return ps.executeUpdate() > 0;
		}
	}

	public boolean excluir(String sku) throws SQLException {
		try (Connection c = DatabaseConnection.getConnection();
				PreparedStatement ps = c.prepareStatement("DELETE FROM PRODUTO WHERE SKU=?")) {
			ps.setString(1, sku);
			return ps.executeUpdate() > 0;
		}
	}
}
