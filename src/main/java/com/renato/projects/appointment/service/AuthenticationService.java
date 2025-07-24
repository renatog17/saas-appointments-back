package com.renato.projects.appointment.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.renato.projects.appointment.controller.dto.auth.AuthenticationDTO;
import com.renato.projects.appointment.controller.dto.auth.LoginResponseDTO;
import com.renato.projects.appointment.security.domain.User;
import com.renato.projects.appointment.security.repository.UserRepository;
import com.renato.projects.appointment.security.service.TokenService;

@Service
public class AuthenticationService {

	private AuthenticationManager authenticationManager;
	private UserRepository userRepository;
	private TokenService tokenService;

	public AuthenticationService(AuthenticationManager authenticationManager, UserRepository userRepository,
			TokenService tokenService) {
		super();
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.tokenService = tokenService;
	}

	public LoginResponseDTO login(AuthenticationDTO data) {
		var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
		var auth = this.authenticationManager.authenticate(usernamePassword);
		var token = tokenService.generateToken((User) auth.getPrincipal());
		return new LoginResponseDTO(token);
	}

	public Boolean existUserByEmail(String email) {
		return userRepository.existsByLogin(email);
	}
}
