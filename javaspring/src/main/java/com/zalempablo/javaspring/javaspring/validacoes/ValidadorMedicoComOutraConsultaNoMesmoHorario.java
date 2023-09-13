package com.zalempablo.javaspring.javaspring.validacoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zalempablo.javaspring.javaspring.exception.ValidacaoException;
import com.zalempablo.javaspring.javaspring.repository.ConsultaRepository;
import com.zalempablo.javaspring.javaspring.service.DadosAgendamentoConsulta;

@Component
public class ValidadorMedicoComOutraConsultaNoMesmoHorario implements ValidadorAgendamentoConsulta{

	@Autowired
	private ConsultaRepository consultaRepository;
	
	public void validar(DadosAgendamentoConsulta dadosAgendamentoConsulta) {
			var medicoPossuiOutraConsultaNoMesmoHorario = consultaRepository.existsByMedicosIdAndData(dadosAgendamentoConsulta.idMedico(), dadosAgendamentoConsulta.data());
			if ((boolean) medicoPossuiOutraConsultaNoMesmoHorario) {
				throw new ValidacaoException("Médico ja possui outra consulta agendada nesse mesmo horario");
			}
	}
}
