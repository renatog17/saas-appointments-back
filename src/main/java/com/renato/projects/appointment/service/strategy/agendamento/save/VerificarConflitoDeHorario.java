package com.renato.projects.appointment.service.strategy.agendamento.save;

import java.util.List;

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
		//nesse ponto o agendamento já vem com a hora de inicio e de termino.
		//primeiro caso: buscar se existe agendamento que comece depois ou na hora de inicio e antes ou na hora de termino
		//segundo teste: buscar se existe agendamento que termine depois ou na hora de inicio e antes ou na hora de termino
		//terceiro teste: verificar se existe agendamento que começa antes ou na hora de inicio e termina depois ou na hora de termino
		List<Agendamento> agendamentos = agendamentoRepository.findConflitos(agendamento.getDateTime(), agendamento.getDateTimeTermino());
		if (agendamentos.size()>0)
			throw new ResponseStatusException(HttpStatus.CONFLICT);
		
	}

}
