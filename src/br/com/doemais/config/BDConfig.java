package br.com.doemais.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BDConfig {

	public static Connection getConnection() {

		String usuario = "doemaissa";
		String senha = "D03m@I$sa";
		String url = "jdbc:sqlserver://doemais.database.windows.net:1433;databaseName=DoeMais" + ";user="
				+ usuario + ";password=" + senha + ";";

		Connection conexao = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conexao = DriverManager.getConnection(url);
		} catch (ClassNotFoundException e) {
			// Erro caso o driver JDBC não foi instalado
			e.printStackTrace();
		} catch (SQLException e) {
			// Erro caso haja problemas para se conectar ao banco de dados
			e.printStackTrace();
		}
		return conexao;

	}

}
