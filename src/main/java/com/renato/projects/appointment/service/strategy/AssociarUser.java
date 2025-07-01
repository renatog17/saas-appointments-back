package com.renato.projects.appointment.service.strategy;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.renato.projects.appointment.controller.dto.tenant.PostTenantDTO;
import com.renato.projects.appointment.domain.Tenant;
import com.renato.projects.appointment.security.domain.User;

@Component
public class AssociarUser implements TenantStrategy{

	@Override
	public void tenantStrategy(PostTenantDTO postTenantDTO, Tenant tenant) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
		    User user = (User) authentication.getPrincipal();
		    tenant.setUser(user);
		}
	}

}
