package br.com.doemais.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.doemais.config.BDConfig;
import br.com.doemais.dbo.Hemocentro;
import br.com.doemais.dbo.UsuariosHemocentro;


public class HemocentroDAO {
	
	public List<UsuariosHemocentro> listarUsuarios() throws Exception {
		List<UsuariosHemocentro> lista = new ArrayList<>();

		Connection conexao = BDConfig.getConnection();

		String sql = "select * from UsuariosHemocentro";

		PreparedStatement statement = conexao.prepareStatement(sql);
		ResultSet rs = statement.executeQuery();

		while (rs.next()) {
			UsuariosHemocentro userHemo = new UsuariosHemocentro();
			userHemo.setId(rs.getInt("ID"));
			userHemo.setEmail(rs.getString("EMAIL"));
			userHemo.setNome(rs.getString("nome"));
			userHemo.setDataNascimento(rs.getString("data_nascimento"));
			userHemo.setCpf(rs.getString("cpf"));
			userHemo.setTelefone(rs.getString("telefone"));
			userHemo.setEndereco(rs.getString("endereco"));
			userHemo.setCidade(rs.getString("cidade"));
			userHemo.setEstado(rs.getString("estado"));
			userHemo.setNumero(rs.getString("numero"));
			userHemo.setCep(rs.getString("cep"));
			userHemo.setFuncao(rs.getString("funcao"));
			userHemo.setComplemento(rs.getString("complemento"));
			userHemo.setLongitude(rs.getString("longitude"));
			userHemo.setLatitude(rs.getString("latitude"));
			userHemo.setId(rs.getInt("hemocentro_id"));

			lista.add(userHemo);
		}

		return lista;
	}
	
	public List<Hemocentro> listarHemocentros() throws Exception {
		List<Hemocentro> lista = new ArrayList<>();

		Connection conexao = BDConfig.getConnection();

		String sql = "select * from Hemocentro";

		PreparedStatement statement = conexao.prepareStatement(sql);
		ResultSet rs = statement.executeQuery();

		while (rs.next()) {
			Hemocentro hemo = new Hemocentro();
			hemo.setId(rs.getInt("ID"));
			hemo.setRazaoSocial(rs.getString("razao_social"));
			hemo.setCnpj(rs.getString("cnpj"));
			hemo.setEndereco(rs.getString("endereco"));
			hemo.setCidade(rs.getString("cidade"));
			hemo.setEstado(rs.getString("estado"));
			hemo.setNumero(rs.getString("numero"));
			hemo.setCep(rs.getString("cep"));
			hemo.setComplemento(rs.getString("complemento"));
			hemo.setLongitude(rs.getString("longitude"));
			hemo.setLatitude(rs.getString("latitude"));
			lista.add(hemo);
		}

		return lista;
	}
	
	public UsuariosHemocentro realizarLogin(String email, String senha) throws Exception {
		UsuariosHemocentro userHemo = null;

		Connection conexao = BDConfig.getConnection();

		String sql = "SELECT * FROM UsuariosHemocentro WHERE email = ? and senha = ?";

		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setString(1, email);
		statement.setString(2, senha);

		ResultSet rs = statement.executeQuery();

		if (rs.next()) {
			userHemo = new UsuariosHemocentro();
			userHemo.setId(rs.getInt("ID"));
			userHemo.setEmail(rs.getString("EMAIL"));
			userHemo.setNome(rs.getString("nome"));
			userHemo.setHemocentroId(rs.getInt("hemocentro_id"));
			userHemo.setDataNascimento(rs.getString("data_nascimento"));
			userHemo.setCpf(rs.getString("cpf"));
			userHemo.setTelefone(rs.getString("telefone"));
			userHemo.setEndereco(rs.getString("endereco"));
			userHemo.setCidade(rs.getString("cidade"));
			userHemo.setEstado(rs.getString("estado"));
			userHemo.setNumero(rs.getString("numero"));
			userHemo.setCep(rs.getString("cep"));
			userHemo.setComplemento(rs.getString("complemento"));
			userHemo.setLongitude(rs.getString("longitude"));
			userHemo.setLatitude(rs.getString("latitude"));

		}

		return userHemo;
	}

	public int addUsuarioHemocentro(UsuariosHemocentro userHemo) throws Exception {
		int idGerado = 0;
		Connection conexao = BDConfig.getConnection();

		String sql = "INSERT INTO UsuariosHemocentro(nome, email, senha, data_nascimento, cpf, telefone, hemocentro_id,endereco, cidade,estado, numero,"
				+ " cep, complemento, longitude, latitude,sexo) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,? , ? ,?, ?)";

		PreparedStatement statement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, userHemo.getNome());
		statement.setString(2, userHemo.getEmail());
		statement.setString(3, userHemo.getSenha());
		statement.setString(4, userHemo.getDataNascimento());
		statement.setString(5, userHemo.getCpf());
		statement.setString(6, userHemo.getTelefone());
		statement.setInt(7, userHemo.getHemocentroId());
		statement.setString(8, userHemo.getEndereco());
		statement.setString(9, userHemo.getCidade());
		statement.setString(10, userHemo.getEstado());
		statement.setString(11, userHemo.getNumero());
		statement.setString(12, userHemo.getCep());
		statement.setString(13, userHemo.getComplemento());
		statement.setString(14, userHemo.getLongitude());
		statement.setString(15, userHemo.getLatitude());		
		statement.setString(16, userHemo.getSexo());		

		statement.execute();
		
		ResultSet rs = statement.getGeneratedKeys();
		if (rs.next()) {
			idGerado = rs.getInt(1);
		}
		
		return idGerado;
	}
	
}
