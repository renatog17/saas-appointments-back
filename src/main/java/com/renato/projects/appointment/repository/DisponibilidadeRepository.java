package com.renato.projects.appointment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.renato.projects.appointment.domain.Disponibilidade;

public interface DisponibilidadeRepository extends JpaRepository<Disponibilidade, Long>{


	List<Disponibilidade> deleteByTenantId(Long tenantId);
	
}
