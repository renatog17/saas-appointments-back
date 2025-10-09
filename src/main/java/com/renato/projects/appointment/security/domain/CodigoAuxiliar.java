package com.renato.projects.appointment.security.domain;

import java.time.LocalDateTime;

import com.renato.projects.appointment.controller.exceptionhandler.exception.CodigoAuxiliarExpiradoException;
import com.renato.projects.appointment.utils.GerarCodigoConfirmacaoEmail;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CodigoAuxiliar {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	private String codigo;
	private LocalDateTime timeGeracaoCod;
	@OneToOne(mappedBy = "codigoAuxiliar")
	private User user;

	public CodigoAuxiliar() {
		super();
		this.gerarNovoCodigo();		
	}

	public void gerarNovoCodigo() {
		this.codigo = GerarCodigoConfirmacaoEmail.gerarCodigo();
		this.timeGeracaoCod = LocalDateTime.now();
	}
	
	public Long getId() {
		return id;
	}

	public boolean isExpirado() {
		return LocalDateTime.now().isAfter(this.timeGeracaoCod.plusMinutes(30));
	}
	
	public String getCodigo() {
		if (isExpirado()) {
			throw new CodigoAuxiliarExpiradoException();
		}
		return this.codigo;
	}
}
