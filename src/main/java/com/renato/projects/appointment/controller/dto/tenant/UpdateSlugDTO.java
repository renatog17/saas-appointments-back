package com.renato.projects.appointment.controller.dto.tenant;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record UpdateSlugDTO(
		@NotEmpty(message = "O slug não pode estar vazio") 
		@Pattern(regexp = "^[a-z0-9-]+$", message = "O slug só pode conter letras minúsculas, números e traços") 
		String slug
		) {

}
