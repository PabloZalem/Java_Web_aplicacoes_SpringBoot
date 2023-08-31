package com.zalempablo.javawebproject;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.zalempablo.javawebproject.service.ConsumoApi;

@SpringBootApplication
public class JavawebprojectApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(JavawebprojectApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//Ele vai ser um método main;
		ConsumoApi c1 = new ConsumoApi();
		var json = c1.obterDados("https://www.omdbapi.com/?t=gilmore+girls&apikey=6585022c");
		System.out.println(json);
	}

}
