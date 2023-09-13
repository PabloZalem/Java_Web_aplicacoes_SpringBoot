package com.zalempablo.javaspring.javaspring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zalempablo.javaspring.javaspring.entities.Consulta;
import com.zalempablo.javaspring.javaspring.entities.Medicos;
import com.zalempablo.javaspring.javaspring.exception.ValidacaoException;
import com.zalempablo.javaspring.javaspring.repository.ConsultaRepository;
import com.zalempablo.javaspring.javaspring.repository.MedicoRepository;
import com.zalempablo.javaspring.javaspring.repository.PacienteRepository;
import com.zalempablo.javaspring.javaspring.validacoes.ValidadorAgendamentoConsulta;

@Service
public class AgendaDeConsulta {

	@Autowired
	private ConsultaRepository consultaRepository;

	@Autowired
	private MedicoRepository medicoRepository;

	@Autowired
	private PacienteRepository pacienteRepository;
	
	@Autowired
	private List<ValidadorAgendamentoConsulta> validadores;

	public void agendar(DadosAgendamentoConsulta dadosAgendamentoConsulta) {
		if (!pacienteRepository.existsById(dadosAgendamentoConsulta.idPaciente())) {
			throw new ValidacaoException("id do paciente informado nao existe");
		}

		if (dadosAgendamentoConsulta.idMedico() != null
				&& !medicoRepository.existsById(dadosAgendamentoConsulta.idMedico())) {
			throw new ValidacaoException("id do medico informado nao existe");
		}
		
		validadores.forEach(v -> v.validar(dadosAgendamentoConsulta));

		var paciente = pacienteRepository.getReferenceById(dadosAgendamentoConsulta.idPaciente());
		var medico = escolherMedico(dadosAgendamentoConsulta);
		var consulta = new Consulta(null, medico, paciente, dadosAgendamentoConsulta.data());
		consultaRepository.save(consulta);
	}

	private Medicos escolherMedico(DadosAgendamentoConsulta dadosAgendamentoConsulta) {
		if (dadosAgendamentoConsulta.idMedico() != null) {
			return medicoRepository.getReferenceById(dadosAgendamentoConsulta.idMedico());
		}

		if (dadosAgendamentoConsulta.especialidade() == null) {
			throw new ValidacaoException("Especialidade é obrigatoria");
		}

		return medicoRepository.escolherMedicoAleatorioLivreNaData(dadosAgendamentoConsulta.especialidade(),
				dadosAgendamentoConsulta.data());
	}
}
