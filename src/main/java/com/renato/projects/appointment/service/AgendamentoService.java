package com.renato.projects.appointment.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.renato.projects.appointment.controller.dto.agendamento.CreateAgendamentoDTO;
import com.renato.projects.appointment.controller.dto.agendamento.ReadAgendamentoDTO;
import com.renato.projects.appointment.domain.Agendamento;
import com.renato.projects.appointment.domain.Consumidor;
import com.renato.projects.appointment.domain.Procedimento;
import com.renato.projects.appointment.repository.AgendamentoRepository;
import com.renato.projects.appointment.repository.ConsumidorRepository;
import com.renato.projects.appointment.repository.ProcedimentoRepository;

@Service
public class AgendamentoService {

	private AgendamentoRepository agendamentoRepository;
	private ProcedimentoRepository procedimentoRepository;
	private ConsumidorRepository consumidorRepository;
	
	public AgendamentoService(AgendamentoRepository agendamentoRepository) {
		super();
		this.agendamentoRepository = agendamentoRepository;
	}
	
	public ResponseEntity<ReadAgendamentoDTO> realizarAgendamento(CreateAgendamentoDTO agendamentoDTO) {
		//obter o procedimento
		Procedimento procedimento = procedimentoRepository.findById(agendamentoDTO.procedimentoId()).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND)
				);
		//obter consumidor por email ou telefone
		Consumidor consumidor;
		if(agendamentoDTO.email()!=null)
			consumidor = consumidorRepository.findByEmail(agendamentoDTO.email());
		else {
			if(agendamentoDTO.telefone()!=null)
				consumidor = consumidorRepository.findByTelefone(agendamentoDTO.telefone());
			else {
				consumidor = new Consumidor(agendamentoDTO.telefone(), agendamentoDTO.email());
				consumidorRepository.save(consumidor);
			}
		}
		//criar agendamento
		Agendamento agendamento = new Agendamento(consumidor, procedimento, agendamentoDTO.dateTime());
		agendamentoRepository.save(agendamento);
		
		return ResponseEntity.ok(new ReadAgendamentoDTO(agendamento));
	}
}
