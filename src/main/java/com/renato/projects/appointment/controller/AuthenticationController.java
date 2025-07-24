package com.renato.projects.appointment.controller;

import java.time.Duration;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.renato.projects.appointment.controller.dto.auth.AuthenticationDTO;
import com.renato.projects.appointment.controller.dto.auth.ConfirmarEmailDTO;
import com.renato.projects.appointment.controller.dto.auth.RegisterDTO;
import com.renato.projects.appointment.controller.exceptionhandler.DadosErroValidacao;
import com.renato.projects.appointment.security.domain.User;
import com.renato.projects.appointment.security.repository.UserRepository;
import com.renato.projects.appointment.security.service.TokenService;
import com.renato.projects.appointment.service.AuthenticationService;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

	private AuthenticationService authenticationService;
	private UserRepository userRepository;
	private TokenService tokenService;

	public AuthenticationController(AuthenticationService authenticationService, UserRepository userRepository,
			TokenService tokenService) {
		super();
		this.authenticationService = authenticationService;
		this.userRepository = userRepository;
		this.tokenService = tokenService;
	}

//	@Autowired
//	private ConrfirmacaoCadastroNovoUserTenant conrfirmacaoCadastroNovoUserTenant;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody AuthenticationDTO data, HttpServletResponse response) {
		String token = authenticationService.login(data).token();
		//return ResponseEntity.ok(authenticationService.login(data));
		 ResponseCookie cookie = ResponseCookie.from("token", token)
			        .httpOnly(true)
			        .secure(true) // true se estiver em HTTPS
			        //FORTE CANDIDATO A IR PARA PROFILE
			        .sameSite("Lax") // ou "Strict" para mais proteção
			        .path("/")
			        .maxAge(Duration.ofHours(2))
			        .build();
		 return ResponseEntity.ok()
			        .header(HttpHeaders.SET_COOKIE, cookie.toString())
			        .build();
	}
	
	@GetMapping("/check")
	public ResponseEntity<Void> check(@CookieValue(name = "token", required = false) String token) {
	    if (token != null && !token.isEmpty()) {
	        String subject = tokenService.validateToken(token);
	        if (!subject.isEmpty()) {
	            return ResponseEntity.ok().build(); // token válido
	        }
	    }
	    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
	
	@PostMapping("/logout")
	public ResponseEntity<?> logout(HttpServletResponse response) {
	    ResponseCookie cookie = ResponseCookie.from("token", "")
	        .httpOnly(true)
	        .secure(true)
	        .path("/")
	        .maxAge(0) // Expira o cookie imediatamente
	        .build();
	    return ResponseEntity.ok()
	        .header(HttpHeaders.SET_COOKIE, cookie.toString())
	        .body("Logout realizado");
	}


	@GetMapping("/{email}")
	public ResponseEntity<?> existUserByEmail(@PathVariable String email) {
		return ResponseEntity.ok(authenticationService.existUserByEmail(email));
	}

	@PostMapping("/register")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "400", description = "Não é possível criar um novo usuário com as informações dadas", content = @Content(schema = @Schema(implementation = DadosErroValidacao.class))),
			@ApiResponse(responseCode = "200", description = "Novo usuário cadastrado com sucesso") })
	public ResponseEntity<?> register(@Valid @RequestBody RegisterDTO data) {

		// authenticationService.register(data);

		return ResponseEntity.ok().build();
	}

	// falta mapeamento aqui
	@PostMapping("/confirmaremail")
	@Transactional
	public ResponseEntity<?> confirmarEmail(@RequestBody ConfirmarEmailDTO confirmarEmailDTO) {
		Optional<UserDetails> userOptional = userRepository.findByLogin(confirmarEmailDTO.login()); 	
		if (userOptional.isPresent()) {
			User user = (User) userOptional.get();
			if (user.getCodigoConfirmacaoEmail().equals(confirmarEmailDTO.codigo())) {
				user.setConfirmacaoEmail(true);
				userRepository.save(user); // não se esqueça de salvar a alteração!
				return ResponseEntity.noContent().build();
			}
		}
		return ResponseEntity.badRequest().build();
	}
}