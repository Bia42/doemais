package br.com.doemais.dbo;

public class Campanhas {
	private String descricao;
	private String tipoSangue;
	private int hemocentroId;
	private int patrocinadorId;
	private String conteudo;
	private int quantCupons;
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getTipoSangue() {
		return tipoSangue;
	}
	public void setTipoSangue(String tipoSangue) {
		this.tipoSangue = tipoSangue;
	}
	public int getHemocentroId() {
		return hemocentroId;
	}
	public void setHemocentroId(int hemocentroId) {
		this.hemocentroId = hemocentroId;
	}
	public int getPatrocinadorId() {
		return patrocinadorId;
	}
	public void setPatrocinadorId(int patrocinadorId) {
		this.patrocinadorId = patrocinadorId;
	}
	public String getConteudo() {
		return conteudo;
	}
	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}
	public int getQuantCupons() {
		return quantCupons;
	}
	public void setQuantCupons(int quantCupons) {
		this.quantCupons = quantCupons;
	}
	
	
}
