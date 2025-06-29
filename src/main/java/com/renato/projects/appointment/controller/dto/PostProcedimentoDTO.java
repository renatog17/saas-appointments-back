package com.renato.projects.appointment.controller.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PostProcedimentoDTO(
		@NotNull(message = "O valor não pode ser nulo")
	    @DecimalMin(value = "0.0", inclusive = false, message = "O valor deve ser maior que zero")
	    BigDecimal valor,

	    @NotBlank(message = "A descrição é obrigatória")
	    @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres")
	    String descricao,

	    @NotBlank(message = "O nome é obrigatório")
	    @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
	    String nome
		) {

}
