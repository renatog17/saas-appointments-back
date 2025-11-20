package com.renato.projects.appointment.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.renato.projects.appointment.domain.Agendamento;
import com.renato.projects.appointment.domain.Procedimento;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

	List<Agendamento> findByProcedimento_Tenant_IdAndDateTimeAfter(Long tenantId, LocalDateTime now);

	List<Agendamento> findByProcedimentoAndDateTimeBetween(Procedimento procedimento, LocalDateTime inicio,
			LocalDateTime fim);

	Optional<Agendamento> findByDateTime(LocalDateTime dateTime);
	
	@Query("""
		    SELECT a FROM Agendamento a
		    WHERE a.dateTime <= :fim
		      AND a.dateTimeTermino >= :inicio
		""")
		List<Agendamento> findConflitos(
		        @Param("inicio") LocalDateTime inicio,
		        @Param("fim") LocalDateTime fim
		);

}
