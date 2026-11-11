package daos;

import classes.EstoqueReal;
import database.DatabaseConnection;
import java.sql.*;
// import java.math.BigDecimal;
import java.util.*;

public class EstoqueRealDAO {
	private Integer nullableInt(ResultSet r, String col) throws SQLException {
		int v = r.getInt(col);
		return r.wasNull() ? null : v;
	}

	private EstoqueReal map(ResultSet r) throws SQLException {
		EstoqueReal e = new EstoqueReal();
		e.setId(r.getInt("ID"));
		e.setIdentificacao(r.getString("IDENTIFICACAO"));
		e.setPosXInicial(nullableInt(r, "POS_X_INICIAL"));
		e.setPosYInicial(nullableInt(r, "POS_Y_INICIAL"));
		e.setPosZInicial(nullableInt(r, "POS_Z_INICIAL"));
		e.setPosXFinal(nullableInt(r, "POS_X_FINAL"));
		e.setPosYFinal(nullableInt(r, "POS_Y_FINAL"));
		e.setPosZFinal(nullableInt(r, "POS_Z_FINAL"));
		e.setCodTipoEstoque(r.getInt("COD_TIPO_ESTOQUE"));
		e.setArmazenamentoMax(r.getBigDecimal("ARMAZENAMENTO_MAX"));
		e.setVirtual(r.getBoolean("VIRTUAL"));
		e.setAtiva(r.getBoolean("ATIVA"));
		return e;
	}

	private void setNullableInt(PreparedStatement p, int i, Integer v) throws SQLException {
		if (v == null)
			p.setNull(i, Types.INTEGER);
		else
			p.setInt(i, v);
	}

	private void bind(PreparedStatement p, EstoqueReal e) throws SQLException {
		p.setString(1, e.getIdentificacao());
		setNullableInt(p, 2, e.getPosXInicial());
		setNullableInt(p, 3, e.getPosYInicial());
		setNullableInt(p, 4, e.getPosZInicial());
		setNullableInt(p, 5, e.getPosXFinal());
		setNullableInt(p, 6, e.getPosYFinal());
		setNullableInt(p, 7, e.getPosZFinal());
		p.setInt(8, e.getCodTipoEstoque());
		p.setBigDecimal(9, e.getArmazenamentoMax());
		p.setBoolean(10, e.isVirtual());
		p.setBoolean(11, e.isAtiva());
	}

	private static final String COLS = "IDENTIFICACAO,POS_X_INICIAL,POS_Y_INICIAL,POS_Z_INICIAL,POS_X_FINAL,POS_Y_FINAL,POS_Z_FINAL,COD_TIPO_ESTOQUE,ARMAZENAMENTO_MAX,VIRTUAL,ATIVA";

	public void inserir(EstoqueReal e) throws SQLException {
		String s = "INSERT INTO ESTOQUE_REAL(" + COLS + ") VALUES(?,?,?,?,?,?,?,?,?,?,?)";
		try (Connection c = DatabaseConnection.getConnection();
				PreparedStatement p = c.prepareStatement(s, Statement.RETURN_GENERATED_KEYS)) {
			bind(p, e);
			p.executeUpdate();
			try (ResultSet k = p.getGeneratedKeys()) {
				if (k.next())
					e.setId(k.getInt(1));
			}
		}
	}

	public EstoqueReal buscarPorId(int id) throws SQLException {
		try (Connection c = DatabaseConnection.getConnection();
				PreparedStatement p = c.prepareStatement("SELECT * FROM ESTOQUE_REAL WHERE ID=?")) {
			p.setInt(1, id);
			try (ResultSet r = p.executeQuery()) {
				return r.next() ? map(r) : null;
			}
		}
	}

	public List<EstoqueReal> listarTodos() throws SQLException {
		List<EstoqueReal> l = new ArrayList<>();
		try (Connection c = DatabaseConnection.getConnection();
				PreparedStatement p = c.prepareStatement("SELECT * FROM ESTOQUE_REAL ORDER BY IDENTIFICACAO");
				ResultSet r = p.executeQuery()) {
			while (r.next())
				l.add(map(r));
		}
		return l;
	}

	public boolean atualizar(EstoqueReal e) throws SQLException {
		String s = "UPDATE ESTOQUE_REAL SET IDENTIFICACAO=?,POS_X_INICIAL=?,POS_Y_INICIAL=?,POS_Z_INICIAL=?,POS_X_FINAL=?,POS_Y_FINAL=?,POS_Z_FINAL=?,COD_TIPO_ESTOQUE=?,ARMAZENAMENTO_MAX=?,VIRTUAL=?,ATIVA=? WHERE ID=?";
		try (Connection c = DatabaseConnection.getConnection(); PreparedStatement p = c.prepareStatement(s)) {
			bind(p, e);
			p.setInt(12, e.getId());
			return p.executeUpdate() > 0;
		}
	}

	public boolean excluir(int id) throws SQLException {
		try (Connection c = DatabaseConnection.getConnection();
				PreparedStatement p = c.prepareStatement("DELETE FROM ESTOQUE_REAL WHERE ID=?")) {
			p.setInt(1, id);
			return p.executeUpdate() > 0;
		}
	}
}
