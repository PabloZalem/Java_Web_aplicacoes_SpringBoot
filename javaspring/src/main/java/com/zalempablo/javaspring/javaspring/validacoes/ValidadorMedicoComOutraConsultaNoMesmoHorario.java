package com.zalempablo.javaspring.javaspring.validacoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zalempablo.javaspring.javaspring.exception.ValidacaoException;
import com.zalempablo.javaspring.javaspring.repository.ConsultaRepository;
import com.zalempablo.javaspring.javaspring.service.DadosAgendamentoConsulta;

@Component
public class ValidadorMedicoComOutraConsultaNoMesmoHorario implements ValidadorAgendamentoConsulta{

	@Autowired ConsultaRepository consultaRepository;
	
	public void validar(DadosAgendamentoConsulta dadosAgendamentoConsulta) {
			var medicoPossuiOutraConsultaNoMesmoHorario = consultaRepository.existsByMedicosIdAndData(dadosAgendamentoConsulta.idMedico(), dadosAgendamentoConsulta.data());
			if (medicoPossuiOutraConsultaNoMesmoHorario) {
				throw new ValidacaoException("Médico ja possui outra consulta agendada nesse mesmo horario");
			}
	}
}
/*
Inversão de Dependência (DIP):
	A classe utiliza injeção de dependência ao usar a anotação 
	@Autowired para obter uma instância de ConsultaRepository. Isso está de 
	acordo com o princípio da inversão de dependência, pois a classe depende 
	de uma abstração (ConsultaRepository) em vez de criar diretamente a dependência.

Responsabilidade Única (SRP):
	A classe tem a responsabilidade de validar se um médico possui outra 
	consulta agendada no mesmo horário. Parece estar alinhada com o princípio 
	da responsabilidade única.

Aberto/Fechado (OCP):
	Similar ao caso anterior, a classe não parece estar diretamente 
	relacionada ao princípio Aberto/Fechado. No entanto, a estrutura da classe 
	permite a extensão sem a modificação da classe existente, seguindo uma 
	abordagem que favorece a extensibilidade.
*/