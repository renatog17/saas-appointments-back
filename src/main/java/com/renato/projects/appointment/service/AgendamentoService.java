package com.renato.projects.appointment.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.renato.projects.appointment.controller.dto.agendamento.CreateAgendamentoDTO;
import com.renato.projects.appointment.controller.dto.agendamento.ReadAgendamentoDTO;
import com.renato.projects.appointment.domain.Agendamento;
import com.renato.projects.appointment.repository.AgendamentoRepository;
import com.renato.projects.appointment.service.email.strategy.ConfirmacaoAgendamento;
import com.renato.projects.appointment.service.strategy.agendamento.save.BuscarOuCriarConsumidor;
import com.renato.projects.appointment.service.strategy.agendamento.save.BuscarProcedimento;
import com.renato.projects.appointment.service.strategy.agendamento.save.SaveAgendamentoStrategy;
import com.renato.projects.appointment.service.strategy.agendamento.save.VerificarConflitoDeHorario;

import jakarta.transaction.Transactional;

@Service
public class AgendamentoService {

	private AgendamentoRepository agendamentoRepository;
	private BuscarOuCriarConsumidor buscarOuCriarConsumidor;
	private BuscarProcedimento buscarProcedimento;
	private ConfirmacaoAgendamento confirmacaoAgendamento;
	private VerificarConflitoDeHorario verificarConflitoDeHorario;
	

	public AgendamentoService(AgendamentoRepository agendamentoRepository,
			BuscarOuCriarConsumidor buscarOuCriarConsumidor, BuscarProcedimento buscarProcedimento,
			ConfirmacaoAgendamento confirmacaoAgendamento,
			VerificarConflitoDeHorario verificarConflitoDeHorario) {
		super();
		this.agendamentoRepository = agendamentoRepository;
		this.buscarOuCriarConsumidor = buscarOuCriarConsumidor;
		this.buscarProcedimento = buscarProcedimento;
		this.confirmacaoAgendamento = confirmacaoAgendamento;
		this.verificarConflitoDeHorario = verificarConflitoDeHorario;
	}

	@Transactional
	public ResponseEntity<ReadAgendamentoDTO> realizarAgendamento(CreateAgendamentoDTO agendamentoDTO) {
		Agendamento agendamento = new Agendamento();
		agendamento.setDateTime(agendamentoDTO.dateTime());

		List<SaveAgendamentoStrategy> strategies = new ArrayList<SaveAgendamentoStrategy>();
		strategies.add(buscarProcedimento);
		strategies.add(buscarOuCriarConsumidor);
		strategies.add(verificarConflitoDeHorario);
		
		for (SaveAgendamentoStrategy saveAgendamentoStrategy : strategies) {
			saveAgendamentoStrategy.agendamentoStrategy(agendamento, agendamentoDTO);
		}

		agendamentoRepository.save(agendamento);

		confirmacaoAgendamento.enviarEmail(agendamento);

		// se houver erro em agendamento, o código alcança essa linha?
		return ResponseEntity.ok(new ReadAgendamentoDTO(agendamento));
	}

	public ResponseEntity<List<ReadAgendamentoDTO>> obterAgendamentosPorTenant(Long tenantId) {
	    List<Agendamento> agendamentos = agendamentoRepository.findByProcedimento_Tenant_IdAndDateTimeAfter(
	        tenantId, LocalDateTime.now()
	    );
	    return ResponseEntity.ok(agendamentos.stream().map(ReadAgendamentoDTO::new).toList());
	}
	
	public ResponseEntity<ReadAgendamentoDTO> obterAgendamentosDetalhadosPorTenant(Long tenantId){
		List<Agendamento> agendamentos = agendamentoRepository.findByProcedimento_Tenant_IdAndDateTimeAfter(
		        tenantId, LocalDateTime.now()
		    );
		return null;
	}
}
