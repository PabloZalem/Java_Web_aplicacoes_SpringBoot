package com.zalempablo.javaspring.javaspring.validacoes;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.zalempablo.javaspring.javaspring.exception.ValidacaoException;
import com.zalempablo.javaspring.javaspring.repository.ConsultaRepository;
import com.zalempablo.javaspring.javaspring.service.DadosAgendamentoConsulta;
import com.zalempablo.javaspring.javaspring.service.Especialidade;

public class ValidadorPacienteSemOutraConsultaNoDiaTest {

    @Mock
    private ConsultaRepository consultaRepository;

    private ValidadorPacienteSemOutraConsultaNoDia validador;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        validador = new ValidadorPacienteSemOutraConsultaNoDia(consultaRepository);
    }

    @Test
    public void testValidar_PacienteSemOutraConsultaNoDia() {
        DadosAgendamentoConsulta dadosAgendamentoConsulta = new DadosAgendamentoConsulta(1L, 2L, LocalDateTime.now().plusHours(1), Especialidade.GINECOLOGIA);

        when(consultaRepository.existsByPacienteIdAndDataBetween(eq(2L), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(false);

        validador.validar(dadosAgendamentoConsulta);
    }

    @Test
    public void testValidar_PacienteComOutraConsultaNoDia() {
        DadosAgendamentoConsulta dadosAgendamentoConsulta = new DadosAgendamentoConsulta(1L, 2L, LocalDateTime.now().plusHours(1), Especialidade.DERMATOLOGIA);

        when(consultaRepository.existsByPacienteIdAndDataBetween(eq(2L), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(true);

        try {
            validador.validar(dadosAgendamentoConsulta);
        } catch (ValidacaoException e) {
            // Verifique se a exceção foi lançada com a mensagem correta
            assertEquals("Paciente ja possui uma consulta agendada nesse dia", e.getMessage());
        }
    }
}
