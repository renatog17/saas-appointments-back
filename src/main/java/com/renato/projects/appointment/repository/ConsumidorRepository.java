package com.renato.projects.appointment.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.renato.projects.appointment.domain.Consumidor;

public interface ConsumidorRepository extends JpaRepository<Consumidor, Long>{

	Consumidor findByTelefone(String telefone);
	Optional<Consumidor> findByEmail(String email);
}
