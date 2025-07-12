package com.renato.projects.appointment.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.renato.projects.appointment.service.UploadImagemTenantService;

@RequestMapping("/image/tenant")
@RestController
public class UploadImagemTenantController {

	private UploadImagemTenantService uploadImagemTenantService;

	public UploadImagemTenantController(UploadImagemTenantService uploadImagemTenantService) {
		super();
		this.uploadImagemTenantService = uploadImagemTenantService;
	}

	@PostMapping
	public ResponseEntity<?> postTenantImagem(@RequestParam("file") MultipartFile file) throws IOException {
		uploadImagemTenantService.uploadImage(file);
		return ResponseEntity.noContent().build();
	

	}
}
