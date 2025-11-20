package com.renato.projects.appointment.controller.dto.tenant;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UpdateDuracaoDTO(
		@NotNull(message = "A duração é obrigatória.")
		@Min(value = 1, message = "A duração deve ser maior que zero.")
		Integer duracao) {

}
