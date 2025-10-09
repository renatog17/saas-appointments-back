package com.renato.projects.appointment.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.renato.projects.appointment.controller.dto.disponibilidade.PostDisponibilidadeDTO;
import com.renato.projects.appointment.service.DisponibilidadeService;

@RestController
@RequestMapping("/disponibilidade")
public class DisponibilidadeController {

	private DisponibilidadeService disponibilidadeService;

	public DisponibilidadeController(DisponibilidadeService disponibilidadeService) {
		super();
		this.disponibilidadeService = disponibilidadeService;
	}

	@PostMapping
	public ResponseEntity<?> adicionarIntervalos(@RequestBody List<PostDisponibilidadeDTO> disponibilidadeDTO){
		disponibilidadeService.atualizarIntervalos(disponibilidadeDTO);
		return ResponseEntity.ok().build();
	}	
}
