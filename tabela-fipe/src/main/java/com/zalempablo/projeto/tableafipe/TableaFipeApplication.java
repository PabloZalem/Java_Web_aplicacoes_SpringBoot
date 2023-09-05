package com.zalempablo.projeto.tableafipe;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TableaFipeApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(TableaFipeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.exibirMenu();
		
	}

}
