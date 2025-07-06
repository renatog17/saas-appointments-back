package com.renato.projects.appointment.service.strategy.tenant.save;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.renato.projects.appointment.controller.dto.tenant.PostTenantDTO;
import com.renato.projects.appointment.domain.Tenant;
import com.renato.projects.appointment.repository.TenantRepository;

@Component
public class VerificarDisponibilidadeSlug implements SaveTenantStrategy {

	private TenantRepository repository;

	public VerificarDisponibilidadeSlug(TenantRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public void tenantStrategy(PostTenantDTO postTenantDTO, Tenant tenant) {
		Optional<Tenant> tenantOptional = repository.findBySlug(tenant.getSlug());
		if (tenantOptional.isPresent()) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Este Slug já está em uso");
		}
	}

}
