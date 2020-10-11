package br.com.doemais.dbo;

public class Agendados {
	//bia
	private int agendaId;
	private int doadorId;
	private String horarioDoacao;
	private String nomeDoador;
	private String CpfDoador;
	private String hemocentro;
	private String flag_checkin;

	public int getAgendaId() {
		return agendaId;
	}
	public void setAgendaId(int agendaId) {
		this.agendaId = agendaId;
	}
	public int getDoadorId() {
		return doadorId;
	}
	public void setDoadorId(int doadorId) {
		this.doadorId = doadorId;
	}
	public String getHorarioDoacao() {
		return horarioDoacao;
	}
	public void setHorarioDoacao(String horarioDoacao) {
		this.horarioDoacao = horarioDoacao;
	}
	public String getNomeDoador() {
		return nomeDoador;
	}
	public void setNomeDoador(String nomeDoador) {
		this.nomeDoador = nomeDoador;
	}
	public String getCpfDoador() {
		return CpfDoador;
	}
	public void setCpfDoador(String cpfDoador) {
		CpfDoador = cpfDoador;
	}
	public String getHemocentro() {
		return hemocentro;
	}
	public void setHemocentro(String hemocentro) {
		this.hemocentro = hemocentro;
	}
	public String getFlag_checkin() {
		return flag_checkin;
	}
	public void setFlag_checkin(String flag_checkin) {
		this.flag_checkin = flag_checkin;
	}
	
	
	

}
