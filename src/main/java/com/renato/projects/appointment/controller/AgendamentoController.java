package com.renato.projects.appointment.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
	//teste githubactions
	//mais um teste
	@PostMapping
	public ResponseEntity<?> criarAgendamento(@RequestBody CreateAgendamentoDTO createAgendamentoDTO){
		agendamentoService.realizarAgendamento(createAgendamentoDTO);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{tenantId}")
	public ResponseEntity<?> getAgendamentos(@PathVariable Long tenantId){
		return agendamentoService.obterAgendamentosPorTenant(tenantId);
	}
	
	@DeleteMapping("/{tenantId}")
	public void exluirAgendamento(@PathVariable Long tenantId){
		//tem que informar a quem cadastrou
		agendamentoService.excluirAgendamentoPorId(tenantId);
	}
}
