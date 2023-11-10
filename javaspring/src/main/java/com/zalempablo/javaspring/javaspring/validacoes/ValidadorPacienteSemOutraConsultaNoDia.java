package com.zalempablo.javaspring.javaspring.validacoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zalempablo.javaspring.javaspring.exception.ValidacaoException;
import com.zalempablo.javaspring.javaspring.repository.ConsultaRepository;
import com.zalempablo.javaspring.javaspring.service.DadosAgendamentoConsulta;

@Component
public class ValidadorPacienteSemOutraConsultaNoDia implements ValidadorAgendamentoConsulta{
	@Autowired
	private ConsultaRepository consultaRepository;
	
	public ValidadorPacienteSemOutraConsultaNoDia(ConsultaRepository consultaRepository2) {
		this.consultaRepository = consultaRepository2;
	}

	public void validar(DadosAgendamentoConsulta dadosAgendamentoConsulta) {
		var primeiroHorario = dadosAgendamentoConsulta.data().withHour(7);
		var ultimoHorario = dadosAgendamentoConsulta.data().withHour(18);
		var pacientePossuiOutraConsultaNoDia = consultaRepository.existsByPacienteIdAndDataBetween(
				dadosAgendamentoConsulta.idPaciente(), primeiroHorario, ultimoHorario);
		if(pacientePossuiOutraConsultaNoDia) {
			throw new ValidacaoException("Paciente ja possui uma consulta agendada nesse dia");
		}
	}
}

/*
Inversão de Dependência (DIP):
	A classe utiliza injeção de dependência no construtor ao receber uma 
	instância de ConsultaRepository. Isso está de acordo com o princípio da 
	inversão de dependência, pois a classe depende de uma abstração 
	(ConsultaRepository) em vez de criar diretamente a dependência.

Responsabilidade Única (SRP):
	A classe tem a responsabilidade de validar se um paciente possui outra consulta 
	agendada no mesmo dia. Parece estar alinhada com o princípio da responsabilidade 
	única.
	
Aberto/Fechado (OCP):
	Semelhante aos casos anteriores, a classe não parece estar diretamente relacionada 
	ao princípio Aberto/Fechado. A estrutura da classe permite a adição de novas 
	regras de validação sem modificar a classe existente, favorecendo a extensibilidade
*/