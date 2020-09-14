package br.com.doemais.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.doemais.config.BDConfig;
import br.com.doemais.dbo.AgendaHemocentro;
import br.com.doemais.dbo.AtendimentoHemocentro;
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
			hemo.setHemocentroId(rs.getInt("ID"));
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

	public List<Hemocentro> listarHemocentrosPorNivel() throws Exception {
		List<Hemocentro> lista = new ArrayList<>();

		Connection conexao = BDConfig.getConnection();

		String sql = "select * from Hemocentro";
		String sql2 = "select * from NiveisSangue where hemocentro_id = ?";


		PreparedStatement statement = conexao.prepareStatement(sql);
		ResultSet rs = statement.executeQuery();
		



		while (rs.next()) {
			Hemocentro hemo = new Hemocentro();
			hemo.setHemocentroId(rs.getInt("ID"));
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
			
			PreparedStatement statement2 = conexao.prepareStatement(sql2);
			statement2.setInt(1, rs.getInt("ID"));
			ResultSet rs2 = statement2.executeQuery();
			
			Map<String,Double> niveis = new HashMap<String,Double>();

			while(rs2.next()) {
				niveis.put(rs2.getString("tipoSangue"), rs2.getDouble("nivel"));
			}
			hemo.setNiveisSangue(niveis);
			
			lista.add(hemo);
		}

		return lista;
	}
	
	public Hemocentro listarHemocentro(int hemocentroId) throws Exception {
		Hemocentro hemo = null;
		Connection conexao = BDConfig.getConnection();

		String sql = "select * from Hemocentro where id = ?";
	

		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setInt(1, hemocentroId);

		ResultSet rs = statement.executeQuery();

		while (rs.next()) {
			hemo = new Hemocentro();
			hemo.setHemocentroId(rs.getInt("ID"));
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
		}

		return hemo;
	}

	public List<AgendaHemocentro> getAgendaHemocentro(int hemocentroId) throws Exception {
		List<AgendaHemocentro>  horarios = new ArrayList<>();
		AgendaHemocentro horario = null;

		Connection conexao = BDConfig.getConnection();

		String sql = "select * from agenda_hemocentro  where hemocentro_id = ? and horario_cheio = 0";
	

		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setInt(1, hemocentroId);

		ResultSet rs = statement.executeQuery();

		while (rs.next()) {
			horario = new AgendaHemocentro();
			horario.setHemocentroId(rs.getInt("hemocentro_id"));
			horario.setAgendaId(rs.getInt("id"));
			horario.setHorario_doacao(rs.getString("horario_doacao"));
			horario.setQdtOcupados(rs.getInt("qtd_ocupados"));
			horario.setQdtVagas(rs.getInt("qtd_vagas"));
			horarios.add(horario);	
		}

		return horarios;
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
	
	public boolean verificarCpfExistente(String cpf) throws Exception {
		Connection conexao = BDConfig.getConnection();

		String sql = "SELECT * FROM UsuariosHemocentro WHERE cpf = ?";

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

		String sql = "SELECT * FROM UsuariosHemocentro WHERE email = ?";

		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setString(1, email);

		ResultSet rs = statement.executeQuery();

		if (rs.next()) {
			return true;
		}
		return false;
	}

	public int addUsuarioHemocentro(UsuariosHemocentro userHemo) throws Exception {
		int idGerado = 0;
		Connection conexao = BDConfig.getConnection();

		String sql = "INSERT INTO UsuariosHemocentro(nome, email, senha, data_nascimento, cpf, telefone, hemocentro_id,endereco, cidade,estado, numero,"
				+ " cep, complemento, longitude, latitude,sexo,funcao) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,? , ? ,?, ?, ?)";

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
		statement.setString(17, userHemo.getFuncao());		

		statement.execute();
		
		ResultSet rs = statement.getGeneratedKeys();
		if (rs.next()) {
			idGerado = rs.getInt(1);
		}
		
		return idGerado;
	}
	public int addAgenda(AtendimentoHemocentro atendimento) throws Exception {
		int idGerado = 0;
		Connection conexao = BDConfig.getConnection();
		String procedure = "EXEC p_agendamento";
		String sql = "insert into atendimento_hemocentro("
				+ "hemocentro_id,"
				+ "dia_inicio,"
				+ "dia_final,"
				+ "hora_final,"
				+ "hora_inicio,"
				+ "minuto_final,"
				+ "minuto_inicio,"
				+ "periodo_final,"
				+ "periodo_inicio,"
				+ "quantidade,"
				+ "tempo,"
				+ "flag_agenda)"
				+ " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		PreparedStatement statement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		statement.setInt(1, atendimento.getHemocentroId());
		statement.setInt(2, atendimento.getDia());
		statement.setInt(3, atendimento.getDiaFinal());
		statement.setInt(4, Integer.parseInt(atendimento.getHoraInicio().substring(0,atendimento.getHoraInicio().indexOf(':'))));
		statement.setInt(5, Integer.parseInt(atendimento.getHoraFinal().substring(0,atendimento.getHoraFinal().indexOf(':'))));
		statement.setInt(6,  Integer.parseInt(atendimento.getHoraInicio().substring(atendimento.getHoraInicio().indexOf(':')+1)));
		statement.setInt(7,  Integer.parseInt(atendimento.getHoraFinal().substring(atendimento.getHoraFinal().indexOf(':')+1)));
		statement.setString(8,  atendimento.getPeriodoFinal());
		statement.setString(9,  atendimento.getPeriodoInicio());
		statement.setInt(10,  atendimento.getQuantidade());
		statement.setInt(11,  atendimento.getTempo());
		statement.setString(12, "N");
		statement.execute();
		
		ResultSet rs = statement.getGeneratedKeys();
		if (rs.next()) {
			idGerado = rs.getInt(1);
			PreparedStatement statement2 = conexao.prepareStatement(procedure, Statement.RETURN_GENERATED_KEYS);

			statement2.executeUpdate();
		}
		
		return idGerado;
	}
	
	public boolean updateHemocentro(int hemocentroId, String horarios) throws Exception {
		Connection conexao = BDConfig.getConnection();

		String sql = "update hemocentro set horarios = ? where id = ?";

		PreparedStatement statement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, horarios);
		statement.setInt(2, hemocentroId);

		int updateCount = statement.executeUpdate();
		
		if(updateCount == 1) {
			return true;
		}
		return false;
	}
	
	
}
