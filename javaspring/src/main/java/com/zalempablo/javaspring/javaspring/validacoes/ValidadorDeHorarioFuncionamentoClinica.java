package com.zalempablo.javaspring.javaspring.validacoes;

import java.time.DayOfWeek;

import org.springframework.stereotype.Component;

import com.zalempablo.javaspring.javaspring.exception.ValidacaoException;
import com.zalempablo.javaspring.javaspring.service.DadosAgendamentoConsulta;

@Component
public class ValidadorDeHorarioFuncionamentoClinica implements ValidadorAgendamentoConsulta{
	
	public void validar(DadosAgendamentoConsulta dadosAgendamentoConsulta) {
		var dataConsulta = dadosAgendamentoConsulta.data();
		var domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
		var antesDaAbertura = dataConsulta.getHour() < 7;
		var depoisDoEncerramento = dataConsulta.getHour() > 18;
		
		if (domingo || antesDaAbertura || depoisDoEncerramento) {
			throw new ValidacaoException("Consulta fora do funcionamento");
		}
	}
}

/*
	Responsabilidade Única (SRP):
	A classe parece ter uma única responsabilidade, que é validar se a 
	consulta está dentro do horário de funcionamento da clínica. Essa responsabilidade 
	única está alinhada com o princípio da SRP.

	Inversão de Dependência (DIP):
	Assim como na classe anterior, esta classe implementa a interface 
	ValidadorAgendamentoConsulta, sugerindo que ela depende de abstrações em vez de 
	implementações concretas. Isso promove a flexibilidade e a extensibilidade do 
	sistema.
*/