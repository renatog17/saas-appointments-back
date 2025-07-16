package com.renato.projects.appointment.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mailjet.client.errors.MailjetException;
import com.renato.projects.appointment.controller.dto.agendamento.CreateAgendamentoDTO;
import com.renato.projects.appointment.controller.dto.agendamento.ReadAgendamentoDTO;
import com.renato.projects.appointment.domain.Agendamento;
import com.renato.projects.appointment.repository.AgendamentoRepository;
import com.renato.projects.appointment.service.strategy.agendamento.save.BuscarOuCriarConsumidor;
import com.renato.projects.appointment.service.strategy.agendamento.save.BuscarProcedimento;
import com.renato.projects.appointment.service.strategy.agendamento.save.SaveAgendamentoStrategy;

import jakarta.transaction.Transactional;

@Service
public class AgendamentoService {

	private AgendamentoRepository agendamentoRepository;
	private BuscarOuCriarConsumidor buscarOuCriarConsumidor;
	private BuscarProcedimento buscarProcedimento;
	private EmailService emailService;

	public AgendamentoService(AgendamentoRepository agendamentoRepository,
			BuscarOuCriarConsumidor buscarOuCriarConsumidor, BuscarProcedimento buscarProcedimento,
			EmailService emailService) {
		super();
		this.agendamentoRepository = agendamentoRepository;
		this.buscarOuCriarConsumidor = buscarOuCriarConsumidor;
		this.buscarProcedimento = buscarProcedimento;
		this.emailService = emailService;
	}

	@Transactional
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
		
		try {
			emailService.sendEmail(agendamento);
		} catch (MailjetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//se houver erro em agendamento, o código alcança essa linha?
		
		
		return ResponseEntity.ok(new ReadAgendamentoDTO(agendamento));
	}

	public Map<LocalDate, List<LocalTime>> obterAgendamentosPorTenant(Long tenantId) {
		List<Agendamento> agendamentos = agendamentoRepository.findByProcedimento_Tenant_IdAndDateTimeAfter(tenantId, LocalDateTime.now());
		Map<LocalDate, List<LocalTime>> agendamentosMap = new HashMap<LocalDate, List<LocalTime>>();
		for (Agendamento agendamento : agendamentos) {
			LocalDateTime dateTime = agendamento.getDateTime();
			LocalDate date = dateTime.toLocalDate();
			if(!agendamentosMap.containsKey(date)) {
				agendamentosMap.put(date, new ArrayList<LocalTime>());
			}
			LocalTime time = dateTime.toLocalTime();
			agendamentosMap.get(date).add(time);
		}
		
		return agendamentosMap;
	}
}
