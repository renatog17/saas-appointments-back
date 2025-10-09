package com.renato.projects.appointment.service;

import org.springframework.stereotype.Service;

import com.renato.projects.appointment.repository.CodigoAuxiliarRepository;
import com.renato.projects.appointment.security.domain.CodigoAuxiliar;

@Service
public class CodigoAuxiliarService {

	private CodigoAuxiliarRepository codigoAuxiliarRepository;

	public CodigoAuxiliarService(CodigoAuxiliarRepository codigoAuxiliarRepository) {
		super();
		this.codigoAuxiliarRepository = codigoAuxiliarRepository;
	}

	public void gerarNovoCodigoAuxiliar(String login) {
		CodigoAuxiliar codigoAuxiliar = codigoAuxiliarRepository.findByUser_Login(login);
		codigoAuxiliar.gerarNovoCodigo();
	}
}
