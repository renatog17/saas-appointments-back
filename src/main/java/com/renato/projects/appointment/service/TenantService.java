package com.renato.projects.appointment.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.renato.projects.appointment.controller.dto.tenant.PostTenantDTO;
import com.renato.projects.appointment.controller.dto.tenant.ReadTenantDTO;
import com.renato.projects.appointment.domain.Tenant;
import com.renato.projects.appointment.repository.TenantRepository;
import com.renato.projects.appointment.security.domain.User;
import com.renato.projects.appointment.service.strategy.tenant.save.AssociarUser;
import com.renato.projects.appointment.service.strategy.tenant.save.SaveTenantStrategy;
import com.renato.projects.appointment.service.strategy.tenant.save.VerificarDisponibilidadeSlug;

import jakarta.transaction.Transactional;

@Service
public class TenantService {

	private TenantRepository tenantRepository;
	private AssociarUser associarUser;
	private VerificarDisponibilidadeSlug disponibilidadeSlug;

	public TenantService(TenantRepository tenantRepository,
			AssociarUser associarUser,
			VerificarDisponibilidadeSlug disponibilidadeSlug) {
		super();
		this.tenantRepository = tenantRepository;
		this.associarUser = associarUser;
		this.disponibilidadeSlug = disponibilidadeSlug;
	}

	@Transactional
	public Tenant save(PostTenantDTO postTenantDTO) {
		List<SaveTenantStrategy> stretegies = new ArrayList<>();
		stretegies.add(associarUser);
		stretegies.add(disponibilidadeSlug);
		Tenant tenant = postTenantDTO.toModel();
		for (SaveTenantStrategy tenantStrategy : stretegies) {
			tenantStrategy.tenantStrategy(postTenantDTO, tenant);
		}
		return tenantRepository.save(tenant);
	}
	
	public ReadTenantDTO findTenantWithProcedimentosBySlug(String slug) {
		Tenant tenant = tenantRepository.findBySlug(slug).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
		return new ReadTenantDTO(tenant);
	}
	
	public ReadTenantDTO findTenantWithProcedimentosByUserAuthenticated() {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    Tenant tenant = tenantRepository.findByUser((User) authentication.getPrincipal())
	        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
	    return new ReadTenantDTO(tenant);
	}

}
