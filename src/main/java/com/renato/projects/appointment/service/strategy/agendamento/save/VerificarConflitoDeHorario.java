package com.renato.projects.appointment.service.strategy.agendamento.save;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.renato.projects.appointment.controller.dto.agendamento.CreateAgendamentoDTO;
import com.renato.projects.appointment.domain.Agendamento;
import com.renato.projects.appointment.repository.AgendamentoRepository;

@Component
public class VerificarConflitoDeHorario implements SaveAgendamentoStrategy {

	private AgendamentoRepository agendamentoRepository;

	public VerificarConflitoDeHorario(AgendamentoRepository agendamentoRepository) {
		super();
		this.agendamentoRepository = agendamentoRepository;
	}

	@Override
	public void agendamentoStrategy(Agendamento agendamento, CreateAgendamentoDTO agendamentoDTO) {
		Optional<Agendamento> agendamentoOptional = agendamentoRepository.findByDateTime(agendamentoDTO.dateTime());
		if (agendamentoOptional.isPresent())
			new ResponseStatusException(HttpStatus.CONFLICT);

	}

}
