package com.renato.projects.appointment.service;

import org.springframework.stereotype.Service;

import com.renato.projects.appointment.security.domain.User;
import com.renato.projects.appointment.security.repository.UserRepository;
import com.renato.projects.appointment.service.email.strategy.ConfirmacaoCadastroNovoUserTenant;

import jakarta.transaction.Transactional;

@Service
public class ConfirmacaoEmailService {
	
	private UserRepository userRepository;
	private ConfirmacaoCadastroNovoUserTenant cadastroNovoUserTenant;
	
	public ConfirmacaoEmailService(UserRepository userRepository, ConfirmacaoCadastroNovoUserTenant cadastroNovoUserTenant) {
		super();
		this.userRepository = userRepository;
		this.cadastroNovoUserTenant = cadastroNovoUserTenant;
	}

	@Transactional
	public void gerarNovoCodigo(String loginOrId) {
		User user;
		if(loginOrId.contains("@")) {
			user = (User) userRepository.findByLogin(loginOrId).orElseThrow();
		}else {
			user = (User) userRepository.findById(Long.parseLong(loginOrId)).orElseThrow();
		}
			
		user.getConfirmacaoEmail().gerarNovoCodigo();
		cadastroNovoUserTenant.enviarEmail(user);
	}
}
