package com.renato.projects.appointment.controller.dto.agendamento;

import java.time.LocalDateTime;

public record CreateAgendamentoDTO(
		Long procedimentoId,
		LocalDateTime dateTime,
		String email,
		String telefone
		) {
}
