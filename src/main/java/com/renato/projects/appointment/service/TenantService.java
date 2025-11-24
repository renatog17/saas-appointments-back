package com.renato.projects.appointment.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.renato.projects.appointment.controller.dto.tenant.PostTenantDTO;
import com.renato.projects.appointment.controller.dto.tenant.ReadTenantDTO;
import com.renato.projects.appointment.controller.dto.tenant.UpdateSlugDTO;
import com.renato.projects.appointment.domain.Tenant;
import com.renato.projects.appointment.repository.TenantRepository;
import com.renato.projects.appointment.security.domain.User;
import com.renato.projects.appointment.service.email.strategy.ConfirmacaoCadastroNovoUserTenant;
import com.renato.projects.appointment.service.strategy.tenant.save.SaveTenantStrategy;
import com.renato.projects.appointment.service.strategy.tenant.save.VerificarDisponibilidadeSlug;
import com.renato.projects.appointment.service.strategy.tenant.save.VerificarDisponibilidadeUserEmail;

@Service
public class TenantService {

	private TenantRepository tenantRepository;
	private VerificarDisponibilidadeUserEmail verificarDisponibilidadeUserEmail;
	private VerificarDisponibilidadeSlug verificarDisponibilidadeSlug;
	private ConfirmacaoCadastroNovoUserTenant confirmacaoCadastroNovoUserTenant;

	public TenantService(TenantRepository tenantRepository,
			VerificarDisponibilidadeUserEmail verificarDisponibilidadeUserEmail,
			VerificarDisponibilidadeSlug verificarDisponibilidadeSlug,
			ConfirmacaoCadastroNovoUserTenant confirmacaoCadastroNovoUserTenant) {
		super();
		this.tenantRepository = tenantRepository;
		this.verificarDisponibilidadeUserEmail = verificarDisponibilidadeUserEmail;
		this.verificarDisponibilidadeSlug = verificarDisponibilidadeSlug;
		this.confirmacaoCadastroNovoUserTenant = confirmacaoCadastroNovoUserTenant;
	}

	@Transactional
	public Tenant save(PostTenantDTO postTenantDTO) {
		//Pattern strategy para regras de negócio{
		List<SaveTenantStrategy> stretegies = new ArrayList<>();

		stretegies.add(verificarDisponibilidadeUserEmail);
		stretegies.add(verificarDisponibilidadeSlug);
		
		Tenant tenant = postTenantDTO.toModel();
		for (SaveTenantStrategy tenantStrategy : stretegies) {
			tenantStrategy.tenantStrategy(postTenantDTO, tenant);
		}
		//}Fim pattern strategy
		tenantRepository.save(tenant);
		
		confirmacaoCadastroNovoUserTenant.enviarEmail(tenant);
		
		return tenant;
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

	public Boolean existTenantBySlug(String slug) {
		return tenantRepository.existsBySlug(slug);	
	}

	@Transactional
	public void updateSlug(String slug, UpdateSlugDTO updateSlugDTO) {
		if(existTenantBySlug(updateSlugDTO.slug())) {
			throw new ResponseStatusException(
		            HttpStatus.CONFLICT,
		            "Já existe um tenant com esse slug."
		        );
		}
		tenantRepository
			.findBySlug(slug).orElseThrow(
					() -> new NoSuchElementException()).setSlug(updateSlugDTO.slug()
			);
	}

	@Transactional
	public void updateDuracaoProcedimentos(Integer duracao) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    Tenant tenant = tenantRepository.findByUser((User) authentication.getPrincipal()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));;
	    tenant.setIntervaloEmMinutos(duracao);
	}

	public List<Tenant> findTenants(){
		return tenantRepository.findAll();
	}
	
	@Transactional
	public void updateReceberAgendaDiariaPorEmailDTO(Boolean receberAgendaDiariaPorEmail) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    Tenant tenant = tenantRepository.findByUser((User) authentication.getPrincipal()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));;
	    tenant.setReceberAgendaDiariaPorEmail(receberAgendaDiariaPorEmail);
	}
}
