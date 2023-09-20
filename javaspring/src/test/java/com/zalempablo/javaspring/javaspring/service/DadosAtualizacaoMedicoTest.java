package com.zalempablo.javaspring.javaspring.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class DadosAtualizacaoMedicoTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidDadosAtualizacaoMedico() {
        DadosEndereco dadosEndereco = new DadosEndereco(
            "Rua ABC",
            "Bairro Teste",
            "12345678",
            "Cidade",
            "UF",
            "123",
            "Apto 101"
        );

        DadosAtualizacaoMedico medico = new DadosAtualizacaoMedico(
            1L,
            "Nome Médico",
            "123-456-7890",
            dadosEndereco
        );

        Set<ConstraintViolation<DadosAtualizacaoMedico>> violations = validator.validate(medico);
        assertTrue(violations.isEmpty(), "Nenhum erro de validação esperado");
    }

    @Test
    public void testInvalidDadosAtualizacaoMedico() {
        DadosEndereco dadosEndereco = new DadosEndereco(
            "Rua ABC",
            "Bairro Teste",
            "12345678",
            "Cidade",
            "UF",
            "123",
            "Apto 101"
        );

        // Nome em branco, o que é inválido de acordo com as anotações
        DadosAtualizacaoMedico medico = new DadosAtualizacaoMedico(
            1L,
            "",
            "123-456-7890",
            dadosEndereco
        );

        Set<ConstraintViolation<DadosAtualizacaoMedico>> violations = validator.validate(medico);
        assertFalse(!violations.isEmpty(), "Esperava-se erro de validação para o nome em branco");
    }
}

