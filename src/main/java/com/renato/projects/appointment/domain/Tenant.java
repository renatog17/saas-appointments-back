package com.renato.projects.appointment.domain;

import java.util.ArrayList;
import java.util.List;

import com.renato.projects.appointment.security.domain.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
public class Tenant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	private String img;
	private String nome;
	private String slug;
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;
	@OneToMany(mappedBy = "tenant")
	private List<Procedimento> procedimentos = new ArrayList<>();
	
	public Tenant(String nome, String slug, List<Procedimento> procedimentos, User user) {
		super();
		this.nome = nome;
		this.slug = slug;
		this.procedimentos = procedimentos;
		this.user = user;
	}

}
