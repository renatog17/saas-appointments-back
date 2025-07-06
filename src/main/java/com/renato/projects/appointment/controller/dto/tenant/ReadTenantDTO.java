package com.renato.projects.appointment.controller.dto.tenant;

import java.util.List;

import com.renato.projects.appointment.controller.dto.procedimento.ReadProcedimentoDTO;
import com.renato.projects.appointment.domain.Tenant;

public record ReadTenantDTO(String nome, String slug, List<ReadProcedimentoDTO> procedimentos) {

	public ReadTenantDTO(Tenant tenant) {
	    this(
	        tenant.getNome(),
	        tenant.getSlug(),
	        tenant.getProcedimentos().stream()
	            .filter(p -> !Boolean.TRUE.equals(p.getArquivado())) 
	            .map(ReadProcedimentoDTO::new)
	            .toList()
	    );
	}


}
