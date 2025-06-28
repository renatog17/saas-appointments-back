package com.renato.projects.appointment.service.strategy;

import com.renato.projects.appointment.controller.dto.PostTenantDTO;
import com.renato.projects.appointment.domain.Tenant;

public interface TenantStrategy {

	public void tenantStrategy(PostTenantDTO postTenantDTO, Tenant tenant);
}
