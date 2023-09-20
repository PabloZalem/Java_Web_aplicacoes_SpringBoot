package com.zalempablo.javaspring.javaspring.service;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class DadosCadastroPacienteTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testDadosCadastroPacienteValido() {
        // Dados de exemplo válidos
        DadosEndereco endereco = new DadosEndereco("Rua Teste"
        		, "Bairro Teste"
        		, "12345-678"
        		, "Cidade Test"
        		, "SP"
        		, "1023"
        		, "Complemento Test");
        DadosCadastroPaciente paciente = DadosCadastroPaciente.criar("Nome Teste", "email@teste.com", "1234567890", "123.456.789-09", endereco);

        // Verifica se não há violações das validações
        assertFalse(validator.validate(paciente).isEmpty());
    }

    @Test
    void testNomeEmBranco() {
        // Dados de exemplo com nome em branco
        DadosEndereco endereco = new DadosEndereco("Rua Teste"
        		, "Bairro Teste"
        		, "12345-678"
        		, "Cidade Test"
        		, "SP"
        		, "1023"
        		, "Complemento Test");
        DadosCadastroPaciente paciente = DadosCadastroPaciente.criar("", "email@teste.com", "1234567890", "123.456.789-09", endereco);

        // Verifica se há uma violação de @NotBlank no campo 'nome'
        assertFalse(validator.validate(paciente).isEmpty());
    }

    @Test
    void testEmailInvalido() {
        // Dados de exemplo com e-mail inválido
        DadosEndereco endereco = new DadosEndereco("Rua Teste"
        		, "Bairro Teste"
        		, "12345-678"
        		, "Cidade Test"
        		, "SP"
        		, "1023"
        		, "Complemento Test");
        DadosCadastroPaciente paciente = DadosCadastroPaciente.criar("Nome Teste", "email_invalido", "1234567890", "123.456.789-09", endereco);

        // Verifica se há uma violação de @Email no campo 'email'
        assertFalse(validator.validate(paciente).isEmpty());
    }

    // Você pode criar testes semelhantes para os outros campos, como 'telefone', 'cpf', e 'endereco'.
}
