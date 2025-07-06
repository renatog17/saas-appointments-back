package com.renato.projects.appointment.service.strategy.tenant.save;

import com.renato.projects.appointment.controller.dto.tenant.PostTenantDTO;
import com.renato.projects.appointment.domain.Tenant;

public interface SaveTenantStrategy {

	public void tenantStrategy(PostTenantDTO postTenantDTO, Tenant tenant);
}
