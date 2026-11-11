package daos;

import classes.OrdemServicoItem;
import database.DatabaseConnection;
import java.sql.*;
import java.util.*;

public class OrdemServicoItemDAO {
	private OrdemServicoItem map(ResultSet r) throws SQLException {
		OrdemServicoItem i = new OrdemServicoItem();
		i.setId(r.getInt("ID"));
		i.setOrdemServico(r.getInt("ORDEM_SERVICO"));
		i.setSku(r.getString("SKU"));
		i.setDescProduto(r.getString("DESC_PRODUTO"));
		i.setCodBarras(r.getString("COD_BARRAS"));
		i.setQtde(r.getBigDecimal("QTDE"));
		i.setUnidade(r.getString("UNIDADE"));
		i.setTipo(r.getString("TIPO"));
		return i;
	}

	public void inserir(OrdemServicoItem i) throws SQLException {
		String s = "INSERT INTO ORDEM_SERVICO_ITEM(ORDEM_SERVICO,SKU,DESC_PRODUTO,COD_BARRAS,QTDE,UNIDADE,TIPO) VALUES(?,?,?,?,?,?,?)";
		try (Connection c = DatabaseConnection.getConnection();
				PreparedStatement p = c.prepareStatement(s, Statement.RETURN_GENERATED_KEYS)) {
			p.setInt(1, i.getOrdemServico());
			p.setString(2, i.getSku());
			p.setString(3, i.getDescProduto());
			p.setString(4, i.getCodBarras());
			p.setBigDecimal(5, i.getQtde());
			p.setString(6, i.getUnidade());
			p.setString(7, i.getTipo());
			p.executeUpdate();
			try (ResultSet k = p.getGeneratedKeys()) {
				if (k.next())
					i.setId(k.getInt(1));
			}
		}
	}

	public OrdemServicoItem buscarPorId(int id) throws SQLException {
		try (Connection c = DatabaseConnection.getConnection();
				PreparedStatement p = c.prepareStatement("SELECT * FROM ORDEM_SERVICO_ITEM WHERE ID=?")) {
			p.setInt(1, id);
			try (ResultSet r = p.executeQuery()) {
				return r.next() ? map(r) : null;
			}
		}
	}

	public List<OrdemServicoItem> listarPorOrdemServico(int ordem) throws SQLException {
		List<OrdemServicoItem> l = new ArrayList<>();
		try (Connection c = DatabaseConnection.getConnection();
				PreparedStatement p = c
						.prepareStatement("SELECT * FROM ORDEM_SERVICO_ITEM WHERE ORDEM_SERVICO=? ORDER BY ID")) {
			p.setInt(1, ordem);
			try (ResultSet r = p.executeQuery()) {
				while (r.next())
					l.add(map(r));
			}
		}
		return l;
	}

	public List<OrdemServicoItem> listarTodos() throws SQLException {
		List<OrdemServicoItem> l = new ArrayList<>();
		try (Connection c = DatabaseConnection.getConnection();
				PreparedStatement p = c.prepareStatement("SELECT * FROM ORDEM_SERVICO_ITEM ORDER BY ID");
				ResultSet r = p.executeQuery()) {
			while (r.next())
				l.add(map(r));
		}
		return l;
	}

	public boolean atualizar(OrdemServicoItem i) throws SQLException {
		String s = "UPDATE ORDEM_SERVICO_ITEM SET ORDEM_SERVICO=?,SKU=?,DESC_PRODUTO=?,COD_BARRAS=?,QTDE=?,UNIDADE=?,TIPO=? WHERE ID=?";
		try (Connection c = DatabaseConnection.getConnection(); PreparedStatement p = c.prepareStatement(s)) {
			p.setInt(1, i.getOrdemServico());
			p.setString(2, i.getSku());
			p.setString(3, i.getDescProduto());
			p.setString(4, i.getCodBarras());
			p.setBigDecimal(5, i.getQtde());
			p.setString(6, i.getUnidade());
			p.setString(7, i.getTipo());
			p.setInt(8, i.getId());
			return p.executeUpdate() > 0;
		}
	}

	public boolean excluir(int id) throws SQLException {
		try (Connection c = DatabaseConnection.getConnection();
				PreparedStatement p = c.prepareStatement("DELETE FROM ORDEM_SERVICO_ITEM WHERE ID=?")) {
			p.setInt(1, id);
			return p.executeUpdate() > 0;
		}
	}
}
