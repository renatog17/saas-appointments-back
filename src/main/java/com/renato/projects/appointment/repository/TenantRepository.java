package com.renato.projects.appointment.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.renato.projects.appointment.domain.Tenant;

public interface TenantRepository extends JpaRepository<Tenant, Long>{

    Optional<Tenant> findBySlug(String slug);
}
