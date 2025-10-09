package com.renato.projects.appointment.service;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.renato.projects.appointment.controller.dto.disponibilidade.PostDisponibilidadeDTO;
import com.renato.projects.appointment.domain.Disponibilidade;
import com.renato.projects.appointment.domain.Tenant;
import com.renato.projects.appointment.repository.DisponibilidadeRepository;
import com.renato.projects.appointment.repository.TenantRepository;
import com.renato.projects.appointment.security.domain.User;

import jakarta.transaction.Transactional;

@Service
public class DisponibilidadeService {

	private DisponibilidadeRepository disponibilidadeRepository;
	private TenantRepository tenantRepository;

	public DisponibilidadeService(DisponibilidadeRepository disponibilidadeRepository,
			TenantRepository tenantRepository) {
		super();
		this.disponibilidadeRepository = disponibilidadeRepository;
		this.tenantRepository = tenantRepository;
	}

	@Transactional
	public void atualizarIntervalos(List<PostDisponibilidadeDTO> disponibilidadesDTO) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		Tenant tenant = tenantRepository.findByUser(user).get();

		disponibilidadeRepository.deleteByTenantId(tenant.getId());

		List<Disponibilidade> disponibilidades = 
				disponibilidadesDTO
				.stream()
				.map(PostDisponibilidadeDTO::toModel)
				.toList();
		
	    disponibilidades.forEach(d -> d.setTenant(tenant));

	    disponibilidadeRepository.saveAll(disponibilidades);
		

	}

}
