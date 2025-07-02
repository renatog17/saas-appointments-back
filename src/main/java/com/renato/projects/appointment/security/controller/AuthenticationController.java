package com.renato.projects.appointment.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.renato.projects.appointment.controller.exceptionhandler.DadosErroValidacao;
import com.renato.projects.appointment.security.controller.dto.AuthenticationDTO;
import com.renato.projects.appointment.security.controller.dto.LoginResponseDTO;
import com.renato.projects.appointment.security.controller.dto.RegisterDTO;
import com.renato.projects.appointment.security.domain.User;
import com.renato.projects.appointment.security.domain.UserRole;
import com.renato.projects.appointment.security.repository.UserRepository;
import com.renato.projects.appointment.security.service.TokenService;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TokenService tokenService;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody AuthenticationDTO data) {
		var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
		var auth = this.authenticationManager.authenticate(usernamePassword);
		var token = tokenService.generateToken((User) auth.getPrincipal());
		return ResponseEntity.ok(new LoginResponseDTO(token));
	}
	
	@PostMapping("/register")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "400", description = "Não é possível criar um novo usuário com as informações dadas",
					content = @Content(schema = @Schema(implementation = DadosErroValidacao.class))),
			@ApiResponse(responseCode = "200", description = "Novo usuário cadastrado com sucesso")
	})
	public ResponseEntity<?> register(@Valid @RequestBody RegisterDTO data) {
		if(this.userRepository.findByLogin(data.login()) != null)
			return ResponseEntity.badRequest().build();
		String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
		User newUser = new User(data.login(), encryptedPassword, UserRole.USER);
		
		this.userRepository.save(newUser);
		return ResponseEntity.ok().build();
	}
}