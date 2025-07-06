package com.renato.projects.appointment.service.strategy.agendamento.save;

import com.renato.projects.appointment.controller.dto.agendamento.CreateAgendamentoDTO;
import com.renato.projects.appointment.domain.Agendamento;

public interface SaveAgendamentoStrategy {

	void agendamentoStrategy(Agendamento agendamento, CreateAgendamentoDTO agendamentoDTO);
}
