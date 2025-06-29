package com.renato.projects.appointment.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.renato.projects.appointment.controller.dto.PostProcedimentoDTO;
import com.renato.projects.appointment.domain.Procedimento;
import com.renato.projects.appointment.domain.Tenant;
import com.renato.projects.appointment.repository.ProcedimentoRepository;
import com.renato.projects.appointment.repository.TenantRepository;
import com.renato.projects.appointment.security.domain.User;

@Service
public class ProcedimentoService {

	private ProcedimentoRepository procedimentoRepository;
	private TenantRepository tenantRepository;

	public ProcedimentoService(ProcedimentoRepository procedimentoRepository, TenantRepository tenantRepository) {
		super();
		this.procedimentoRepository = procedimentoRepository;
		this.tenantRepository = tenantRepository;
	}

	public void salvarProcedimentos(List<PostProcedimentoDTO> procedimentosDTO) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		Tenant tenant = tenantRepository.findByUser(user).get();
		
		List<Procedimento> procedimentos = procedimentosDTO.stream().map(dto -> {
			return new Procedimento(dto, tenant);
		}).collect(Collectors.toList());

		procedimentoRepository.saveAll(procedimentos);
	}

}
