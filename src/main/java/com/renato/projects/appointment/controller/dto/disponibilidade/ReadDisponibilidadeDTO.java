package com.renato.projects.appointment.controller.dto.disponibilidade;

import java.time.LocalTime;

import com.renato.projects.appointment.domain.Disponibilidade;

public record ReadDisponibilidadeDTO(
		Long id,
		Integer diaDaSemana,
		LocalTime inicio,
		LocalTime fim
		) {

	public ReadDisponibilidadeDTO(Disponibilidade disponibilidade) {
		this(
				disponibilidade.getId(),
				disponibilidade.getDiaDaSemana(),
				disponibilidade.getInicio(),
				disponibilidade.getFim()
				);
	}
}
