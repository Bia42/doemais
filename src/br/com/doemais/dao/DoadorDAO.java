package br.com.doemais.dao;

import java.nio.charset.StandardCharsets;
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
	
	
	public Doador listarDoador(String cpf) throws Exception {
		Doador doador = null;

		Connection conexao = BDConfig.getConnection();
		String historico = "";

		String sql = "select nome, cpf, data_nascimento, telefone, email, tipo_sanguineo,id from doador where cpf = ?";
		String sql2 = "select data_hora, quantidade from doacoes where doador_id = ? and confirmacao = 1";

		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setString(1, cpf);
		
		ResultSet rs = statement.executeQuery();		
		while (rs.next()) {
			doador = new Doador();
			doador.setId(rs.getInt("ID"));
			doador.setEmail(rs.getString("EMAIL"));
			doador.setNome(rs.getString("nome"));
			doador.setTipoSanguineo(rs.getString("tipo_Sanguineo"));
			doador.setDataNascimento(rs.getString("data_nascimento"));
			doador.setCpf(rs.getString("cpf"));
			doador.setTelefone(rs.getString("telefone"));
			
			statement = conexao.prepareStatement(sql2);
			statement.setInt(1,rs.getInt("ID"));
			ResultSet rs2 = statement.executeQuery();
			while(rs2.next()) {
				historico.concat("Data da doa��o: " + rs2.getString("data_hora") + " Quantidade(L): " + rs2.getString("quantidade") + '\n');
			}

			
			doador.setHistorico(historico);

		}

		return doador;
	}
	
	public Doador listarDoadorPorCod(String codUser) throws Exception {
		Doador doador = null;

		Connection conexao = BDConfig.getConnection();

		String sql = "select nome, cpf, data_nascimento, telefone, email, tipo_sanguineo,id from doador where codUser = ?";

		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setString(1, codUser);
		
		ResultSet rs = statement.executeQuery();
		
		while (rs.next()) {
			doador = new Doador();
			doador.setId(rs.getInt("ID"));
			doador.setEmail(rs.getString("EMAIL"));
			doador.setNome(rs.getString("nome"));
			doador.setTipoSanguineo(rs.getString("tipo_Sanguineo"));
			doador.setDataNascimento(rs.getString("data_nascimento"));
			doador.setCpf(rs.getString("cpf"));
			doador.setTelefone(rs.getString("telefone"));
		}

		return doador;
	}
	public boolean verificarCpfExistente(String cpf) throws Exception {
		Connection conexao = BDConfig.getConnection();

		String sql = "SELECT * FROM doador WHERE cpf = ?";

		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setString(1, cpf);

		ResultSet rs = statement.executeQuery();

		if (rs.next()) {
			return true;
		}
		return false;
	}
	
	public boolean verificarUserExistente(String email) throws Exception {
		Connection conexao = BDConfig.getConnection();

		String sql = "SELECT * FROM doador WHERE email = ?";

		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setString(1, email);

		ResultSet rs = statement.executeQuery();

		if (rs.next()) {
			return true;
		}
		return false;
	}
	
	public List<Doacoes> listarDoaocoes(int doadorId) throws Exception {
		List<Doacoes> lista = new ArrayList<>();

		Connection conexao = BDConfig.getConnection();

		String sql = "select "
				+ "	a.ID idDoacao, a.doador_id, a.hemocentro_id, a.data_hora, a.quantidade, a.confirmacao, b.razao_social"
				+ "	from "
				+ "	doacoes a" + 
				"	inner join Hemocentro b on a.hemocentro_id = b.id "
				+ " where doador_id = ? and confirmacao = '0'";

		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setInt(1, doadorId);

		ResultSet rs = statement.executeQuery();

		while (rs.next()) {
			Doacoes doacoes = new Doacoes();
			doacoes.setId(rs.getInt("idDoacao"));
			doacoes.setDoadorId(rs.getInt("doador_id"));
			doacoes.setHemocentroId(rs.getInt("hemocentro_id"));
			doacoes.setDataHora(rs.getString("data_hora"));
			doacoes.setQuantidade(rs.getInt("quantidade"));
			doacoes.setConfirmacao(rs.getInt("confirmacao"));
			doacoes.setHemoRazaoSocial(rs.getString("razao_social"));
			lista.add(doacoes);
		}

		return lista;
	}
	
	public Doador realizarLogin(String email, String senha) throws Exception {
		Doador doador = null;

		Connection conexao = BDConfig.getConnection();
		PreparedStatement stmtLog = null;
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
			doador.setCodUser(rs.getString("codUser"));

			stmtLog = conexao.prepareStatement("exec log_add ?,null,'LOGIN','doador','APP_LOGIN'");
			stmtLog.setInt(1, rs.getInt("ID"));
			stmtLog.execute();
		}

		return doador;
	}

	public int addDoador(Doador doador) throws Exception {
		int idGerado = 0;
		Connection conexao = BDConfig.getConnection();

		String sql = "INSERT INTO Doador(nome, email, senha, data_nascimento, cpf, telefone, tipo_sanguineo,endereco, cidade,estado, numero,"
				+ " cep, complemento, longitude, latitude, codUser) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,? , ? ,?, ?)";
		PreparedStatement statement = conexao.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
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
		statement.setString(16, doador.geradorCodUser());		

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
