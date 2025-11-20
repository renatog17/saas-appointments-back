package com.renato.projects.appointment.domain;

import java.util.ArrayList;
import java.util.List;

import com.renato.projects.appointment.security.domain.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
	private String coverImg;
	private String nome;
	@Column(nullable = false, unique = true)
	private String slug;
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "user_id")
	private User user;
	@OneToMany(mappedBy = "tenant", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<Procedimento> procedimentos = new ArrayList<>();
	
	@OneToMany(mappedBy = "tenant", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Disponibilidade> disponibilidades;
	
	@OneToMany(mappedBy = "tenant", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Indisponibilidade> indisponibilidades;
	
	private Integer intervaloEmMinutos;
	
	public Tenant(String nome, String slug, List<Procedimento> procedimentos, User user) {
		super();
		this.nome = nome;
		this.slug = slug.toLowerCase();
		this.procedimentos = procedimentos;
		this.user = user;
		this.intervaloEmMinutos = 60; //60 eh padrao
	}

}
