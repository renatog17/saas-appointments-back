package com.renato.projects.appointment.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.renato.projects.appointment.controller.dto.tenant.PostTenantDTO;
import com.renato.projects.appointment.controller.dto.tenant.ReadTenantDTO;
import com.renato.projects.appointment.domain.Tenant;
import com.renato.projects.appointment.repository.TenantRepository;
import com.renato.projects.appointment.service.strategy.AssociarUser;
import com.renato.projects.appointment.service.strategy.TenantStrategy;
import com.renato.projects.appointment.service.strategy.VerificarDisponibilidadeSlug;

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
		List<TenantStrategy> stretegies = new ArrayList<>();
		stretegies.add(associarUser);
		stretegies.add(disponibilidadeSlug);
		
		Tenant tenant = postTenantDTO.toModel();
		
		for (TenantStrategy tenantStrategy : stretegies) {
			tenantStrategy.tenantStrategy(postTenantDTO, tenant);
		}
		
		return tenantRepository.save(tenant);
	}
	
	public ReadTenantDTO findTenantWithProcedimentosBySlug(String slug) {
		Tenant tenant = tenantRepository.findBySlug(slug).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
		
		
		return new ReadTenantDTO(tenant);
	}
}
