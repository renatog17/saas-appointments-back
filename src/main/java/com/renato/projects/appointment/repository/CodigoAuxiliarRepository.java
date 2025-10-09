package com.renato.projects.appointment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.renato.projects.appointment.security.domain.CodigoAuxiliar;

public interface CodigoAuxiliarRepository extends JpaRepository<CodigoAuxiliar, Long>{

	CodigoAuxiliar findByUser_Login(String login);

}
