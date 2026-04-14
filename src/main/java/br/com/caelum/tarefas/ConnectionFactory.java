package br.com.caelum.tarefas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

	public Connection getConnection() throws SQLException {
		System.out.println("conectando ...");

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new SQLException(e);
		}

		String dbHost = System.getenv("DB_HOST");
		if (dbHost == null || dbHost.isEmpty()) {
			dbHost = "localhost";
		}

		return DriverManager.getConnection("jdbc:mysql://" + dbHost + "/fj21?useUnicode=true&characterEncoding=UTF-8&useSSL=false", "root", "12345");
	}

}
