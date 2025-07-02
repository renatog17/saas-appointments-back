package com.renato.projects.appointment.controller.dto.agendamento;

import java.time.LocalDateTime;

import com.renato.projects.appointment.controller.dto.procedimento.ReadProcedimentoDTO;
import com.renato.projects.appointment.domain.Agendamento;

public record ReadAgendamentoDTO(
		Long id,
		LocalDateTime dateTime,
		ReadProcedimentoDTO procedimento) {

	public ReadAgendamentoDTO(Agendamento agendamento) {
		this(agendamento.getId(), agendamento.getDateTime(), new ReadProcedimentoDTO(agendamento.getProcedimento()));
	}
}
