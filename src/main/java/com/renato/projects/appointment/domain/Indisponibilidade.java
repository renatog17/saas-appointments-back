package com.renato.projects.appointment.domain;

import java.time.LocalDate;
import java.time.LocalTime;

import com.renato.projects.appointment.controller.dto.disponibilidade.PostIndisponibilidadeDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class Indisponibilidade {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "tenant_id")
	private Tenant tenant;
	private LocalDate data;
	private LocalTime inicio;
	private LocalTime fim;

	public Indisponibilidade(Tenant tenant, LocalDate data, LocalTime inicio, LocalTime fim) {
		super();
		this.tenant = tenant;
		this.data = data;
		this.inicio = inicio;
		this.fim = fim;
	}

	public Indisponibilidade(PostIndisponibilidadeDTO dto) {
		this.data = dto.data();
		this.inicio = dto.inicio();
		this.fim = dto.fim();
	}
}
