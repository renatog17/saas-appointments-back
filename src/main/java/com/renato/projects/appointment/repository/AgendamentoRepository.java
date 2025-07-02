package com.renato.projects.appointment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.renato.projects.appointment.domain.Agendamento;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long>{

}
