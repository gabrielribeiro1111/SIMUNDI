package daos;

import classes.Cliente;
import database.DatabaseConnection;
import java.sql.*;
import java.util.*;

public class ClienteDAO {
	private Cliente map(ResultSet rs) throws SQLException {
		Cliente c = new Cliente();
		c.setCodCliente(rs.getString("COD_CLIENTE"));
		c.setNome(rs.getString("NOME"));
		c.setTipo(rs.getString("TIPO"));
		c.setDocumento(rs.getString("DOCUMENTO"));
		c.setContato(rs.getString("CONTATO"));
		c.setInativo(rs.getBoolean("INATIVO"));
		c.setBloqueado(rs.getBoolean("BLOQUEADO"));
		return c;
	}

	public void inserir(Cliente c) throws SQLException {

	    String sql = "INSERT INTO CLIENTE "
	            + "(NOME,TIPO,DOCUMENTO,CONTATO,INATIVO,BLOQUEADO) "
	            + "VALUES (?,?,?,?,?,?)";

	    try (Connection con = DatabaseConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(
	                 sql, Statement.RETURN_GENERATED_KEYS)) {

	        ps.setString(1, c.getNome());
	        ps.setString(2, c.getTipo());
	        ps.setString(3, c.getDocumento());
	        ps.setString(4, c.getContato());
	        ps.setBoolean(5, c.isInativo());
	        ps.setBoolean(6, c.isBloqueado());

	        ps.executeUpdate();

	        try (ResultSet rs = ps.getGeneratedKeys()) {
	            if (rs.next()) {
	                c.setCodCliente(rs.getString(1));
	            } else {
	                throw new SQLException("Não foi possível recuperar o código gerado.");
	            }
	        }
	    }
	}

	public Cliente buscarPorCodigo(String codigo) throws SQLException {
		String sql = "SELECT * FROM CLIENTE WHERE COD_CLIENTE=?";
		try (Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, codigo);
			try (ResultSet rs = ps.executeQuery()) {
				return rs.next() ? map(rs) : null;
			}
		}
	}

	public List<Cliente> listarTodos() throws SQLException {
		List<Cliente> lista = new ArrayList<>();
		String sql = "SELECT * FROM CLIENTE ORDER BY NOME";
		try (Connection con = DatabaseConnection.getConnection();
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {
			while (rs.next())
				lista.add(map(rs));
		}
		return lista;
	}

	public boolean atualizar(Cliente c) throws SQLException {
		String sql = "UPDATE CLIENTE SET NOME=?,TIPO=?,DOCUMENTO=?,CONTATO=?,INATIVO=?,BLOQUEADO=? WHERE COD_CLIENTE=?";
		try (Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, c.getNome());
			ps.setString(2, c.getTipo());
			ps.setString(3, c.getDocumento());
			ps.setString(4, c.getContato());
			ps.setBoolean(5, c.isInativo());
			ps.setBoolean(6, c.isBloqueado());
			ps.setString(7, c.getCodCliente());
			return ps.executeUpdate() > 0;
		}
	}

	public boolean excluir(String codigo) throws SQLException {
		try (Connection con = DatabaseConnection.getConnection();
				PreparedStatement ps = con.prepareStatement("DELETE FROM CLIENTE WHERE COD_CLIENTE=?")) {
			ps.setString(1, codigo);
			return ps.executeUpdate() > 0;
		}
	}
}
