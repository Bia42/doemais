package br.com.doemais.dbo;

public class AgendaHemocentro {
	private Integer hemocentroId;
	private int agendaId;
	private String horarioDoacao;
	private int qdtVagas;
	private int qdtOcupados;
	private int horarioCheio;
	public Integer getHemocentroId() {
		return hemocentroId;
	}
	public void setHemocentroId(Integer hemocentroId) {
		this.hemocentroId = hemocentroId;
	}
	public int getAgendaId() {
		return agendaId;
	}
	public void setAgendaId(int agendaId) {
		this.agendaId = agendaId;
	}
	public String getHorario_doacao() {
		return horarioDoacao;
	}
	public void setHorario_doacao(String horario_doacao) {
		this.horarioDoacao = horario_doacao;
	}
	public int getQdtVagas() {
		return qdtVagas;
	}
	public void setQdtVagas(int qdtVagas) {
		this.qdtVagas = qdtVagas;
	}
	public int getQdtOcupados() {
		return qdtOcupados;
	}
	public void setQdtOcupados(int qdtOcupados) {
		this.qdtOcupados = qdtOcupados;
	}
	public int getHorarioCheio() {
		return horarioCheio;
	}
	public void setHorarioCheio(int horarioCheio) {
		this.horarioCheio = horarioCheio;
	}
	

}
