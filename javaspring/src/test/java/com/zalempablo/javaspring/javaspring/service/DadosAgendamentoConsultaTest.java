package com.zalempablo.javaspring.javaspring.service;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


public class DadosAgendamentoConsultaTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidDadosAgendamentoConsulta() {
        // Crie uma instância válida de DadosAgendamentoConsulta
        DadosAgendamentoConsulta agendamento = new DadosAgendamentoConsulta(
                1L, 2L, LocalDateTime.now().plusDays(1), Especialidade.CARDIOLOGIA);

        // Valide a instância usando o Bean Validation
        var violations = validator.validate(agendamento);
        assertTrue(violations.isEmpty(), "Não deve haver violações de validação");
    }

    @Test
    public void testInvalidDadosAgendamentoConsulta() {
        // Crie uma instância inválida de DadosAgendamentoConsulta
        DadosAgendamentoConsulta agendamento = new DadosAgendamentoConsulta(
                1L, null, LocalDateTime.now().minusDays(1), null);

        // Valide a instância usando o Bean Validation
        var violations = validator.validate(agendamento);
        assertFalse(violations.isEmpty(), "Deve haver violações de validação");

        // Certifique-se de que as violações esperadas ocorreram
        assertEquals(2, violations.size(), "Deve haver 2 violações de validação");
    }
}

