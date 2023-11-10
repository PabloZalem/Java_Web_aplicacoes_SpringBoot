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

/*
	Responsabilidade Única (SRP):
	O ValidadorDeHorarioAntecedencia parece ter uma única responsabilidade, 
	que é validar se a consulta foi agendada com antecedência de 30 minutos. 
	Isso está alinhado com o princípio da SRP, que sugere que uma classe deve 
	ter apenas um motivo para mudar.

	Inversão de Dependência (DIP):
	O uso de interfaces, como ValidadorAgendamentoConsulta, sugere a aplicação 
	do princípio da inversão de dependência. A classe ValidadorDeHorarioAntecedencia 
	depende de uma abstração (ValidadorAgendamentoConsulta), e não de implementações 
	concretas. Isso facilita a extensibilidade do sistema, pois novos validadores 
	podem ser adicionados sem modificar a classe que os utiliza.
*/