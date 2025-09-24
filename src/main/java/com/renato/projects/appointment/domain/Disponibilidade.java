package com.renato.projects.appointment.domain;

import java.time.LocalTime;

import com.renato.projects.appointment.controller.dto.indisponibilidade.PostDisponibilidadeDTO;

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
public class Disponibilidade {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "procedimento_id")
	private Procedimento procedimento;
	private Integer diaDaSemana;
	private LocalTime inicio;
	private LocalTime fim;
	
	public Disponibilidade(Procedimento procedimento, Integer diaDaSemana, LocalTime inicio, LocalTime fim) {
		super();
		this.procedimento = procedimento;
		this.diaDaSemana = diaDaSemana;
		this.inicio = inicio;
		this.fim = fim;
	}
	
	public Disponibilidade(PostDisponibilidadeDTO disponibilidadeDTO) {
		this.diaDaSemana = disponibilidadeDTO.dia();
		this.inicio = disponibilidadeDTO.inicio();
		this.fim = disponibilidadeDTO.fim();
	}
	
	
}
