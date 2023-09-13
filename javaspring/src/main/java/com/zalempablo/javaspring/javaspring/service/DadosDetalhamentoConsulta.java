package com.zalempablo.javaspring.javaspring.service;

import java.time.LocalDateTime;

import com.zalempablo.javaspring.javaspring.entities.Consulta;

public record DadosDetalhamentoConsulta(Long id,
										Long idMedico,
										Long idPaciente,
										LocalDateTime data) {

	public DadosDetalhamentoConsulta(Consulta consulta) {
		this(consulta.getId(), consulta.getMedicos().getId(), consulta.getPaciente().getId(), consulta.getData());
	}

}
