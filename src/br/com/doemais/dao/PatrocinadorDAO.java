package br.com.doemais.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import br.com.doemais.config.BDConfig;
import br.com.doemais.dbo.Cupom;
import br.com.doemais.dbo.Patrocinador;


public class PatrocinadorDAO {
	
	public List<Patrocinador> listarPatrocinadores() throws Exception {
		List<Patrocinador> lista = new ArrayList<>();

		Connection conexao = BDConfig.getConnection();

		String sql = "select * from Patrocinador where status = 1";

		PreparedStatement statement = conexao.prepareStatement(sql);
		ResultSet rs = statement.executeQuery();

		while (rs.next()) {
			Patrocinador pat = new Patrocinador();
			pat.setId(rs.getInt("ID"));
			pat.setRazaoSocial(rs.getString("razao_social"));
			pat.setNivel(rs.getString("nivel"));
			pat.setCnpj(rs.getString("cnpj"));
			pat.setEndereco(rs.getString("endereco"));
			pat.setCidade(rs.getString("cidade"));
			pat.setEstado(rs.getString("estado"));
			pat.setNumero(rs.getString("numero"));
			pat.setCep(rs.getString("cep"));
			pat.setComplemento(rs.getString("complemento"));
			pat.setLogo(new String(rs.getBytes("logo"), "UTF-8"));
			lista.add(pat);
		}

		return lista;
	}
	public List<Patrocinador> listarPatrocinadoresPendentes() throws Exception {
		List<Patrocinador> lista = new ArrayList<>();

		Connection conexao = BDConfig.getConnection();

		String sql = "select * from Patrocinador where status is null";

		PreparedStatement statement = conexao.prepareStatement(sql);
		ResultSet rs = statement.executeQuery();

		while (rs.next()) {
			Patrocinador pat = new Patrocinador();
			pat.setId(rs.getInt("ID"));
			pat.setRazaoSocial(rs.getString("razao_social"));
			pat.setNivel(rs.getString("nivel"));
			pat.setCnpj(rs.getString("cnpj"));
			pat.setEndereco(rs.getString("endereco"));
			pat.setCidade(rs.getString("cidade"));
			pat.setEstado(rs.getString("estado"));
			pat.setNumero(rs.getString("numero"));
			pat.setCep(rs.getString("cep"));
			pat.setComplemento(rs.getString("complemento"));
			pat.setLogo(new String(rs.getBytes("logo"), "UTF-8"));
			lista.add(pat);
		}

		return lista;
	}
	
