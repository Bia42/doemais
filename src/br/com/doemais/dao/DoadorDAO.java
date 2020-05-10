package br.com.doemais.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.doemais.config.BDConfig;
import br.com.doemais.dbo.Doacoes;
import br.com.doemais.dbo.Doador;


public class DoadorDAO {
	
	public List<Doador> listarDoadores() throws Exception {
		List<Doador> lista = new ArrayList<>();

		Connection conexao = BDConfig.getConnection();

		String sql = "select * from doador";

		PreparedStatement statement = conexao.prepareStatement(sql);
		ResultSet rs = statement.executeQuery();

		while (rs.next()) {
			Doador doador = new Doador();
			doador.setId(rs.getInt("ID"));
			doador.setEmail(rs.getString("EMAIL"));
			doador.setNome(rs.getString("nome"));
			doador.setTipoSanguineo(rs.getString("tipo_Sanguineo"));
			doador.setDataNascimento(rs.getString("data_nascimento"));
			doador.setCpf(rs.getString("cpf"));
			doador.setTelefone(rs.getString("telefone"));
			doador.setEndereco(rs.getString("endereco"));
			doador.setCidade(rs.getString("cidade"));
			doador.setEstado(rs.getString("estado"));
			doador.setNumero(rs.getString("numero"));
			doador.setCep(rs.getString("cep"));
			doador.setComplemento(rs.getString("complemento"));
			doador.setLongitude(rs.getString("longitude"));
			doador.setLatitude(rs.getString("latitude"));

			lista.add(doador);
		}

		return lista;
	}
	public List<Doacoes> listarDoaocoes(int doadorId) throws Exception {
		List<Doacoes> lista = new ArrayList<>();

		Connection conexao = BDConfig.getConnection();

		String sql = "select * from doacoes where doador_id = ? and confirmacao = '0'";

		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setInt(1, doadorId);

		ResultSet rs = statement.executeQuery();

		while (rs.next()) {
			Doacoes doacoes = new Doacoes();
			doacoes.setId(rs.getInt("ID"));
			doacoes.setDoadorId(rs.getInt("doador_id"));
			doacoes.setHemocentroId(rs.getInt("hemocentro_id"));
			doacoes.setDataHora(rs.getString("data_hora"));
			doacoes.setQuantidade(rs.getInt("quantidade"));
			doacoes.setConfirmacao(rs.getInt("confirmacao"));

			lista.add(doacoes);
		}

		return lista;
	}
	
	public Doador realizarLogin(String email, String senha) throws Exception {
		Doador doador = null;

		Connection conexao = BDConfig.getConnection();

		String sql = "SELECT * FROM doador WHERE email = ? and senha = ?";

		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setString(1, email);
		statement.setString(2, senha);

		ResultSet rs = statement.executeQuery();

		if (rs.next()) {
			doador = new Doador();
			doador.setId(rs.getInt("ID"));
			doador.setEmail(rs.getString("EMAIL"));
			doador.setNome(rs.getString("nome"));
			doador.setTipoSanguineo(rs.getString("tipo_Sanguineo"));
			doador.setDataNascimento(rs.getString("data_nascimento"));
			doador.setCpf(rs.getString("cpf"));
			doador.setTelefone(rs.getString("telefone"));
			doador.setEndereco(rs.getString("endereco"));
			doador.setCidade(rs.getString("cidade"));
			doador.setEstado(rs.getString("estado"));
			doador.setNumero(rs.getString("numero"));
			doador.setCep(rs.getString("cep"));
			doador.setComplemento(rs.getString("complemento"));
			doador.setLongitude(rs.getString("longitude"));
			doador.setLatitude(rs.getString("latitude"));

		}

		return doador;
	}

	public int addDoador(Doador doador) throws Exception {
		int idGerado = 0;
		Connection conexao = BDConfig.getConnection();

		String sql = "INSERT INTO Doador(nome, email, senha, data_nascimento, cpf, telefone, tipo_sanguineo,endereco, cidade,estado, numero,"
				+ " cep, complemento, longitude, latitude) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,? , ? ,?)";
		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setString(1, doador.getNome());
		statement.setString(2, doador.getEmail());
		statement.setString(3, doador.getSenha());
		statement.setString(4, doador.getDataNascimento());
		statement.setString(5, doador.getCpf());
		statement.setString(6, doador.getTelefone());
		statement.setString(7, doador.getTipoSanguineo());
		statement.setString(8, doador.getEndereco());
		statement.setString(9, doador.getCidade());
		statement.setString(10, doador.getEstado());
		statement.setString(11, doador.getNumero());
		statement.setString(12, doador.getCep());
		statement.setString(13, doador.getComplemento());
		statement.setString(14, doador.getLongitude());
		statement.setString(15, doador.getLatitude());		

		statement.execute();
		
		ResultSet rs = statement.getGeneratedKeys();
		if (rs.next()) {
			idGerado = rs.getInt(1);
		}
		
		return idGerado;
	}
	
	public boolean updateDoacao(int doacaoId, int confirmacao) throws Exception {
		Connection conexao = BDConfig.getConnection();

		String sql = "update doacoes set confirmacao = ? where id = ?";

		PreparedStatement statement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		statement.setInt(1, confirmacao);
		statement.setInt(2, doacaoId);

		int updateCount = statement.executeUpdate();
		
		if(updateCount == 1) {
			return true;
		}
		return false;
	}
	
}
