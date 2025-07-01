package com.renato.projects.appointment.controller.dto.tenant;

import com.renato.projects.appointment.domain.Tenant;

public record PostTenantDTO(String nome, String slug) {

	public Tenant toModel() {
		return new Tenant(nome, slug);
	}

}
