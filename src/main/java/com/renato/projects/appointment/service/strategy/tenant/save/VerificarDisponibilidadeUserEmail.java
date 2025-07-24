package com.renato.projects.appointment.service.strategy.tenant.save;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.renato.projects.appointment.controller.dto.tenant.PostTenantDTO;
import com.renato.projects.appointment.domain.Tenant;
import com.renato.projects.appointment.security.domain.User;
import com.renato.projects.appointment.service.AuthenticationService;

@Component
public class VerificarDisponibilidadeUserEmail implements SaveTenantStrategy {

	private AuthenticationService authenticationService;

	public VerificarDisponibilidadeUserEmail(AuthenticationService authenticationService) {
		super();
		this.authenticationService = authenticationService;
	}

	@Override
	public void tenantStrategy(PostTenantDTO postTenantDTO, Tenant tenant) {
		User user = postTenantDTO.toModel().getUser();

		if(authenticationService.existUserByEmail(user.getLogin())) {
			System.out.println("HELLO1");
			throw new ResponseStatusException(HttpStatus.CONFLICT);
		}
	}

}
