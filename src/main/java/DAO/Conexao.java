package DAO;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
	private String server = "localhost";
	private String login = "root";
	private String senhaMDB = "123456";
	private String senha = "12345678";
	private String bd = "bdClientes";
	private final Boolean MARIADB = false;
	
	public Connection getConexao() {
		if(MARIADB) {
			return getConexaoMDB();
		}else {
			return getConexaoSqlServer();
		}
	}
	
	public Connection getConexaoSqlServer() {
		String path = "jdbc:sqlserver://"+server+";"
        + "databaseName="+bd+";"
        + "user="+login+";"
        + "password="+senha+";"+
        "encrypt=true;trustServerCertificate=true";
       
		Connection conn = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager.getConnection(path);
			return conn;
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	public Connection getConexaoMDB() {
		String path="jdbc:mariadb://"+server+"/"+bd;		
		Connection conn = null;
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(path,login,senhaMDB);
			return conn;
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	public void close(Connection cn) {
		try {
			cn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
