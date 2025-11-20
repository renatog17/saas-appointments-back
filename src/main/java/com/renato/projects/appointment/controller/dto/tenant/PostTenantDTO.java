package com.renato.projects.appointment.controller.dto.tenant;

import java.util.List;
import java.util.stream.Collectors;

import com.renato.projects.appointment.controller.dto.auth.RegisterDTO;
import com.renato.projects.appointment.controller.dto.disponibilidade.PostDisponibilidadeDTO;
import com.renato.projects.appointment.controller.dto.procedimento.PostProcedimentoDTO;
import com.renato.projects.appointment.domain.Disponibilidade;
import com.renato.projects.appointment.domain.Procedimento;
import com.renato.projects.appointment.domain.Tenant;
import com.renato.projects.appointment.security.domain.User;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record PostTenantDTO(
		@NotEmpty(message = "O nome não pode estar vazio")
		String nome,
		@NotEmpty(message = "O slug não pode estar vazio")
		@Pattern(
			    regexp = "^[a-z0-9-]+$",
			    message = "O slug só pode conter letras minúsculas, números e traços"
			)
		String slug,
		List<PostProcedimentoDTO> procedimentos,
		RegisterDTO register,
		List<PostDisponibilidadeDTO> disponibilidades
		) {

	public Tenant toModel() {
		Tenant tenant = new Tenant();
		tenant.setNome(nome);
		tenant.setSlug(slug);
		tenant.setIntervaloEmMinutos(60);
		
		User user = new User(register.login(), register.password());
		tenant.setUser(user);
		
		List<Procedimento> procedimentosModel = procedimentos
				.stream()
				.map(dto -> {
					Procedimento p = dto.toModel();
					p.setTenant(tenant);
					return p;
				} )
				.collect(Collectors.toList());
		tenant.setProcedimentos(procedimentosModel);
		
		List<Disponibilidade> disponibilidadesModel = disponibilidades.stream()
				 .map(disponibilidadeDto -> {
		                Disponibilidade d = disponibilidadeDto.toModel();
		                d.setTenant(tenant);
		                return d;
		            })
				
				.collect(Collectors.toList());
		tenant.setDisponibilidades(disponibilidadesModel);
		
		return tenant;
	}

}
