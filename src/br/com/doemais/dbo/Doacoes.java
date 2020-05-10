package br.com.doemais.dbo;

public class Doacoes {
	
	private int id;
	private String dataHora;
	private int quantidade;
	private int doadorId;
	private int hemocentroId;
	private int confirmacao;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDataHora() {
		return dataHora;
	}
	public void setDataHora(String dataHora) {
		this.dataHora = dataHora;
	}
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	public int getDoadorId() {
		return doadorId;
	}
	public void setDoadorId(int doadorId) {
		this.doadorId = doadorId;
	}
	public int getHemocentroId() {
		return hemocentroId;
	}
	public void setHemocentroId(int hemocentroId) {
		this.hemocentroId = hemocentroId;
	}
	public int getConfirmacao() {
		return confirmacao;
	}
	public void setConfirmacao(int confirmacao) {
		this.confirmacao = confirmacao;
	}
	
	


}
