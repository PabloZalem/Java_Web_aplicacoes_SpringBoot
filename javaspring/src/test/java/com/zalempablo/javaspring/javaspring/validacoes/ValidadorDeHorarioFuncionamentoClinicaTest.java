package com.zalempablo.javaspring.javaspring.validacoes;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.zalempablo.javaspring.javaspring.exception.ValidacaoException;
import com.zalempablo.javaspring.javaspring.service.DadosAgendamentoConsulta;

class ValidadorDeHorarioFuncionamentoClinicaTest {

    private ValidadorDeHorarioFuncionamentoClinica validador;
    private DadosAgendamentoConsulta dadosAgendamentoConsulta;

    @BeforeEach
    void setUp() {
        validador = new ValidadorDeHorarioFuncionamentoClinica();
        dadosAgendamentoConsulta = mock(DadosAgendamentoConsulta.class);
    }

    @Test
    void testValidacaoDataHoraDentroDoFuncionamento() {
        LocalDateTime dataHora = LocalDateTime.of(2023, 9, 20, 10, 0); // Uma quarta-feira às 10:00
        when(dadosAgendamentoConsulta.data()).thenReturn(dataHora);

        assertDoesNotThrow(() -> validador.validar(dadosAgendamentoConsulta));
    }

    @Test
    void testValidacaoDataHoraDomingo() {
        LocalDateTime dataHora = LocalDateTime.of(2023, 9, 24, 10, 0); // Um domingo às 10:00
        when(dadosAgendamentoConsulta.data()).thenReturn(dataHora);

        assertThrows(ValidacaoException.class, () -> validador.validar(dadosAgendamentoConsulta));
    }

    @Test
    void testValidacaoDataHoraAntesDaAbertura() {
        LocalDateTime dataHora = LocalDateTime.of(2023, 9, 20, 6, 59); // Uma quarta-feira às 6:59
        when(dadosAgendamentoConsulta.data()).thenReturn(dataHora);

        assertThrows(ValidacaoException.class, () -> validador.validar(dadosAgendamentoConsulta));
    }

    @Test
    void testValidacaoDataHoraAposOEncerramento() {
        LocalDateTime dataHora = LocalDateTime.of(2023, 9, 20, 19, 0); // Uma quarta-feira às 19:00
        when(dadosAgendamentoConsulta.data()).thenReturn(dataHora);

        assertThrows(ValidacaoException.class, () -> validador.validar(dadosAgendamentoConsulta));
    }
}

