package com.renato.projects.appointment.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.renato.projects.appointment.controller.dto.procedimento.PostProcedimentoDTO;
import com.renato.projects.appointment.domain.Procedimento;
import com.renato.projects.appointment.domain.Tenant;
import com.renato.projects.appointment.repository.ProcedimentoRepository;
import com.renato.projects.appointment.repository.TenantRepository;
import com.renato.projects.appointment.security.domain.User;

import jakarta.transaction.Transactional;

@Service
public class ProcedimentoService {

	private ProcedimentoRepository procedimentoRepository;
	private TenantRepository tenantRepository;

	public ProcedimentoService(ProcedimentoRepository procedimentoRepository, TenantRepository tenantRepository) {
		super();
		this.procedimentoRepository = procedimentoRepository;
		this.tenantRepository = tenantRepository;
	}

	@Transactional
	public void salvarProcedimentos(List<PostProcedimentoDTO> procedimentosDTO) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		Tenant tenant = tenantRepository.findByUser(user).get();
		
		List<Procedimento> procedimentos = procedimentosDTO.stream().map(dto -> {
			return new Procedimento(dto, tenant);
		}).collect(Collectors.toList());

		procedimentoRepository.saveAll(procedimentos);
	}

	@Transactional
	public void arquivarProcedimento(Long id) {
		Optional<Procedimento> procedimento = procedimentoRepository.findById(id);
		if(procedimento.isPresent())
			procedimento.get().setArquivado(true);
	}
	
	@Transactional
	public void deletarProcedimento(Long id) {
		procedimentoRepository.deleteById(id);
	}

}
