package com.renato.projects.appointment.controller;

import java.time.Duration;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.renato.projects.appointment.controller.dto.auth.AuthenticationDTO;
import com.renato.projects.appointment.security.service.TokenService;
import com.renato.projects.appointment.service.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

	private AuthenticationService authenticationService;
	private TokenService tokenService;
	

	public AuthenticationController(AuthenticationService authenticationService, 
			TokenService tokenService) {
		super();
		this.authenticationService = authenticationService;
		this.tokenService = tokenService;
		
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody AuthenticationDTO data, HttpServletResponse response) {
		String token = authenticationService.login(data).token();
		ResponseCookie cookie = ResponseCookie.from("token", token).httpOnly(true).secure(false) // true se estiver em
																								// HTTPS
				// FORTE CANDIDATO A IR PARA PROFILE
				.sameSite("Lax") // ou "Strict" para mais proteção
				.path("/").maxAge(Duration.ofHours(2)).build();
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).build();
		// o return abaixo, é utilizado quando não se está trabalhando com token
		// return ResponseEntity.ok(authenticationService.login(data));
	}

	@GetMapping("/check")
	public ResponseEntity<Void> check(@CookieValue(name = "token", required = false) String token) {
		if (token != null && !token.isEmpty()) {
			String subject = tokenService.validateToken(token);

			if (!subject.isEmpty()) {
				return ResponseEntity.ok().build(); 
			}
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}

	@PostMapping("/logout")
	public ResponseEntity<?> logout(HttpServletResponse response) {
		ResponseCookie cookie = ResponseCookie.from("token", "").httpOnly(true).secure(true).path("/").maxAge(0)
				.build();
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body("Logout realizado");
	}

	@GetMapping("/{email}")
	public ResponseEntity<?> existUserByEmail(@PathVariable String email) {
		return ResponseEntity.ok(authenticationService.existUserByEmail(email));
	}

}