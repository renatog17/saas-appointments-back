package com.renato.projects.appointment.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.renato.projects.appointment.controller.dto.PostTenantDTO;
import com.renato.projects.appointment.domain.Tenant;
import com.renato.projects.appointment.repository.TenantRepository;
import com.renato.projects.appointment.service.strategy.AssociarUser;
import com.renato.projects.appointment.service.strategy.TenantStrategy;
import com.renato.projects.appointment.service.strategy.VerificarDisponibilidadeSlug;

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
}
