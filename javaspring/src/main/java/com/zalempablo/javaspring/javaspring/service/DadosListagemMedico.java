package com.zalempablo.javaspring.javaspring.service;

import com.zalempablo.javaspring.javaspring.entities.Medicos;

public record DadosListagemMedico(	Long id,
									String nome,
									String email,
									String crm,
									Especialidade especialidade
								) {
	
	public DadosListagemMedico(Medicos medicos) {
		this(medicos.getId(), medicos.getNome(), medicos.getEmail(), medicos.getCrm(), medicos.getEspecialidade());
	}
}
