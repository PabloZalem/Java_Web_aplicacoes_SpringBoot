package com.zalempablo.javaspring.javaspring.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DadosCadastroPaciente(
        @NotBlank
        String nome,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String telefone,
        @NotBlank
        @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}")
        String cpf,
        @NotNull @Valid DadosEndereco endereco) {
	
	
	
	 public static DadosCadastroPaciente criar(String nome, String email, String telefone, String cpf, DadosEndereco endereco) {
	        return new DadosCadastroPaciente(nome, email, telefone, cpf, endereco);
	    }
}
