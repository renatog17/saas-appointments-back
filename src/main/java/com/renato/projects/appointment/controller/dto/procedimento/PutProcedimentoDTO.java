package com.renato.projects.appointment.controller.dto.procedimento;

import java.math.BigDecimal;

public record PutProcedimentoDTO(
		BigDecimal valor,
		String descricao,
		String nome) {
}
