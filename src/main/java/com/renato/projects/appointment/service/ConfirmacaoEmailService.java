package com.renato.projects.appointment.service;

import org.springframework.stereotype.Service;

import com.renato.projects.appointment.controller.dto.auth.ConfirmarEmailDTO;
import com.renato.projects.appointment.security.domain.CodigoAuxiliar;
import com.renato.projects.appointment.security.domain.User;
import com.renato.projects.appointment.security.repository.UserRepository;
import com.renato.projects.appointment.service.email.strategy.ConfirmacaoCadastroNovoUserTenant;

import jakarta.transaction.Transactional;

@Service
public class ConfirmacaoEmailService {
	
	private UserRepository userRepository;
	private ConfirmacaoCadastroNovoUserTenant cadastroNovoUserTenant;
	public CodigoAuxiliarService codigoAuxiliarService;
	
	public ConfirmacaoEmailService(UserRepository userRepository,
			ConfirmacaoCadastroNovoUserTenant cadastroNovoUserTenant,
			CodigoAuxiliarService codigoAuxiliarService) {
		super();
		this.userRepository = userRepository;
		this.cadastroNovoUserTenant = cadastroNovoUserTenant;
		this.codigoAuxiliarService = codigoAuxiliarService;
	}

	@Transactional
	public void enviarNovoCodigo(String login) {
		
		codigoAuxiliarService.gerarNovoCodigoAuxiliar(login);
		
		User user = (User) userRepository.findByLogin(login).orElseThrow();
		
		cadastroNovoUserTenant.enviarEmail(user);
	}
	
	public Boolean confirmarCodigo(ConfirmarEmailDTO confirmarEmailDTO) {
		User user = (User) userRepository.findByLogin(confirmarEmailDTO.login()).orElseThrow();
		CodigoAuxiliar codigoAuxiliar = user.getCodigoAuxiliar();
		if(codigoAuxiliar.getCodigo().equals(confirmarEmailDTO.codigo())) {
			user.confirmarEmail();
			return true;
		}
		return false;
	}
}
