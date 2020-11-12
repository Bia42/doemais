package br.com.doemais.dbo;

import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;

public class Cupom {
	
	private int cupomId;
	private int patrocinadorId;
	private int doadorId;
	private String cupom;
	private String patrocinador;
	private int quantidade;
	private String descricao;
	
	
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public int getCupomId() {
		return cupomId;
	}
	public void setCupomId(int cupomId) {
		this.cupomId = cupomId;
	}
	public int getPatrocinadorId() {
		return patrocinadorId;
	}
	public void setPatrocinadorId(int patrocinadorId) {
		this.patrocinadorId = patrocinadorId;
	}

	public int getDoadorId() {
		return doadorId;
	}
	public void setDoadorId(int doadorId) {
		this.doadorId = doadorId;
	}
	public String getCupom() {
		return cupom;
	}
	public void setCupom(String cupom) {
		this.cupom = cupom;
	}
	public String getPatrocinador() {
		return patrocinador;
	}
	public void setPatrocinador(String patrocinador) {
		this.patrocinador = patrocinador;
	}
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	public String geradorDeCupom(int patrocinadorId) {
		
	    Random r = new Random();

	    String cupom = RandomStringUtils.randomAlphabetic(7);
	    cupom = cupom + r.nextInt(25) + patrocinadorId;
	    
	    return cupom;
	}
	public String geradorDeCupomDoador(int doadorId) {
		
	    Random r = new Random();

	    String cupom = RandomStringUtils.randomAlphabetic(6);
	    cupom = cupom + r.nextInt(25) + "IND" + doadorId;
	    
	    return cupom;
	}
	
	
	
	

}
