package com.renato.projects.appointment.controller.dto.indisponibilidade;

import java.time.LocalDate;
import java.time.LocalTime;

import com.renato.projects.appointment.domain.Indisponibilidade;

public record PostIndisponibilidadeDTO(
		
		LocalDate data,
		LocalTime inicio,
		LocalTime fim
		) {
	
	public Indisponibilidade toModel() {
		return new Indisponibilidade(this);
	}

}
