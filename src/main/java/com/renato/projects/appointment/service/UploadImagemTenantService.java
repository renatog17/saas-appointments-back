package com.renato.projects.appointment.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.renato.projects.appointment.domain.Tenant;
import com.renato.projects.appointment.repository.TenantRepository;
import com.renato.projects.appointment.security.domain.User;

@Service
public class UploadImagemTenantService {
	@Value("${upload.dir}")
	private String uploadDir;
	private TenantRepository tenantRepository;

	public UploadImagemTenantService(TenantRepository tenantRepository) {
		super();
		this.tenantRepository = tenantRepository;
	}

	public void uploadImage(MultipartFile file) throws IOException {
		String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
		Path path = Paths.get(uploadDir, filename);
		Files.createDirectories(path.getParent());
		Files.write(path, file.getBytes());

		User user = null;

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			user = (User) authentication.getPrincipal();
		}

		Tenant tenant = tenantRepository.findByUser(user).get();

		tenant.setImg(filename);
		tenantRepository.save(tenant);
	}

}
