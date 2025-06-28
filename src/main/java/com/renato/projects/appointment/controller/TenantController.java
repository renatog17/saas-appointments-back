package com.renato.projects.appointment.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.renato.projects.appointment.controller.dto.PostTenantDTO;
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
}
