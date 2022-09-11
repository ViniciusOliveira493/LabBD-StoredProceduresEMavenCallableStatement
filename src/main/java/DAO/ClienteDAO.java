package DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Cliente;

public class ClienteDAO implements InterfaceDAO<Cliente>{
	
	@Override
	public String create(Cliente obj) throws SQLException {
		return cliente('I', obj);
	}

	@Override
	public String update(Cliente obj) throws SQLException {
		return cliente('U', obj);
	}

	@Override
	public String delete(Cliente obj) throws SQLException {
		return cliente('D', obj);
	}

	@Override
	public Cliente read(Cliente obj) throws Exception {
		String query = 	"SELECT cpf,nome,email,limiteCredito,dtNascimento "
						+ "FROM tbCliente WHERE cpf=?";
		Conexao conn = new Conexao();
		Connection cn = null;
		try {
			cn = conn.getConexao();
			PreparedStatement pstm = cn.prepareStatement(query);
			pstm.setString(1, obj.getCpf());
			ResultSet rs = pstm.executeQuery();			
			while(rs.next()) {			
				obj = new Cliente();
				obj.setCpf(rs.getString("cpf"));
				obj.setNome(rs.getString("nome"));
				obj.setEmail(rs.getString("email"));
				obj.setLimiteDoCartao(rs.getDouble("limiteCredito"));
				obj.setDataNascimento(LocalDate.parse(rs.getDate("dtNascimento").toString()));
			}
			pstm.close(); 
			rs.close();
		} catch (Exception e) {			
			throw e;
		}finally {
			conn.close(cn);
		}
		return obj;
	}

	@Override
	public List<Cliente> list() throws Exception {
		List<Cliente> lista = new ArrayList<Cliente>();
		String query = 	"SELECT cpf,nome,email,limiteCredito,dtNascimento "
				+ "FROM tbCliente";
		Conexao conn = new Conexao();
		Connection cn = null;
		try {
			cn = conn.getConexao();
			PreparedStatement pstm = cn.prepareStatement(query);
			ResultSet rs = pstm.executeQuery();			
			while(rs.next()) {			
				Cliente obj = new Cliente();
				obj.setCpf(rs.getString("cpf"));
				obj.setNome(rs.getString("nome"));
				obj.setEmail(rs.getString("email"));
				obj.setLimiteDoCartao(rs.getDouble("limiteCredito"));
				obj.setDataNascimento(LocalDate.parse(rs.getDate("dtNascimento").toString()));
				lista.add(obj);
			}
			pstm.close(); 
			rs.close();
		} catch (Exception e) {			
			throw e;
		}finally {
			conn.close(cn);
		}
		return lista;
	}
	
	private String cliente(char op,Cliente cliente) throws SQLException {
		Conexao conn = new Conexao();
		Connection cn = conn.getConexao();
		String retorno = "";
		String query = "{CALL sp_cliente (?,?,?,?,?,?,?)}";
		try {
			CallableStatement cs = cn.prepareCall(query);
			cs.setString(1, op+"");
			cs.setString(2, cliente.getCpf());
			cs.setString(3, cliente.getNome());
			cs.setString(4, cliente.getEmail());
			cs.setDouble(5, cliente.getLimiteDoCartao());
			if( op=='D' || op=='d') {
				cs.setDate(6, java.sql.Date.valueOf("2020-02-02"));
			}else {
				cs.setDate(6, java.sql.Date.valueOf(cliente.getDataNascimento()));
			}
			
			cs.registerOutParameter(7, Types.VARCHAR);
			cs.execute();
			retorno = cs.getString(7);
			cs.close();
		} catch (SQLException e) {
			throw e;
		} finally {
			conn.close(cn);
		}		
		return retorno;
	}

}
