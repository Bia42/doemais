package br.com.doemais.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BDConfig {

	public static Connection getConnection() {

		String usuario = "sa";
		String senha = "Nicom2113$";
		String url = "jdbc:sqlserver://doemais.cqpqpjj8wkwg.us-east-1.rds.amazonaws.com:1433;databaseName=doemais" + ";user="
				+ usuario + ";password=" + senha + ";";

		Connection conexao = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conexao = DriverManager.getConnection(url);
		} catch (ClassNotFoundException e) {
			// Erro caso o driver JDBC não foi instalado
			//LUCAS CHEGOU
			e.printStackTrace();
		} catch (SQLException e) {
			// Erro caso haja problemas para se conectar ao banco de dados
			e.printStackTrace();
		}
		return conexao;

	}

}
