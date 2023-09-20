package com.zalempablo.javaspring.javaspring.validacoes;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.zalempablo.javaspring.javaspring.exception.ValidacaoException;
import com.zalempablo.javaspring.javaspring.repository.PacienteRepository;
import com.zalempablo.javaspring.javaspring.service.DadosAgendamentoConsulta;
import com.zalempablo.javaspring.javaspring.service.Especialidade;

public class ValidadorPacienteAtivoTest {

    private ValidadorPacienteAtivo validadorPacienteAtivo;

    @Mock
    private PacienteRepository pacienteRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Inicialize os objetos mock
        validadorPacienteAtivo = new ValidadorPacienteAtivo();
        validadorPacienteAtivo.setPacienteRepository(pacienteRepository);
    }

    @Test
    public void testPacienteAtivo() {
        // Simule o comportamento do repositório para retornar true
        when(pacienteRepository.findAtivoById(anyLong())).thenReturn(true);

        DadosAgendamentoConsulta dadosAgendamentoConsulta = new DadosAgendamentoConsulta(1L, 1L, LocalDateTime.now().plusDays(1), Especialidade.CARDIOLOGIA);

        // Verifique se não há exceções lançadas
        assertDoesNotThrow(() -> validadorPacienteAtivo.validar(dadosAgendamentoConsulta));
    }

    @Test
    public void testPacienteInativo() {
        // Simule o comportamento do repositório para retornar false
        when(pacienteRepository.findAtivoById(anyLong())).thenReturn(false);

        DadosAgendamentoConsulta dadosAgendamentoConsulta = new DadosAgendamentoConsulta(1L, 1L, LocalDateTime.now().plusDays(1), Especialidade.CARDIOLOGIA);

        // Verifique se uma ValidacaoException é lançada
        assertThrows(ValidacaoException.class, () -> validadorPacienteAtivo.validar(dadosAgendamentoConsulta));
    }
}
