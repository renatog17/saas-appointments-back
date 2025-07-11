package com.renato.projects.appointment.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.renato.projects.appointment.controller.dto.agendamento.CreateAgendamentoDTO;
import com.renato.projects.appointment.controller.dto.agendamento.ReadAgendamentoDTO;
import com.renato.projects.appointment.domain.Agendamento;
import com.renato.projects.appointment.repository.AgendamentoRepository;
import com.renato.projects.appointment.service.strategy.agendamento.save.BuscarOuCriarConsumidor;
import com.renato.projects.appointment.service.strategy.agendamento.save.BuscarProcedimento;
import com.renato.projects.appointment.service.strategy.agendamento.save.SaveAgendamentoStrategy;

@Service
public class AgendamentoService {

	private AgendamentoRepository agendamentoRepository;
	private BuscarOuCriarConsumidor buscarOuCriarConsumidor;
	private BuscarProcedimento buscarProcedimento;

	public AgendamentoService(AgendamentoRepository agendamentoRepository,
			BuscarOuCriarConsumidor buscarOuCriarConsumidor, BuscarProcedimento buscarProcedimento) {
		super();
		this.agendamentoRepository = agendamentoRepository;
		this.buscarOuCriarConsumidor = buscarOuCriarConsumidor;
		this.buscarProcedimento = buscarProcedimento;
	}

	public ResponseEntity<ReadAgendamentoDTO> realizarAgendamento(CreateAgendamentoDTO agendamentoDTO) {
		Agendamento agendamento = new Agendamento();
		agendamento.setDateTime(agendamentoDTO.dateTime());

		List<SaveAgendamentoStrategy> strategies = new ArrayList<SaveAgendamentoStrategy>();
		strategies.add(buscarProcedimento);
		strategies.add(buscarOuCriarConsumidor);

		for (SaveAgendamentoStrategy saveAgendamentoStrategy : strategies) {
			saveAgendamentoStrategy.agendamentoStrategy(agendamento, agendamentoDTO);
		}

		agendamentoRepository.save(agendamento);

		return ResponseEntity.ok(new ReadAgendamentoDTO(agendamento));
	}
}
