package br.com.doemais.dbo;

import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;

public class GeradorDeCupom {

	public static void main(String[] args) {

		String bia = "biaIND12";
		String id = bia.substring(bia.indexOf("IND")+3);
		System.out.println(id);
	}

}
