package com.renato.projects.appointment.domain;

import java.util.ArrayList;
import java.util.List;

import com.renato.projects.appointment.utils.TitleCapitalization;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Consumidor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	private String telefone;
	private String nome;
	private String email;
	@OneToMany(mappedBy = "consumidor")
	private List<Agendamento> agendamentos = new ArrayList<>();
	
	public Consumidor(String email, String nome) {
		super();
		this.email = email.toLowerCase();
		this.nome = TitleCapitalization.titleCapitalization(nome);
	}
	
	public void setNome(String nome) {
		this.nome = TitleCapitalization.titleCapitalization(nome);
	}

	public void setEmail(String email) {
		this.email = email.toLowerCase();
	}
	
}
