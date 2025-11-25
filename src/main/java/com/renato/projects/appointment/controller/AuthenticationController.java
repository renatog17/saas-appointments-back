package com.renato.projects.appointment.controller;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
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

	private final AuthenticationService authenticationService;
	private final TokenService tokenService;
	
	@Value("${app.cookie.secure}")
    private boolean cookieSecure;

    @Value("${app.cookie.same-site}")
    private String cookieSameSite;

    @Value("${app.cookie.max-age-hours}")
    private long cookieMaxAgeHours;
	
	public AuthenticationController(AuthenticationService authenticationService, 
			TokenService tokenService) {
		this.authenticationService = authenticationService;
		this.tokenService = tokenService;
		
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody AuthenticationDTO data, HttpServletResponse response) {
		String token = authenticationService.login(data).token();
		ResponseCookie cookie = 
				ResponseCookie
					.from("token", token)
					.httpOnly(true)
					.secure(cookieSecure)
					.sameSite(cookieSameSite)
					.path("/")
					.domain("zendaavip.com.br") 
					.maxAge(Duration.ofHours(cookieMaxAgeHours))
					.build();
		
		return ResponseEntity
					.ok()
					.header(HttpHeaders.SET_COOKIE, cookie.toString())
					.build();
	}

	@GetMapping("/check")
	public ResponseEntity<Void> check(@CookieValue(name = "token", required = false) String token) {
		if (token != null && !token.isEmpty()) {
			String subject = tokenService.validateToken(token);

			if (!subject.isEmpty()) {
				return ResponseEntity.ok().build(); 
			}
		}
		return ResponseEntity
				.status(HttpStatus.UNAUTHORIZED)
				.build();
	}

	@PostMapping("/logout")
	public ResponseEntity<?> logout(HttpServletResponse response) {
		ResponseCookie cookie = 
				ResponseCookie
					.from("token", "")
					.httpOnly(true)
					.secure(cookieSecure)
					.sameSite(cookieSameSite)
					.path("/")
					.maxAge(0)
					.build();
		return ResponseEntity
				.ok()
				.header(HttpHeaders.SET_COOKIE, cookie.toString())
				.body("Logout realizado");
	}

	@GetMapping("/{email}")
	public ResponseEntity<?> existUserByEmail(@PathVariable String email) {
		return ResponseEntity.ok(authenticationService.existUserByEmail(email));
	}

}