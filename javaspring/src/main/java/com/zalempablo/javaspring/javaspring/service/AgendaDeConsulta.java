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

	/*
	  Princípio da Inversão de Dependência (Dependency Inversion Principle - DIP):
	  injeção de dependência é utilizada por meio da anotação @Autowired para
	  fornecer as dependências necessárias à classe.
	 */
	@Autowired
	private ConsultaRepository consultaRepository;

	@Autowired
	private MedicoRepository medicoRepository;

	@Autowired
	private PacienteRepository pacienteRepository;

	@Autowired
	private List<ValidadorAgendamentoConsulta> validadores;

	@Autowired
	public AgendaDeConsulta() {
		// Construtor vazio
	}

	// Método para configurar as dependências manualmente
	public void configurarDependencias(ConsultaRepository consultaRepository, MedicoRepository medicoRepository,
			PacienteRepository pacienteRepository, List<ValidadorAgendamentoConsulta> validadores) {
		this.consultaRepository = consultaRepository;
		this.medicoRepository = medicoRepository;
		this.pacienteRepository = pacienteRepository;
		this.validadores = validadores;
	}

	// pode ser necessário utilizar findById
	/*
	 * Princípio da Responsabilidade Única (Single Responsibility Principle - SRP):
	 	
	 */
	public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dadosAgendamentoConsulta) {
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
		if (medico == null) {
			throw new ValidacaoException("Não existe médico disponível nessa data");
		}

		var consulta = new Consulta(null, medico, paciente, dadosAgendamentoConsulta.data());
		consultaRepository.save(consulta);

		return new DadosDetalhamentoConsulta(consulta);
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
