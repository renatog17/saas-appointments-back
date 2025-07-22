package com.renato.projects.appointment.security.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.renato.projects.appointment.controller.exceptionhandler.DadosErroValidacao;
import com.renato.projects.appointment.domain.Tenant;
import com.renato.projects.appointment.repository.TenantRepository;
import com.renato.projects.appointment.security.controller.dto.AuthenticationDTO;
import com.renato.projects.appointment.security.controller.dto.ConfirmarEmailDTO;
import com.renato.projects.appointment.security.controller.dto.LoginResponseDTO;
import com.renato.projects.appointment.security.controller.dto.RegisterDTO;
import com.renato.projects.appointment.security.domain.User;
import com.renato.projects.appointment.security.domain.UserRole;
import com.renato.projects.appointment.security.repository.UserRepository;
import com.renato.projects.appointment.security.service.TokenService;
import com.renato.projects.appointment.service.ProcedimentoService;
import com.renato.projects.appointment.service.email.strategy.ConrfirmacaoCadastroNovoUserTenant;

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
	@Autowired
	private ConrfirmacaoCadastroNovoUserTenant conrfirmacaoCadastroNovoUserTenant;
	@Autowired
	private TenantRepository tenantRepository;
	@Autowired
	private ProcedimentoService procedimentoService;
	
	@GetMapping("/{email}")
	public ResponseEntity<?> existUserByEmail(@PathVariable String email){
		return ResponseEntity.ok(userRepository.existsByLogin(email));
	}
	
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
		User newUser = new User(data.login(), encryptedPassword, UserRole.USER, false);
		
		this.userRepository.save(newUser);
		//Renato, o certo é salvar tudo ao final, quando tudo estiver verificado
		Tenant tenant = new Tenant(data.name(), data.tenant());
		Optional<Tenant> findBySlug = tenantRepository.findBySlug(tenant.getSlug());
		if(findBySlug.isEmpty()) {
			tenantRepository.save(tenant);
		}
		
		procedimentoService.salvarProcedimentos(data.procedimentos());
		
		//conrfirmacaoCadastroNovoUserTenant.enviarEmail(newUser);
		return ResponseEntity.ok().build();
	}
	
	//falta mapeamento aqui
	public ResponseEntity<?> confirmarEmail(@RequestBody ConfirmarEmailDTO confirmarEmailDTO){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
		    User user = (User) authentication.getPrincipal();
		    if(user.getCodigoConfirmacaoEmail().equals(confirmarEmailDTO.codigo())) {
		    	user.setConfirmacaoEmail(true);
		    }
		    return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.badRequest().build();
		
	}
}