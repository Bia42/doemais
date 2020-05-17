package br.com.doemais.dbo;

import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;

public class GeradorDeCupom {

	public static void main(String[] args) {

	    Random r = new Random();

	    String alphabet = "123xyz";
	    for (int i = 0; i < 50; i++) {
	     //   System.out.println(alphabet.charAt(r.nextInt(alphabet.length())));
	    }
	    
	    String cupom = RandomStringUtils.randomAlphabetic(5);
	    cupom = cupom + r.nextInt(25);
	    System.out.println(cupom);
	}

}
