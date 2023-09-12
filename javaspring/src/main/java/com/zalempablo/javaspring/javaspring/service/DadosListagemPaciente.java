package com.zalempablo.javaspring.javaspring.service;

import com.zalempablo.javaspring.javaspring.entities.Paciente;

public record DadosListagemPaciente(Long id, String nome, String email, String cpf) {

	public DadosListagemPaciente(Paciente paciente) {
		this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getCpf());
	}

}
