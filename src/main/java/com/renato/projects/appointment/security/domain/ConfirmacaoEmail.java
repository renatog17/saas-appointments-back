package com.renato.projects.appointment.security.domain;

import java.time.LocalDateTime;
import java.util.Optional;

import com.renato.projects.appointment.utils.GerarCodigoConfirmacaoEmail;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class ConfirmacaoEmail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String codigoConfirmacaoEmail;
	private LocalDateTime timeGeracaoCodConfEmail;
	private Boolean confirmacaoEmail;
	@OneToOne(mappedBy = "confirmacaoEmail")
	private User user;

	public ConfirmacaoEmail() {
		super();
		this.gerarNovoCodigo();		
	}

	public void gerarNovoCodigo() {
		this.codigoConfirmacaoEmail = GerarCodigoConfirmacaoEmail.gerarCodigo();
		this.timeGeracaoCodConfEmail = LocalDateTime.now();
		this.confirmacaoEmail = false;
	}
	
	public Long getId() {
		return id;
	}

	public String getCodigoConfirmacaoEmail() {
		return codigoConfirmacaoEmail;
	}

	public LocalDateTime getTimeGeracaoCodConfEmail() {
		return timeGeracaoCodConfEmail;
	}

	public Boolean getConfirmacaoEmail() {
		return confirmacaoEmail;
	}

	public void setConfirmacaoEmail(Boolean confirmacaoEmail) {
		this.confirmacaoEmail = confirmacaoEmail;
	}

	public boolean isExpirado() {
		return LocalDateTime.now().isAfter(this.timeGeracaoCodConfEmail.plusMinutes(30));
	}
	//começar a rever por aqui
	public Optional<String> getCodigoValido() {
		if (isExpirado()) {
			return Optional.empty();
		}
		return Optional.of(this.codigoConfirmacaoEmail);
	}
}
