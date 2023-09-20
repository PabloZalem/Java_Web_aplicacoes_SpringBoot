package com.zalempablo.javaspring.javaspring.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

public class DadosCadastroMedicoTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void testValidCadastroMedico() {
        // Crie uma instância válida de DadosCadastroMedico
        DadosCadastroMedico cadastroMedico = new DadosCadastroMedico(
                "Dr. Exemplo",
                "exemplo@email.com",
                "123456789",
                "123456",
                Especialidade.CARDIOLOGIA,
                new DadosEndereco("Rua Teste"
                		, "Bairro Teste"
                		, "12345-678"
                		, "Cidade Test"
                		, "SP"
                		, "1023"
                		, "Complemento Test")
        );

        Set<ConstraintViolation<DadosCadastroMedico>> violations = validator.validate(cadastroMedico);

        // Não deve haver violações de validação
        assertFalse(violations.isEmpty());
    }

    @Test
    void testInvalidCadastroMedico() {
        // Crie uma instância inválida de DadosCadastroMedico com campos em branco
        DadosCadastroMedico cadastroMedico = new DadosCadastroMedico(
                "",
                "",
                "",
                "",
                Especialidade.CARDIOLOGIA,
                new DadosEndereco("" 
                		, ""
                		, ""
                		, ""
                		, ""
                		, ""
                		, "")
        );

        Set<ConstraintViolation<DadosCadastroMedico>> violations = validator.validate(cadastroMedico);

        // Deve haver violações de validação para todos os campos em branco
        assertEquals(11, violations.size());
    }
}

