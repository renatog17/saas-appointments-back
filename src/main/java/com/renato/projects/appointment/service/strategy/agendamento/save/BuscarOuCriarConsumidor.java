package com.renato.projects.appointment.service.strategy.agendamento.save;

import org.springframework.stereotype.Component;

import com.renato.projects.appointment.controller.dto.agendamento.CreateAgendamentoDTO;
import com.renato.projects.appointment.domain.Agendamento;
import com.renato.projects.appointment.domain.Consumidor;
import com.renato.projects.appointment.repository.ConsumidorRepository;

@Component
public class BuscarOuCriarConsumidor implements SaveAgendamentoStrategy{

	private ConsumidorRepository consumidorRepository;
	
	@Override
	public void agendamentoStrategy(Agendamento agendamento, CreateAgendamentoDTO agendamentoDTO) {
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
		agendamento.setConsumidor(consumidor);
	}

}
