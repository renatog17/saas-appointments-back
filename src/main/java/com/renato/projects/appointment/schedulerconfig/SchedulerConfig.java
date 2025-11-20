package com.renato.projects.appointment.schedulerconfig;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.renato.projects.appointment.domain.Tenant;
import com.renato.projects.appointment.service.TenantService;
import com.renato.projects.appointment.service.email.strategy.EnvioAgendaDiaria;

@Configuration
@EnableScheduling
public class SchedulerConfig {

	private final EnvioAgendaDiaria envioAgendaDiaria;
	private final TenantService tenantService;
	
	public SchedulerConfig(EnvioAgendaDiaria envioAgendaDiaria, TenantService tenantService) {
		super();
		this.envioAgendaDiaria = envioAgendaDiaria;
		this.tenantService = tenantService;
	}

	@Scheduled(cron = "0 5 0 * * *", zone = "America/Sao_Paulo")
	//mudar isso para as properties
	public void enviarEmailDiario() {
		System.out.println("aqui");
		List<Tenant> tenants = tenantService.findTenants();
		System.out.println(tenants.size());
		for (Tenant tenant : tenants) {
			System.out.println("aqui dentro do for each");
			envioAgendaDiaria.enviarEmail(tenant);
		}
	}
}
