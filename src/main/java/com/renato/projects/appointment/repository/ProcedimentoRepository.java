package com.renato.projects.appointment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.renato.projects.appointment.domain.Procedimento;

public interface ProcedimentoRepository extends JpaRepository<Procedimento, Long>{

}
