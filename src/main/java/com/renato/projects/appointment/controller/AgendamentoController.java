package com.renato.projects.appointment.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.renato.projects.appointment.controller.dto.agendamento.CreateAgendamentoDTO;
import com.renato.projects.appointment.service.AgendamentoService;

@RestController
@RequestMapping("/agendamento")
public class AgendamentoController {

	private AgendamentoService agendamentoService;

	public AgendamentoController(AgendamentoService agendamentoService) {
		super();
		this.agendamentoService = agendamentoService;
	}
	
	@PostMapping
	public ResponseEntity<?> criarAgendamento(@RequestBody CreateAgendamentoDTO createAgendamentoDTO){
		agendamentoService.realizarAgendamento(createAgendamentoDTO);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{tenantId}")
	public ResponseEntity<?> getAgendamentos(@PathVariable Long tenantId){
		Map<LocalDate, List<LocalTime>> agendamentos = agendamentoService.obterAgendamentosPorTenant(tenantId);
		return ResponseEntity.ok(agendamentos);
	}
	
	@GetMapping("/details/{tenantId}")
	public ResponseEntity<?> getAgendamentosDetalhados(@PathVariable Long tenantId){
		// QUE BOM QUE EU DECIDI DAR UM NOVO PASSO!!!!
		return null;
	}
}
