package com.renato.projects.appointment.utils;

import java.util.Random;

public class GerarCodigoConfirmacaoEmail {

	public static String gerarCodigo() {
        Random random = new Random();
        int codigo = 100000 + random.nextInt(900000); // garante número de 6 dígitos
        return String.valueOf(codigo);
    }
}
