package com.renato.projects.appointment.service.strategy.agendamento.save;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.renato.projects.appointment.controller.dto.agendamento.CreateAgendamentoDTO;
import com.renato.projects.appointment.domain.Agendamento;
import com.renato.projects.appointment.domain.Consumidor;
import com.renato.projects.appointment.repository.ConsumidorRepository;

import jakarta.transaction.Transactional;

@Component
public class BuscarOuCriarConsumidor implements SaveAgendamentoStrategy {

	private ConsumidorRepository consumidorRepository;

	public BuscarOuCriarConsumidor(ConsumidorRepository consumidorRepository) {
		super();
		this.consumidorRepository = consumidorRepository;
	}

	@Override
	@Transactional
	public void agendamentoStrategy(Agendamento agendamento, CreateAgendamentoDTO agendamentoDTO) {
		Optional<Consumidor> consumidor = consumidorRepository.findByEmail(agendamentoDTO.email());
		
		if(consumidor.isEmpty()) {
			Consumidor novoConsumidor = new Consumidor(agendamentoDTO.email(), agendamentoDTO.nome());
			consumidorRepository.save(novoConsumidor);
			agendamento.setConsumidor(novoConsumidor);
		}else {
			Consumidor consumidorExistente = consumidor.get();
			consumidorExistente.setNome(agendamentoDTO.nome());
			agendamento.setConsumidor(consumidorExistente);	
		}
	}

}
