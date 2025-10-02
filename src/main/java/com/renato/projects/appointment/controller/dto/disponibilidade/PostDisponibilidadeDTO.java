package com.renato.projects.appointment.controller.dto.disponibilidade;

import java.time.LocalTime;

import com.renato.projects.appointment.domain.Disponibilidade;

public record PostDisponibilidadeDTO(

		Integer dia,
		LocalTime inicio,
		LocalTime fim) {

	public Disponibilidade toModel() {
		return new Disponibilidade(this);
	}

}
