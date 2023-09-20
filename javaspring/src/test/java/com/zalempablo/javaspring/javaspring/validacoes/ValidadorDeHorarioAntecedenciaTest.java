package com.zalempablo.javaspring.javaspring.validacoes;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.zalempablo.javaspring.javaspring.exception.ValidacaoException;
import com.zalempablo.javaspring.javaspring.service.DadosAgendamentoConsulta;
import com.zalempablo.javaspring.javaspring.service.Especialidade;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.zalempablo.javaspring.javaspring.exception.ValidacaoException;
import com.zalempablo.javaspring.javaspring.service.DadosAgendamentoConsulta;
import com.zalempablo.javaspring.javaspring.service.Especialidade;

public class ValidadorDeHorarioAntecedenciaTest {

    private ValidadorDeHorarioAntecedencia validador;

    @BeforeEach
    void setUp() {
        validador = new ValidadorDeHorarioAntecedencia();
    }

    @Test
    void testValidarComAgendamentoComAntecedencia() {
        // Crie uma instância de DadosAgendamentoConsulta com uma data futura
        LocalDateTime dataFutura = LocalDateTime.now().plusMinutes(60); // Agendado para 1 hora no futuro
        DadosAgendamentoConsulta dadosAgendamento = new DadosAgendamentoConsulta(1L, 2L, dataFutura, Especialidade.CARDIOLOGIA);

        // A validação deve passar sem lançar uma exceção
        assertDoesNotThrow(() -> validador.validar(dadosAgendamento));
    }

    @Test
    void testValidarComAgendamentoSemAntecedencia() {
        // Crie uma instância de DadosAgendamentoConsulta com uma data muito próxima
        LocalDateTime dataProxima = LocalDateTime.now().plusMinutes(15); // Agendado para 15 minutos no futuro
        DadosAgendamentoConsulta dadosAgendamento = new DadosAgendamentoConsulta(1L, 2L, dataProxima, Especialidade.CARDIOLOGIA);

        // A validação deve lançar uma ValidacaoException
        assertThrows(ValidacaoException.class, () -> validador.validar(dadosAgendamento));
    }
}
