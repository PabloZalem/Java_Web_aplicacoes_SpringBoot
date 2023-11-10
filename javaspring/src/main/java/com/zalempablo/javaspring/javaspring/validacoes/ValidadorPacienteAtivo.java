package com.zalempablo.javaspring.javaspring.validacoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zalempablo.javaspring.javaspring.exception.ValidacaoException;
import com.zalempablo.javaspring.javaspring.repository.PacienteRepository;
import com.zalempablo.javaspring.javaspring.service.DadosAgendamentoConsulta;

@Component
public class ValidadorPacienteAtivo implements ValidadorAgendamentoConsulta{

	@Autowired PacienteRepository pacienteRepository;
	
	public void validar(DadosAgendamentoConsulta dadosAgendamentoConsulta) {
		var pacienteEstaAtivo = pacienteRepository.findAtivoById(dadosAgendamentoConsulta.idPaciente());
		if(!pacienteEstaAtivo) {
			throw new ValidacaoException("Consulta não pode ser agendada com paciente excluído");
		}
	}

	public void setPacienteRepository(PacienteRepository pacienteRepository2) {
			this.pacienteRepository = pacienteRepository2;
	}
}

/*
 	Inversão de Dependência (DIP):
		A classe utiliza injeção de dependência ao usar a anotação @Autowired 
		para obter uma instância de PacienteRepository. Isso está de acordo com 
		o princípio da inversão de dependência, pois a classe depende de uma 
		abstração (PacienteRepository) em vez de criar diretamente a dependência.

Responsabilidade Única (SRP):
	A classe tem a responsabilidade de validar se um paciente está ativo. 
	Parece estar alinhada com o princípio da responsabilidade única.

Aberto/Fechado (OCP):
	Assim como nos casos anteriores, a classe não parece estar diretamente 
	relacionada ao princípio Aberto/Fechado. A estrutura da classe permite a 
	adição de novas regras de validação sem modificar a classe existente, 
	favorecendo a extensibilidade.
 */
