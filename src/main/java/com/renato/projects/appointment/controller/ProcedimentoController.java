package com.renato.projects.appointment.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.renato.projects.appointment.controller.dto.procedimento.PostProcedimentoDTO;
import com.renato.projects.appointment.service.ProcedimentoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/procedimento")
public class ProcedimentoController {

	private ProcedimentoService procedimentoService;

	public ProcedimentoController(ProcedimentoService prodcedimentoService) {
		super();
		this.procedimentoService = prodcedimentoService;
	}

	@PostMapping
	public ResponseEntity<?> postProcedimentos(@RequestBody @Valid List<PostProcedimentoDTO> procedimentos){
		procedimentoService.salvarProcedimentos(procedimentos);
		return ResponseEntity.ok().build();
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<?> arquivarProcedimento(@PathVariable Long id){
		System.out.println("desabilitando procedimento");
		procedimentoService.arquivarProcedimento(id);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletarProcedimento(@PathVariable Long id){
		procedimentoService.deletarProcedimento(id);
		return ResponseEntity.noContent().build();
	}
}
