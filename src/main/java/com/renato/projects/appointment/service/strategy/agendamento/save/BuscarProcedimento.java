package com.renato.projects.appointment.service.strategy.agendamento.save;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.renato.projects.appointment.controller.dto.agendamento.CreateAgendamentoDTO;
import com.renato.projects.appointment.domain.Agendamento;
import com.renato.projects.appointment.domain.Procedimento;
import com.renato.projects.appointment.repository.ProcedimentoRepository;

@Component
public class BuscarProcedimento implements SaveAgendamentoStrategy {

	private ProcedimentoRepository procedimentoRepository;

	public BuscarProcedimento(ProcedimentoRepository procedimentoRepository) {
		super();
		this.procedimentoRepository = procedimentoRepository;
	}

	@Override
	public void agendamentoStrategy(Agendamento agendamento, CreateAgendamentoDTO agendamentoDTO) {
		Procedimento procedimento = procedimentoRepository.findById(agendamentoDTO.procedimentoId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		agendamento.setProcedimento(procedimento);
		agendamento.setDateTimeTermino( agendamentoDTO.dateTime().plusMinutes(agendamento.getProcedimento().getTenant().getIntervaloEmMinutos()).minusMinutes(1));
	}

}
