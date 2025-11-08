package com.renato.projects.appointment.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.renato.projects.appointment.service.UploadImagemTenantService;

//@RequestMapping("/image/tenant")
@RequestMapping("/image/")
@RestController
public class UploadImagemController {

	private UploadImagemTenantService uploadImagemTenantService;

	public UploadImagemController(UploadImagemTenantService uploadImagemTenantService) {
		super();
		this.uploadImagemTenantService = uploadImagemTenantService;
	}

	@PostMapping("/profile")
	public ResponseEntity<?> postTenantProfileImage(@RequestParam("file") MultipartFile file) throws IOException {
		uploadImagemTenantService.uploadProfileImage(file);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping("/cover")
	public ResponseEntity<?> postTenantCoverImage(@RequestParam("file") MultipartFile file) throws IOException {
		uploadImagemTenantService.uploadCoverImage(file);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping("/procedimento/{id}")
	public ResponseEntity<?> postProcedimentoImage(@RequestParam("file") MultipartFile file, 
			@PathVariable Long id) throws IOException{
		uploadImagemTenantService.uploadProcedimentoImage(id, file);
		return ResponseEntity.noContent().build();
	}
}
