package com.renato.projects.appointment.security.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

	@Value("${cors.allowed-origin}")
    private String[] allowedOrigin;
	
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                    //.allowedOrigins(allowedOrigin)
                .allowedOrigins("http://www.zendaavip.com.br", "http://zendaavip.com.br", "https://www.zendaavip.com.br", "https://zendaavip.com.br")
                
                    //verificar se utilizando o localhost:3000, funciona em produção tbm
                	//.allowedOrigins("http://localhost:3000")
                    .allowedMethods("PATCH","GET", "POST", "PUT", "DELETE", "OPTIONS")
                    .allowedHeaders("*")
                    .allowCredentials(true);
            }
        };
    }
}
