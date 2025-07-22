package com.renato.projects.appointment.utils;

public class TitleCapitalization {

	public static String titleCapitalization(String nome) {
	    if (nome == null || nome.isBlank()) {
	        return nome;
	    }

	    String[] palavras = nome.trim().toLowerCase().split("\\s+");
	    StringBuilder nomeCapitalizado = new StringBuilder();

	    for (String palavra : palavras) {
	        if (!palavra.isEmpty()) {
	            nomeCapitalizado.append(Character.toUpperCase(palavra.charAt(0)))
	                            .append(palavra.substring(1))
	                            .append(" ");
	        }
	    }

	    return nomeCapitalizado.toString().trim();
	}
}
