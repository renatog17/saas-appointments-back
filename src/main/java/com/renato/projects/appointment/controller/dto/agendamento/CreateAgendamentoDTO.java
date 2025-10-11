package com.renato.projects.appointment.controller.dto.agendamento;

import java.time.LocalDateTime;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateAgendamentoDTO(
		@NotNull
		Long procedimentoId,
		@NotNull
		@FutureOrPresent
		LocalDateTime dateTime,
		String telefone,
		@NotBlank
		String email,
		@NotBlank
		String nome
		) {
}