	public List<Cupom> listarCuponsDisponiveis() throws Exception {
		List<Cupom> lista = new ArrayList<>();

		Connection conexao = BDConfig.getConnection();

		String sql = "select a.id idCupom, cupom, patrocinador_id, b.razao_social patrocinador, descricao from Cupom a inner join patrocinador b on a.patrocinador_id = b.id where usuario_id is null";

		PreparedStatement statement = conexao.prepareStatement(sql);
		ResultSet rs = statement.executeQuery();

		while (rs.next()) {
			Cupom cupom = new Cupom();
			cupom.setCupomId(rs.getInt("idCupom"));
			cupom.setPatrocinadorId(rs.getInt("patrocinador_id"));
			cupom.setPatrocinador(rs.getString("patrocinador"));
			cupom.setCupom(rs.getString("cupom"));
			cupom.setDescricao(rs.getString("descricao"));

			lista.add(cupom);
		}
		return lista;
	}
	public Cupom listarCupomAutoGerado(int doadorId) throws Exception {
		Cupom cupom = null;
		boolean result= false;
		Connection conexao = BDConfig.getConnection();

		String sql = "select top 1 a.id idCupom, cupom, patrocinador_id, b.razao_social patrocinador, descricao from Cupom a inner join patrocinador b on a.patrocinador_id = b.id where usuario_id is null";

		PreparedStatement statement = conexao.prepareStatement(sql);
		ResultSet rs = statement.executeQuery();

		while (rs.next()) {
			//result = this.vinculoCupom(rs.getInt("idCupom"), doadorId);
			if(result) {
				cupom = new Cupom();
				cupom.setCupomId(rs.getInt("idCupom"));
				cupom.setPatrocinadorId(rs.getInt("patrocinador_id"));
				cupom.setPatrocinador(rs.getString("patrocinador"));
				cupom.setCupom(rs.getString("cupom"));
				cupom.setDescricao(rs.getString("descricao"));
			}
			String sql2= "update cupom set doador_id_ind = ? where id = ?";

			PreparedStatement statement2 = conexao.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS);
			statement2.setInt(1, doadorId);
			statement2.setInt(2, cupom.getCupomId());

			int updateCount = statement2.executeUpdate();

		}
		return cupom;
	}
	
	public List<Cupom> listarCuponsDispoPat(int patrocinadorId) throws Exception {
		List<Cupom> lista = new ArrayList<>();

		Connection conexao = BDConfig.getConnection();

		String sql = "select a.id idCupom, cupom, patrocinador_id, b.razao_social patrocinador , descricao from Cupom a inner join patrocinador b on a.patrocinador_id = b.id where usuario_id is null and b.id = ?";

		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setInt(1, patrocinadorId);

		ResultSet rs = statement.executeQuery();

		while (rs.next()) {
			Cupom cupom = new Cupom();
			cupom.setCupomId(rs.getInt("idCupom"));
			cupom.setPatrocinadorId(rs.getInt("patrocinador_id"));
			cupom.setPatrocinador(rs.getString("patrocinador"));
			cupom.setCupom(rs.getString("cupom"));
			cupom.setDescricao(rs.getString("descricao"));
			lista.add(cupom);
		}
		return lista;
	}
	
	public List<Cupom> listarCuponsResgaPat(int patrocinadorId) throws Exception {
		List<Cupom> lista = new ArrayList<>();

		Connection conexao = BDConfig.getConnection();

		String sql = "select a.id idCupom, cupom, patrocinador_id, b.razao_social patrocinador , descricao from Cupom a inner join patrocinador b on a.patrocinador_id = b.id where usuario_id is not null and b.id = ?";

		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setInt(1, patrocinadorId);

		ResultSet rs = statement.executeQuery();

		while (rs.next()) {
			Cupom cupom = new Cupom();
			cupom.setCupomId(rs.getInt("idCupom"));
			cupom.setPatrocinadorId(rs.getInt("patrocinador_id"));
			cupom.setPatrocinador(rs.getString("patrocinador"));
			cupom.setCupom(rs.getString("cupom"));
			cupom.setDescricao(rs.getString("descricao"));
			lista.add(cupom);
		}
		return lista;
	}
	
	public List<Cupom> listarCuponsDoador(int doadorId) throws Exception {
		List<Cupom> lista = new ArrayList<>();

		Connection conexao = BDConfig.getConnection();

		String sql = "select a.id idCupom, cupom, patrocinador_id, b.razao_social patrocinador, usuario_id, descricao from Cupom a inner join patrocinador b on a.patrocinador_id = b.id where usuario_id = ?";

		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setInt(1, doadorId);

		ResultSet rs = statement.executeQuery();

		while (rs.next()) {
			Cupom cupom = new Cupom();
			cupom.setCupomId(rs.getInt("idCupom"));
			cupom.setPatrocinadorId(rs.getInt("patrocinador_id"));
			cupom.setDoadorId(rs.getInt("usuario_id"));
			cupom.setPatrocinador(rs.getString("patrocinador"));
			cupom.setCupom(rs.getString("cupom"));
			cupom.setDescricao(rs.getString("descricao"));
			
			lista.add(cupom);
		}
		return lista;
	}
	
	public Patrocinador realizarLogin(String email, String senha) throws Exception {
		Patrocinador pat = null;

		Connection conexao = BDConfig.getConnection();

		String sql = "SELECT * FROM Patrocinador WHERE email = ? and senha = ? and status = 1";

		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setString(1, email);
		statement.setString(2, senha);

		ResultSet rs = statement.executeQuery();

		if (rs.next()) {
			pat = new Patrocinador();
			pat.setId(rs.getInt("ID"));
			pat.setRazaoSocial(rs.getString("razao_social"));
			pat.setNivel(rs.getString("nivel"));
			pat.setCnpj(rs.getString("cnpj"));
			pat.setEndereco(rs.getString("endereco"));
			pat.setCidade(rs.getString("cidade"));
			pat.setEstado(rs.getString("estado"));
			pat.setNumero(rs.getString("numero"));
			pat.setCep(rs.getString("cep"));
			pat.setComplemento(rs.getString("complemento"));
		//	pat.setLogo(rs.getBytes("logo"));
		}

		return pat;
	}
	
	public boolean verificarCnpjExistente(String cnpj) throws Exception {
		Connection conexao = BDConfig.getConnection();

		String sql = "SELECT * FROM Patrocinador WHERE cnpj = ?";

		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setString(1, cnpj);

		ResultSet rs = statement.executeQuery();

		if (rs.next()) {
			return true;
		}
		return false;
	}
	
	public boolean verificarUserExistente(String email) throws Exception {
		Connection conexao = BDConfig.getConnection();

		String sql = "SELECT * FROM Patrocinador WHERE email = ? and status is null or status = 0";

		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setString(1, email);

		ResultSet rs = statement.executeQuery();

		if (rs.next()) {
			return true;
		}
		return false;
	}

	public int addPatrocinador(Patrocinador userPat) throws Exception {
		int idGerado = 0;
		Connection conexao = BDConfig.getConnection();

		String sql = "INSERT INTO Patrocinador(razao_social,"
				+ " email,"
				+ " senha,"
				+ " cnpj,"
				+ " nivel, logo,endereco, cidade,estado, numero,"
				+ " cep, complemento) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		byte[] decodedString = new String(userPat.getLogo().substring(userPat.getLogo().indexOf(",") + 1)).getBytes("UTF-8");
		String bia = new String(decodedString, "UTF-8");
		
		PreparedStatement statement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, userPat.getRazaoSocial());
		statement.setString(2, userPat.getEmail());
		statement.setString(3, userPat.getSenha());
		statement.setString(4, userPat.getCnpj());
		statement.setString(5, userPat.getNivel());
		statement.setBytes(6, new String(userPat.getLogo().substring(userPat.getLogo().indexOf(",") + 1)).getBytes("UTF-8"));
		statement.setString(7, userPat.getEndereco());
		statement.setString(8, userPat.getCidade());
		statement.setString(9, userPat.getEstado());
		statement.setString(10, userPat.getNumero());
		statement.setString(11, userPat.getCep());
		statement.setString(12, userPat.getComplemento());
		statement.execute();
		
		ResultSet rs = statement.getGeneratedKeys();
		if (rs.next()) {
			idGerado = rs.getInt(1);
		}
		
		return idGerado;
	}
	
	public List<String> geraCupons(int patrocinadorId, int quantidade, String descricao) throws Exception {

		List<String> cuponsGerados = new ArrayList<>();
		
		Connection conexao = BDConfig.getConnection();
		
		Cupom cupom = new Cupom();
		
		String cupomGerado = "";
		
		for(int i = 0; i < quantidade; i ++) {
			
			cupomGerado = cupom.geradorDeCupom(patrocinadorId);
			String sql = "insert into cupom(cupom, patrocinador_id, descricao) values(?,?,?)";
			
			PreparedStatement statement = conexao.prepareStatement(sql);
			statement.setString(1, cupomGerado);
			statement.setInt(2, patrocinadorId);
			statement.setString(3, descricao);

			statement.execute();
			cuponsGerados.add(cupomGerado);
		}

		
		return cuponsGerados;
	}
	
	public boolean geraCuponsCampanhas(int campanhaId, int quantidade, int patrocinadorId, String descricao) throws Exception {

		boolean retorno = false;
		Connection conexao = BDConfig.getConnection();
		
		Cupom cupom = new Cupom();
		
		String cupomGerado = "";
		
		for(int i = 0; i < quantidade; i ++) {
			
			cupomGerado = cupom.geradorDeCupom(patrocinadorId);
			String sql = "insert into cupom(cupom, patrocinador_id, descricao, campanha_id) values(?,?,?,?)";
			
			PreparedStatement statement = conexao.prepareStatement(sql);
			statement.setString(1, cupomGerado);
			statement.setInt(2, patrocinadorId);
			statement.setString(3, descricao);
			statement.setInt(4, campanhaId);

			statement.execute();
			
		}
		retorno = true;
		
		return retorno;
	}
	
	public boolean cadastrarCampanha(String descricao, int patrocinadorId, int quantidade) throws Exception {

		boolean retorno = false;
		Connection conexao = BDConfig.getConnection();
		
		String sql = "insert into campanhas(descricao, patrocinador_id) values(?,?)";
			
		PreparedStatement statement = conexao.prepareStatement(sql,  Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, descricao);
		statement.setInt(2, patrocinadorId);
		statement.execute();
			
		ResultSet rs = statement.getGeneratedKeys();
		if (rs.next()) {
			int campanhaId = rs.getInt(1);
			this.geraCuponsCampanhas(campanhaId, quantidade, patrocinadorId, descricao);
			retorno = true;
		}
		
		return retorno;
	}
	public boolean vinculoCupom(int cupomId, int usuarioId) throws Exception {
		Connection conexao = BDConfig.getConnection();

		String sql = "update cupom set usuario_id = ? where id = ?";

		PreparedStatement statement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		statement.setInt(1, usuarioId);
		statement.setInt(2, cupomId);

		int updateCount = statement.executeUpdate();
		
		if(updateCount == 1) {
			return true;
		}
		return false;
	}
	public boolean confirmaca(int hemocentroId) throws Exception {
		Connection conexao = BDConfig.getConnection();

		String sql = " update " + 
				"	patrocinador" + 
				" set" + 
				"	status = '1' " + 	
				" where id = ?";

		PreparedStatement statement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		statement.setInt(1, hemocentroId);

		int updateCount = statement.executeUpdate();
		PreparedStatement stmtLog = null;

		stmtLog = conexao.prepareStatement("exec log_add null,?,'CONF_HEMOCENTRO','agendados','CONFIRMACAO_CHECK_IN'");
		stmtLog.setInt(1, hemocentroId);
		stmtLog.execute();
		
		if(updateCount == 1) {
			return true;
		}
		return false;
	}
	
	public boolean excluir(int hemocentroId) throws Exception {
		Connection conexao = BDConfig.getConnection();

		String sql = " update " + 
				"	patrocinador" + 
				" set" + 
				"	status = '0' " + 	
				" where id = ?";

		PreparedStatement statement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		statement.setInt(1, hemocentroId);

		int updateCount = statement.executeUpdate();
		PreparedStatement stmtLog = null;

		stmtLog = conexao.prepareStatement("exec log_add null,?,'CONF_HEMOCENTRO','agendados','CONFIRMACAO_CHECK_IN'");
		stmtLog.setInt(1, hemocentroId);
		stmtLog.execute();
		
		if(updateCount == 1) {
			return true;
		}
		return false;
	}
	
	
}
