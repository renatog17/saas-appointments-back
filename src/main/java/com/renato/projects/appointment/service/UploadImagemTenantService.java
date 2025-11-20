package com.renato.projects.appointment.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.renato.projects.appointment.domain.Procedimento;
import com.renato.projects.appointment.domain.Tenant;
import com.renato.projects.appointment.repository.ProcedimentoRepository;
import com.renato.projects.appointment.repository.TenantRepository;
import com.renato.projects.appointment.security.domain.User;

@Service
public class UploadImagemTenantService {

    @Value("${upload.dir}")
    private String uploadDir;

    private final TenantRepository tenantRepository;
    private final ProcedimentoRepository procedimentoRepository;

    public UploadImagemTenantService(TenantRepository tenantRepository, ProcedimentoRepository procedimentoRepository) {
        this.tenantRepository = tenantRepository;
        this.procedimentoRepository = procedimentoRepository;
    }
    

    private String uploadImage(MultipartFile file, String subfolder) throws IOException {
        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        
        // Caminho final: /uploads/profile/arquivo.jpg OU /uploads/cover/arquivo.jpg
        Path path = Paths.get(uploadDir, subfolder, filename);

        Files.createDirectories(path.getParent());
        Files.write(path, file.getBytes());        
        return subfolder + "/" + filename;
    }

    public void uploadProfileImage(MultipartFile file) throws IOException {
        User user = null;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            user = (User) authentication.getPrincipal();
        }

        Tenant tenant = tenantRepository.findByUser(user).get();

        tenant.setImg(uploadImage(file, "profile"));
        tenantRepository.save(tenant);
    }

    public void uploadCoverImage(MultipartFile file) throws IOException {
        User user = null;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            user = (User) authentication.getPrincipal();
        }

        Tenant tenant = tenantRepository.findByUser(user).get();

        tenant.setCoverImg(uploadImage(file, "cover"));
        tenantRepository.save(tenant);
    }

	public void uploadProcedimentoImage(Long id, MultipartFile file) throws IOException {
		Procedimento procedimento = procedimentoRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
		procedimento.setImage(uploadImage(file, "procedimento/"+procedimento.getId()));
		procedimentoRepository.save(procedimento);
	}
}
