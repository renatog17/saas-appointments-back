package com.renato.projects.appointment.controller.dto.agendamento;

import java.time.LocalDateTime;

import com.renato.projects.appointment.controller.dto.consumidor.ReadConsumidorDTO;
import com.renato.projects.appointment.controller.dto.procedimento.ReadProcedimentoDTO;
import com.renato.projects.appointment.domain.Agendamento;

public record ReadAgendamentoDTO(
		Long id,
		LocalDateTime dateTime,
		ReadProcedimentoDTO procedimento,
		ReadConsumidorDTO consumidor) {

	public ReadAgendamentoDTO(Agendamento agendamento) {
		this(agendamento.getId(), agendamento.getDateTime(), 
				new ReadProcedimentoDTO(agendamento.getProcedimento()),
				new ReadConsumidorDTO(agendamento.getConsumidor()));
	}
}
