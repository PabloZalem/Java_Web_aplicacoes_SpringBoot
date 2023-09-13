package com.zalempablo.javaspring.javaspring.validacoes;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.zalempablo.javaspring.javaspring.exception.ValidacaoException;
import com.zalempablo.javaspring.javaspring.service.DadosAgendamentoConsulta;

@Component
public class ValidadorDeHorarioAntecedencia implements ValidadorAgendamentoConsulta{
	
	public void validar(DadosAgendamentoConsulta dadosAgendamentoConsulta) {
		var dataConsulta = dadosAgendamentoConsulta.data();
		var agora = LocalDateTime.now();
		var diferencaDeMinutos = java.time.Duration.between(agora, dataConsulta).toMinutes();
		
		if(diferencaDeMinutos < 30) {
			throw new ValidacaoException("Consulta deve set agendada com antecedencia de 30 minutos");
		}
	}
}
