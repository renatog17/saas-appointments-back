package com.renato.projects.appointment.controller.dto.procedimento;

import java.math.BigDecimal;

import com.renato.projects.appointment.domain.Procedimento;

public record ReadProcedimentoDTO(
		Long id, String nome, String descricao, BigDecimal valor) {

	public ReadProcedimentoDTO(Procedimento procedimento) {
		this(procedimento.getId(), procedimento.getNome(), procedimento.getDescricao(), procedimento.getValor());
	}
}
