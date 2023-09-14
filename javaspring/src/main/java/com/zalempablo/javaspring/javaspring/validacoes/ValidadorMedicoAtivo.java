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
