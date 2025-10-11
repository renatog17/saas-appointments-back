package com.renato.projects.appointment.controller;

import java.time.Duration;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.renato.projects.appointment.controller.dto.auth.ConfirmarEmailDTO;
import com.renato.projects.appointment.controller.dto.auth.GerarCodigoUnicoDTO;
import com.renato.projects.appointment.security.domain.CodigoAuxiliar;
import com.renato.projects.appointment.security.domain.User;
import com.renato.projects.appointment.security.repository.UserRepository;
import com.renato.projects.appointment.security.service.TokenService;
import com.renato.projects.appointment.service.email.strategy.EnvioCodigoLoginUnico;

@RequestMapping("/login-code")
@RestController
public class LoginCodigoUnicoController {

	private EnvioCodigoLoginUnico envioCodigoLoginUnico;
	private UserRepository userRepository;
	private TokenService tokenService;
	
	
	public LoginCodigoUnicoController(EnvioCodigoLoginUnico envioCodigoLoginUnico, UserRepository userRepository,
			TokenService tokenService) {
		this.envioCodigoLoginUnico = envioCodigoLoginUnico;
		this.userRepository = userRepository;
		this.tokenService = tokenService;
	}
	@PostMapping()
	@Transactional
	public ResponseEntity<?> confirmarCodigoLoginUnico(@RequestBody ConfirmarEmailDTO confirmarEmailDTO) {
		String login = confirmarEmailDTO.login();
		
		User user = (User) userRepository.findByLogin(login).orElseThrow();
		
		CodigoAuxiliar codigoAuxiliar = user.getCodigoAuxiliar();
		if(codigoAuxiliar.isExpirado()) {
			return ResponseEntity.badRequest().build();
		}else {
			if(confirmarEmailDTO.codigo().equals(codigoAuxiliar.getCodigo())) {
				
				var token = tokenService.generateToken(user);
				System.out.println(token);
				ResponseCookie cookie = ResponseCookie.from("token", token).httpOnly(true).secure(true)
						.sameSite("Lax")
						.path("/").maxAge(Duration.ofHours(2)).build();
				return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).build();
							
			}else {
				return ResponseEntity.badRequest().build();
			}
		}
	}
	@PostMapping("/gerarcodigo")
	@Transactional
	public ResponseEntity<?> gerarCodigo(@RequestBody GerarCodigoUnicoDTO gerarCodigoUnicoDTO){
		User user = (User) userRepository.findByLogin(gerarCodigoUnicoDTO.email()).orElseThrow();
		user.getCodigoAuxiliar().gerarNovoCodigo();
		
		envioCodigoLoginUnico.enviarEmail(user);
		
		return ResponseEntity.ok().build();
	}
}
