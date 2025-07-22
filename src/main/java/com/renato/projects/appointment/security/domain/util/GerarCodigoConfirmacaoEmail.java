package com.renato.projects.appointment.security.domain.util;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class GerarCodigoConfirmacaoEmail {

	public static String gerarCodigo() {
		Random random = new Random();
		Set<Integer> numeros = new HashSet<Integer>();
		while(numeros.size()<6) {
			numeros.add(random.nextInt(10));
		}
		
		String codigo = ""; 
		for (Integer integer : numeros) {
			codigo += integer.toString();
		}
		return codigo;
	}
}
