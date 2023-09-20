package com.zalempablo.javaspring.javaspring.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class DadosEnderecoTest {

    @Test
    public void testValidDadosEndereco() {
        // Configura a validação usando o Bean Validation
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        // Cria uma instância de DadosEndereco válida
        DadosEndereco endereco = new DadosEndereco(
                "Rua Principal",
                "Bairro Teste",
                "12345678",
                "Cidade Teste",
                "UF",
                "123",
                "Apto 101"
        );

        // Valida a instância
        Set<ConstraintViolation<DadosEndereco>> violations = validator.validate(endereco);

        // Deve ser válido, portanto, o conjunto de violações deve estar vazio
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidDadosEndereco() {
        // Configura a validação usando o Bean Validation
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        // Cria uma instância de DadosEndereco inválida
        DadosEndereco endereco = new DadosEndereco(
                null, // logradouro em branco
                "Bairro Teste",
                "1234567", // CEP com formato inválido
                "", // cidade em branco
                "", // UF em branco
                "123",
                "Apto 101"
        );

        // Valida a instância
        Set<ConstraintViolation<DadosEndereco>> violations = validator.validate(endereco);

        // Deve conter violações de validação
        assertFalse(violations.isEmpty());

        // Você pode adicionar asserções específicas para cada campo inválido, se desejar
    }
}

