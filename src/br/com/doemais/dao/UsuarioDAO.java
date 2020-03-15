package br.com.doemais.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.doemais.config.BDConfig;
import br.com.doemais.dbo.Usuario;


public class UsuarioDAO {
	
	public List<Usuario> listarUsuarios() throws Exception {
		List<Usuario> lista = new ArrayList<>();

		Connection conexao = BDConfig.getConnection();

		String sql = "select * from usuarios";

		PreparedStatement statement = conexao.prepareStatement(sql);
		ResultSet rs = statement.executeQuery();

		while (rs.next()) {
			Usuario Usuario = new Usuario();
			Usuario.setId(rs.getInt("id"));
			Usuario.setNome(rs.getString("nome"));
			Usuario.setEmail(rs.getString("email"));
			Usuario.setTipoSanguineo(rs.getString("tipoSanguineo"));
			lista.add(Usuario);
		}
// teste
		//teste2
		return lista;
	}
	
	public Usuario buscarUsuarioPorId(int idUsuario) throws Exception {
		Usuario Usuario = null;

		Connection conexao = BDConfig.getConnection();

		String sql = "SELECT * FROM usuarios WHERE ID = ?";

		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setInt(1, idUsuario);
		ResultSet rs = statement.executeQuery();

		if (rs.next()) {
			Usuario = new Usuario();
			Usuario.setId(rs.getInt("ID"));
			Usuario.setEmail(rs.getString("EMAIL"));
			Usuario.setNome(rs.getString("nome"));
			Usuario.setTipoSanguineo(rs.getString("tipoSanguineo"));
			
		}

		return Usuario;
	}

	public int addUsuario(Usuario Usuario) throws Exception {
		int idGerado = 0;
		Connection conexao = BDConfig.getConnection();

		String sql = "INSERT INTO UsuarioS(nome, email, password, tipoSanguineo) VALUES(?, ?, ?, ?)";

		PreparedStatement statement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, Usuario.getNome());
		statement.setString(2, Usuario.getEmail());
		statement.setString(3, Usuario.getPassword());
		statement.setString(4, Usuario.getTipoSanguineo());

		statement.execute();
		
		ResultSet rs = statement.getGeneratedKeys();
		if (rs.next()) {
			idGerado = rs.getInt(1);
		}
		
		return idGerado;
	}
	
	public void editarUsuario(Usuario Usuario, int idUsuario) throws Exception {
		Connection conexao = BDConfig.getConnection();

		String sql = "UPDATE UsuarioS SET TITULO = ?, DESCRICAO = ? WHERE ID = ?";

		PreparedStatement statement = conexao.prepareStatement(sql);
		//statement.setString(1, Usuario.getTitulo());
		//statement.setString(2, Usuario.getDescricao());
		statement.setInt(3, idUsuario);
		statement.execute();
	}
	
	public void removerUsuario(int idUsuario) throws Exception {
		Connection conexao = BDConfig.getConnection();

		String sql = "DELETE FROM UsuarioS WHERE ID = ?";

		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setInt(1, idUsuario);
		statement.execute();
	}
	
}
