package com.renato.projects.appointment.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.renato.projects.appointment.controller.dto.tenant.PostTenantDTO;
import com.renato.projects.appointment.controller.dto.tenant.UpdateSlugDTO;
import com.renato.projects.appointment.domain.Tenant;
import com.renato.projects.appointment.service.TenantService;

@RestController
@RequestMapping("/tenant")
public class TenantController {

	private TenantService tenantService;

	public TenantController(TenantService tenantService) {
		super();
		this.tenantService = tenantService;
	}

	@PostMapping
	public ResponseEntity<?> postTenant(@RequestBody PostTenantDTO postTenantDTO, UriComponentsBuilder uriComponentsBuilder) {
		Tenant tenant = tenantService.save(postTenantDTO);
		URI uri = uriComponentsBuilder.path("/tenant/{id}").buildAndExpand(tenant.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	
	
	@GetMapping("/slug/{slug}")
	public ResponseEntity<?> existTenantBySlug(@PathVariable String slug) {
		return ResponseEntity.ok(tenantService.existTenantBySlug(slug));
	}
	
	@GetMapping("/{slug}")
	public ResponseEntity<?> getTenant(@PathVariable String slug){
		return ResponseEntity.ok(tenantService.findTenantWithProcedimentosBySlug(slug));
	}
	
	@GetMapping()
	public ResponseEntity<?> getTenant(){
		return ResponseEntity.ok(tenantService.findTenantWithProcedimentosByUserAuthenticated());
	}
	
	@PostMapping("/edit/{slug}")
	public ResponseEntity<?> updateSlug(@RequestBody UpdateSlugDTO updateSlugDTO, @PathVariable String slug){
		tenantService.updateSlug(slug, updateSlugDTO);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping()
	public ResponseEntity<?> updateDuracacaoProcedimentos(@RequestBody Integer duracao){
		tenantService.updateDuracaoProcedimentos(duracao);
		return ResponseEntity.ok().build();
	}
}
