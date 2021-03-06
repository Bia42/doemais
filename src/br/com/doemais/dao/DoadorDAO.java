package br.com.doemais.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.doemais.config.BDConfig;
import br.com.doemais.dbo.Agendados;
import br.com.doemais.dbo.Cupom;
import br.com.doemais.dbo.Doacoes;
import br.com.doemais.dbo.Doador;
import br.com.doemais.dbo.Usuario;


public class DoadorDAO {
	
	public List<Doador> listarDoadores() throws Exception {
		List<Doador> lista = new ArrayList<>();
			//bia2
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

		String sql = "select nome, cpf, data_nascimento, telefone, email, tipo_sanguineo,id, dbo.historico(id) historico from doador where cpf = ?";
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
			
//			statement = conexao.prepareStatement(sql2);
//			statement.setInt(1,rs.getInt("ID"));
//			ResultSet rs2 = statement.executeQuery();
//			while(rs2.next()) {
//				historico +=  "Data da doa��o: " + rs2.getString("data_hora") + " Quantidade(L): " + rs2.getString("quantidade") + '\n';
//			}
//			
			doador.setHistorico(rs.getString("historico"));

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
	
	public List<Agendados> listarAgendas(int doadorId) throws Exception {
		List<Agendados> lista = new ArrayList<>();

		Connection conexao = BDConfig.getConnection();

		String sql = "select "
				+ "		c.nome, d.razao_social, b.horario_doacao, a.flag_checkin, a.id agendaId, c.id doadorId"
				+ "	from "
				+ "		agendados a" + 
				"		inner join agenda_hemocentro b on a.agenda_id = b.id "
				+ "		inner join doador c on c.id = a.doador_id "
				+ "		inner join hemocentro d on d.id = b.hemocentro_id "
				+ " where a.doador_id = ? and a.flag_checkin = 0";

		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setInt(1, doadorId);

		ResultSet rs = statement.executeQuery();

		while (rs.next()) {
			Agendados agendados = new Agendados();
			agendados.setHemocentro(rs.getString("razao_social"));
			agendados.setNomeDoador(rs.getString("nome"));
			agendados.setHorarioDoacao(rs.getString("horario_doacao"));
			agendados.setDoadorId(rs.getInt("doadorId"));
			agendados.setFlag_checkin(rs.getString("flag_checkin"));
			agendados.setAgendaId(rs.getInt("agendaId"));
			lista.add(agendados);
		}

		return lista;
	}
	
	public Doador realizarLogin(String email, String senha) throws Exception {
		Doador doador = null;

		Connection conexao = BDConfig.getConnection();
		PreparedStatement stmtLog = null;
		String sql = "select " + 
				"	* " + 
				" from " + 
				"	Doador a " + 
				"	left join (select " + 
				"				count(*) doacoes, doador_id, max(data_hora) ultima_doacao " + 
				"				from doacoes" + 
				"				group by " + 
				"				doador_id) x on a.id = x.doador_id "
				+ " left join (select " + 
				"					count (distinct hemocentro_id) qtd_hemocentros_visitado," + 
				"						doador_id " + 
				"				from" + 
				"					doacoes" + 
				"				group by " + 
				"					doador_id) y on a.id = y.doador_id"
				+ " WHERE email = ? and senha = ?";

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
			doador.setUltimaDoacao(rs.getString("ultima_doacao"));
			doador.setQuantDoacoes(rs.getString("doacoes"));
			doador.setQtdHemocentrosVisitados(rs.getString("qtd_hemocentros_visitado"));

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
		if(doador.getCupom() != null) {
			this.vinculoCupom(doador.getCupom(), idGerado);
			this.listarCupomAutoGerado(doador.getCupom());
			//TODO Notifica��o usu�rio
		}
		
		return idGerado;
	}
	
	public int addAgenda(Agendados agenda) throws Exception {
		int idGerado = 0;
		Connection conexao = BDConfig.getConnection();
		
		String sql = "exec add_agenda ?,?";
		PreparedStatement statement = conexao.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
		statement.setInt(1, agenda.getAgendaId());
		statement.setInt(2, agenda.getDoadorId());	

		statement.execute();
		
		return 1;
	}
	
	public boolean updateDoacao(int doacaoId, int confirmacao) throws Exception {
		Connection conexao = BDConfig.getConnection();

		String sql = "update doacoes set confirmacao = ? where id = ?";

		PreparedStatement statement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		statement.setInt(1, confirmacao);
		statement.setInt(2, doacaoId);

		int updateCount = statement.executeUpdate();
		
		PreparedStatement stmtLog = null;

		stmtLog = conexao.prepareStatement("exec log_add ?,null,'CONFIRMACAO','doacoes','APP_TESTECONSC'");
		stmtLog.setInt(1, doacaoId);
		stmtLog.execute();
		
		if(updateCount == 1) {
			return true;
		}
		return false;
	}
	
	public boolean checkinDoador(int agendaId) throws Exception {
		Connection conexao = BDConfig.getConnection();

		String sql = " update " + 
				"	agendados" + 
				" set" + 
				"	flag_checkin = '1' " + 	
				" where id = ?";

		PreparedStatement statement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		statement.setInt(1, agendaId);

		int updateCount = statement.executeUpdate();
		
		PreparedStatement stmtLog = null;

		stmtLog = conexao.prepareStatement("exec log_add ?,null,'CHECKIN','agendados','APP_CHECKIN'");
		stmtLog.setInt(1, agendaId);
		stmtLog.execute();
		
		if(updateCount == 1) {
			return true;
		}
		return false;
	}
	public boolean vinculoCupom(String cupom, int usuarioId) throws Exception {
		Connection conexao = BDConfig.getConnection();

		String sql = "update cupom set usuario_id = ? where cupom = ?";

		PreparedStatement statement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		statement.setInt(1, usuarioId);
		statement.setString(2, cupom);

		int updateCount = statement.executeUpdate();
		
		if(updateCount == 1) {
			return true;
		}
		return false;
	}
	
	public Cupom listarCupomAutoGerado(String cupom2) throws Exception {
		Cupom cupom = null;
		boolean result= false;
		Connection conexao = BDConfig.getConnection();

		String sql = "select "
				+ "	top 1 a.id idCupom, cupom, patrocinador_id, b.razao_social patrocinador, descricao "
				+ " from "
				+ "	Cupom a "
				+ " inner join patrocinador b on a.patrocinador_id = b.id where usuario_id is null";

		String sql2 = "select doador_id_ind from cupom where cupom = ?";

		PreparedStatement statement = conexao.prepareStatement(sql);
		ResultSet rs = statement.executeQuery();
		
		PreparedStatement statement2 = conexao.prepareStatement(sql2);
		statement2.setString(1, cupom2);
		ResultSet rs2 = statement2.executeQuery();
		
		while (rs.next()) {
			if(rs2.next())
				result = this.vinculoCupom(rs.getString("cupom"), rs2.getInt("doador_id_ind"));
			if(result) {
				cupom = new Cupom();
				cupom.setCupomId(rs.getInt("idCupom"));
				cupom.setPatrocinadorId(rs.getInt("patrocinador_id"));
				cupom.setPatrocinador(rs.getString("patrocinador"));
				cupom.setCupom(rs.getString("cupom"));
				cupom.setDescricao(rs.getString("descricao"));
			}

		}
		return cupom;
	}
	public void editarDoador(Doador doador) throws Exception {
		Connection conexao = BDConfig.getConnection();

		String sql = "UPDATE doador SET nome = ?, email = ?, endereco = ?, tipo_Sanguineo = ? WHERE ID = ?";

		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setString(1, doador.getNome());
		statement.setString(2, doador.getEmail());
		statement.setString(3, doador.getEndereco());
		statement.setString(4, doador.getTipoSanguineo());
		statement.setInt(5, doador.getId());

		statement.execute();
	}
	
	
	
}
