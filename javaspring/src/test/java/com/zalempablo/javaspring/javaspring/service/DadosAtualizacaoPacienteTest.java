package com.zalempablo.javaspring.javaspring.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class DadosAtualizacaoPacienteTest {

    @Test
    void testValidDadosAtualizacaoPaciente() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        DadosEndereco endereco = new DadosEndereco("Rua Teste", "Bairro Teste", "12345678", "Cidade Teste", "UF Teste", "123", "Apto 456");
        DadosAtualizacaoPaciente paciente = new DadosAtualizacaoPaciente(1L, "Nome Teste", "1234567890", endereco);

        assertTrue(validator.validate(paciente).isEmpty());
    }

    @Test
    void testInvalidDadosAtualizacaoPaciente() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        DadosEndereco endereco = new DadosEndereco("Rua Teste", "Bairro Teste", "12345678", "Cidade Teste", "UF Teste", "123", "Apto 456");
        DadosAtualizacaoPaciente paciente = new DadosAtualizacaoPaciente(null, "", "12345", endereco);

        assertFalse(validator.validate(paciente).isEmpty());
    }
}

