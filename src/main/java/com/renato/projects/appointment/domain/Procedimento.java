package com.renato.projects.appointment.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.renato.projects.appointment.controller.dto.procedimento.PostProcedimentoDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Procedimento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	private BigDecimal valor;
	private String descricao;
	private String nome;
	private Boolean habilitado;
	@ManyToOne()
	@JoinColumn(name = "tenant_id")
	private Tenant tenant;
	@OneToMany(mappedBy = "procedimento")
	private List<Agendamento> agendamentos = new ArrayList<>();
	
	public Procedimento(PostProcedimentoDTO dto, Tenant tenant) {
		this.nome = dto.nome();
		this.descricao = dto.descricao();
		this.valor = dto.valor();
		this.tenant = tenant;
		this.habilitado = false;
	}
	public Procedimento(PostProcedimentoDTO dto) {
		this.nome = dto.nome();
		this.descricao = dto.descricao();
		this.valor = dto.valor();
		this.habilitado = false;
	}

	public Procedimento(BigDecimal valor, String descricao, String nome) {
		super();
		this.valor = valor;
		this.descricao = descricao;
		this.nome = nome;
		this.habilitado = false;
	}
	
	
}
