package com.zalempablo.javaspring.javaspring.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import com.zalempablo.javaspring.javaspring.entities.Consulta;
import com.zalempablo.javaspring.javaspring.exception.ValidacaoException;
import com.zalempablo.javaspring.javaspring.repository.ConsultaRepository;
import com.zalempablo.javaspring.javaspring.repository.MedicoRepository;
import com.zalempablo.javaspring.javaspring.repository.PacienteRepository;
import com.zalempablo.javaspring.javaspring.validacoes.ValidadorAgendamentoConsulta;

public class AgendaConsultaServiceTest {

	@Autowired
    private AgendaDeConsulta agendaDeConsulta;

    @Mock
    private ConsultaRepository consultaRepository;

    @Mock
    private MedicoRepository medicoRepository;

    @Mock
    private PacienteRepository pacienteRepository;

    @Mock
    private ValidadorAgendamentoConsulta validadorAgendamentoConsulta;
    
    private Clock clock; // Clock mockado

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        agendaDeConsulta = new AgendaDeConsulta();
        List<ValidadorAgendamentoConsulta> validadores = Collections.singletonList(validadorAgendamentoConsulta);
        agendaDeConsulta.configurarDependencias(consultaRepository, medicoRepository, pacienteRepository, validadores);
        
        // Configurar um Clock mockado para retornar uma data específica no teste
        clock = Clock.fixed(Instant.parse("2023-09-20T10:00:00Z"), ZoneId.of("UTC"));
    
    }

    @Test
    public void testAgendarConsultaSuccess() {
        LocalDateTime data = LocalDateTime.now(clock).plusDays(2);
        DadosAgendamentoConsulta dadosAgendamentoConsulta = new DadosAgendamentoConsulta(1L, 2L, data, Especialidade.CARDIOLOGIA);

        // Configurar mock de PacienteRepository para retornar true
        when(pacienteRepository.existsById(any())).thenReturn(true);

        // Configurar mock de MedicoRepository para retornar true para existsById
        when(medicoRepository.existsById(any())).thenReturn(true);

        // Configurar mock de MedicoRepository para retornar null quando nenhum médico estiver disponível
        when(medicoRepository.escolherMedicoAleatorioLivreNaData(any(), any())).thenReturn(null);

        // Configurar mock de validadorAgendamentoConsulta para não fazer nada (doNothing)
        doNothing().when(validadorAgendamentoConsulta).validar(any());

        // Executar o agendamento
        assertThrows(ValidacaoException.class, () -> agendaDeConsulta.agendar(dadosAgendamentoConsulta));
    }

    @Test
    public void testAgendarConsultaInvalidPatientId() {
    	var data = LocalDateTime.now().plusHours(2);
        DadosAgendamentoConsulta dadosAgendamentoConsulta = new DadosAgendamentoConsulta(1L,2L, data, Especialidade.CARDIOLOGIA);
        when(pacienteRepository.existsById(any())).thenReturn(false);

        assertThrows(ValidacaoException.class, () -> agendaDeConsulta.agendar(dadosAgendamentoConsulta));
        verify(consultaRepository, never()).save(any(Consulta.class));
    }

    @Test
    public void testAgendarConsultaInvalidMedicoId() {
    	var data = LocalDateTime.now().plusHours(2);
        DadosAgendamentoConsulta dadosAgendamentoConsulta = new DadosAgendamentoConsulta(1L,2L, data, Especialidade.CARDIOLOGIA);
        when(pacienteRepository.existsById(any())).thenReturn(true);
        when(medicoRepository.existsById(any())).thenReturn(false);

        assertThrows(ValidacaoException.class, () -> agendaDeConsulta.agendar(dadosAgendamentoConsulta));
        verify(consultaRepository, never()).save(any(Consulta.class));
    }

    @Test
    public void testAgendarConsultaNoAvailableMedico() {
    	var data = LocalDateTime.now().plusHours(2);
    	DadosAgendamentoConsulta dadosAgendamentoConsulta = new DadosAgendamentoConsulta(1L,2L, data, Especialidade.CARDIOLOGIA);
    	when(pacienteRepository.existsById(any())).thenReturn(true);
        when(medicoRepository.existsById(any())).thenReturn(true);
        doNothing().when(validadorAgendamentoConsulta).validar(any());
        when(medicoRepository.escolherMedicoAleatorioLivreNaData(any(), any())).thenReturn(null);

        assertThrows(ValidacaoException.class, () -> agendaDeConsulta.agendar(dadosAgendamentoConsulta));
        verify(consultaRepository, never()).save(any(Consulta.class));
    }
}
