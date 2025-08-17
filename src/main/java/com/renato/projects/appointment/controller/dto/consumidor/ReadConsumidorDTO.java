package com.renato.projects.appointment.controller.dto.consumidor;

import com.renato.projects.appointment.domain.Consumidor;

public record ReadConsumidorDTO(
		Long id,
		String telefone,
		String nome,
		String email
		) {
	
	public ReadConsumidorDTO(Consumidor consumidor) {
		this(consumidor.getId(), consumidor.getTelefone(), consumidor.getNome(), consumidor.getEmail());
	}

}
