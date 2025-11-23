package com.renato.projects.appointment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class AppointmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppointmentApplication.class, args);
	}

	 @PostConstruct
	    public void debugEnv() {
	        System.out.println("POSTGRES_PASSWORD = " + System.getenv("POSTGRES_PASSWORD"));
	    }
}
