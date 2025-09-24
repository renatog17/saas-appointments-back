package com.renato.projects.appointment.controller.dto.procedimento;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import com.renato.projects.appointment.controller.dto.indisponibilidade.PostDisponibilidadeDTO;
import com.renato.projects.appointment.domain.Disponibilidade;
import com.renato.projects.appointment.domain.Procedimento;

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
		String nome,

		List<PostDisponibilidadeDTO> disponibilidades

) {

	public Procedimento toModel() {
		Procedimento procedimento = new Procedimento(this);

		List<Disponibilidade> disponibilidadesModel = disponibilidades.stream()
				 .map(disponibilidadeDto -> {
		                Disponibilidade d = disponibilidadeDto.toModel();
		                d.setProcedimento(procedimento); // << aqui está a referência
		                return d;
		            })
				
				.collect(Collectors.toList());
		
		
		procedimento.setDisponibilidades(disponibilidadesModel);
		
		return procedimento;
	}
}
