package com.example.mail;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MailApplication {

	public static void main(String[] args) {
		SpringApplication.run(MailApplication.class, args);
	}

	@PostConstruct
	public void logStartupMessage() {
		System.out.println("🚀 Mail Service is running");
	}
}
