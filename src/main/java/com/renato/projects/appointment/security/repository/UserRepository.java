package com.renato.projects.appointment.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.renato.projects.appointment.security.domain.User;

public interface UserRepository extends JpaRepository<User, Long>{

	//UserDetails findByLogin(String login);
	Optional<UserDetails> findByLogin(String login);

	Boolean existsByLogin(String email);
}