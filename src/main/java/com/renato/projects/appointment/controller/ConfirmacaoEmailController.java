package com.renato.projects.appointment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.renato.projects.appointment.controller.dto.confirmaremail.ReenviarCodigoDTO;
import com.renato.projects.appointment.service.ConfirmacaoEmailService;
import com.renato.projects.appointment.service.email.strategy.ConfirmacaoCadastroNovoUserTenant;

@RestController
@RequestMapping("/confirmacaoemail")
public class ConfirmacaoEmailController {

	
	public ConfirmacaoEmailService confirmacaoEmailService;
	
	public ConfirmacaoEmailController(ConfirmacaoEmailService confirmacaoEmailService, 
			ConfirmacaoCadastroNovoUserTenant cadastroNovoUserTenant) {
		super();
		this.confirmacaoEmailService = confirmacaoEmailService;
	}



	@PostMapping
	public ResponseEntity<?> reenviarCodigo(@RequestBody ReenviarCodigoDTO reenviarCodigoDTO){
		confirmacaoEmailService.gerarNovoCodigo(reenviarCodigoDTO.loginOrId());
		return ResponseEntity.ok().build();
	}
}
