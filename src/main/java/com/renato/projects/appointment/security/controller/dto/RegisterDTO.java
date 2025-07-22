package com.renato.projects.appointment.security.controller.dto;

import java.util.List;

import com.renato.projects.appointment.controller.dto.procedimento.PostProcedimentoDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterDTO(
		@NotBlank (message = "Login não pode estar em branco")
		@Size(min = 11, message = "O login deve ser um número de telefone ou e-mail") 
		String login,
		@NotBlank (message = "Password não pode estar em branco")
		@Size(min = 8, message = "Passowrd deve ter ao menos 8 caracteres") 
		@Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+=<>?{}\\[\\]~.-]).+$", 
			message = "A senha deve conter ao menos uma letra maiúscula, um número e um caractere especial.")
		String password,
		String name,
		String tenant,
		List<PostProcedimentoDTO> procedimentos
		) {

}