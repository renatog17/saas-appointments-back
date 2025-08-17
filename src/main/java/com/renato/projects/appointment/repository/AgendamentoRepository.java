package com.renato.projects.appointment.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.renato.projects.appointment.domain.Agendamento;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long>{

	List<Agendamento> findByProcedimento_Tenant_IdAndDateTimeAfter(Long tenantId, LocalDateTime now);

	Optional<Agendamento> findByDateTime(LocalDateTime dateTime);
}
