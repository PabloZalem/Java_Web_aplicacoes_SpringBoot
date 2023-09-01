package com.zalempablo.javawebproject;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.zalempablo.javawebproject.principal.Principal;

@SpringBootApplication
public class JavawebprojectApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(JavawebprojectApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//Ele vai ser um método main;
		 Principal principal = new Principal();
		 principal.exibirMenu();
		 
	}

}
