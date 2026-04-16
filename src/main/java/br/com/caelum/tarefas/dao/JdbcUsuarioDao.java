package br.com.caelum.tarefas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.caelum.tarefas.modelo.Usuario;

@Repository
public class JdbcUsuarioDao {
	private Connection conn;

	@Autowired
	public JdbcUsuarioDao(DataSource dataSource) {
		try {
			conn = dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean existeUsuario(Usuario usuario) {
		if (usuario == null) {
			throw new IllegalArgumentException("Usuário não deve ser nulo");
		}

		try {
			PreparedStatement stmt = this.conn.prepareStatement("select senha from usuarios where login = ?");
			stmt.setString(1, usuario.getLogin());
			ResultSet rs = stmt.executeQuery();

			boolean autenticado = false;
			if (rs.next()) {
				String hashArmazenado = rs.getString("senha");
				autenticado = BCrypt.checkpw(usuario.getSenha(), hashArmazenado);
			}

			rs.close();
			stmt.close();

			return autenticado;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
