package com.renato.projects.appointment.security.controller.dto;

import com.renato.projects.appointment.security.domain.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {

}