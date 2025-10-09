package com.renato.projects.appointment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.renato.projects.appointment.controller.dto.auth.ConfirmarEmailDTO;
import com.renato.projects.appointment.controller.dto.confirmaremail.ReenviarCodigoDTO;
import com.renato.projects.appointment.service.ConfirmacaoEmailService;

@RestController
@RequestMapping("/confirmaremail")
public class ConfirmacaoEmailController {

	public ConfirmacaoEmailService confirmacaoEmailService;
	
	public ConfirmacaoEmailController(ConfirmacaoEmailService confirmacaoEmailService) {
		super();
		this.confirmacaoEmailService = confirmacaoEmailService;
	}

	@PostMapping
	@Transactional
	public ResponseEntity<?> confirmarEmail(@RequestBody ConfirmarEmailDTO confirmarEmailDTO) {
		if (confirmacaoEmailService.confirmarCodigo(confirmarEmailDTO)) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.badRequest().build();
	}

	@PostMapping("/reenviarcodigo")
	public ResponseEntity<?> reenviarCodigo(@RequestBody ReenviarCodigoDTO reenviarCodigoDTO) {
		confirmacaoEmailService.enviarNovoCodigo(reenviarCodigoDTO.email());
		return ResponseEntity.ok().build();
	}
}
