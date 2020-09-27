package br.com.doemais.dbo;

public class Agendados {
	
	private int agendaId;
	private int doadorId;
	private String horarioDoacao;
	private String nomeDoador;
	private String CpfDoador;

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
	
	

}
