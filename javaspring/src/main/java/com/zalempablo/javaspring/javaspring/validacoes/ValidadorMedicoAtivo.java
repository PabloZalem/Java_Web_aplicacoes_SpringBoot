package com.zalempablo.javaspring.javaspring.validacoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zalempablo.javaspring.javaspring.exception.ValidacaoException;
import com.zalempablo.javaspring.javaspring.repository.MedicoRepository;
import com.zalempablo.javaspring.javaspring.service.DadosAgendamentoConsulta;

@Component
public class ValidadorMedicoAtivo implements ValidadorAgendamentoConsulta{
	
	@Autowired
	private MedicoRepository medicoRepository;
	
	public void validar(DadosAgendamentoConsulta dadosAgendamentoConsulta) {
		if(dadosAgendamentoConsulta.idMedico() == null) {
			return;
		}
		
		var medicoEstaAtivo = medicoRepository.findAtivoById(dadosAgendamentoConsulta.idMedico());
		if (!medicoEstaAtivo) {
			throw new ValidacaoException("Consulta Nao pode ser agendada com médico excluído");
		}
	}
}
/*
 Inversão de Dependência (DIP):
	Injeção de Dependência: A classe utiliza injeção de dependência por meio da 
	anotação @Autowired para obter uma instância de MedicoRepository. Isso está em 
	conformidade com o princípio de Inversão de Dependência, pois a classe depende 
	de uma abstração (MedicoRepository) em vez de criar diretamente a dependência.

Responsabilidade Única (SRP):
	A classe tem a responsabilidade de validar se um médico está ativo, impedindo 
	que consultas sejam agendadas com médicos excluídos. Parece estar alinhada com 
	o princípio da responsabilidade única.

Aberto/Fechado (OCP):
	A classe não parece estar diretamente relacionada ao princípio Aberto/Fechado. 
	No entanto, pode-se argumentar que ela está fechada para modificação, pois a lógica 
	de validação pode ser estendida adicionando novos validadores sem modificar a 
	classe existente. Isso é uma forma de suportar a extensibilidade
 */
