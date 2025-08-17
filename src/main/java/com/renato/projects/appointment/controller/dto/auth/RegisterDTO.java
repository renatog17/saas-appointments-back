package com.renato.projects.appointment.controller.dto.auth;

import com.renato.projects.appointment.security.domain.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterDTO(
		@Email (message = "Login não respeita formato de email")
		@NotBlank (message = "Login não pode estar em branco")
		@Size(min = 11, message = "O login deve ser um número de telefone ou e-mail") 
		String login,
		@NotBlank (message = "Password não pode estar em branco")
		@Size(min = 8, message = "Passowrd deve ter ao menos 8 caracteres") 
		@Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+=<>?{}\\[\\]~.-]).+$", 
			message = "A senha deve conter ao menos uma letra maiúscula, um número e um caractere especial.")
		String password
		
		) {

	public User toModel() {
		User user = new User(login, password);
		return user;
	}
}