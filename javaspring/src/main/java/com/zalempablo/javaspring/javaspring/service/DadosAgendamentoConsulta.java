package com.zalempablo.javaspring.javaspring.service;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

public record DadosAgendamentoConsulta(	Long id,
										@NotNull Long idPaciente,
										@NotNull Long idMedico,
										@NotNull @Future LocalDateTime data,
										Especialidade especialidade) {

}
