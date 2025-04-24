package com.example.log;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LogApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogApplication.class, args);
	}

	@PostConstruct
	public void logStartupMessage() {
		System.out.println("🚀 Log Service is running");
	}
}
