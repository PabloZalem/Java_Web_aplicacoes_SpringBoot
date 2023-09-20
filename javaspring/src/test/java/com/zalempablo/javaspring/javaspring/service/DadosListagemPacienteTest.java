package com.zalempablo.javaspring.javaspring.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import com.zalempablo.javaspring.javaspring.entities.Endereco;
import com.zalempablo.javaspring.javaspring.entities.Paciente;

public class DadosListagemPacienteTest {

    @Test
    public void testConstructor() {
        // Crie um objeto Paciente para usar nos testes
        Paciente paciente = new Paciente(1L
        		,"Alice"
        		, "alice@example.com"
        		, "123456789"
        		, "123.123.123-12"
        		, new Endereco(new DadosEndereco("123 Main St", "Centro", "12345678", "Cidade Exemplo", "EX", "42", "Apto 301"))
        		, true);

        // Crie um objeto DadosListagemPaciente usando o construtor
        DadosListagemPaciente dadosPaciente = new DadosListagemPaciente(paciente);

        // Verifique se os campos do objeto DadosListagemPaciente foram definidos corretamente
        assertEquals(1L, dadosPaciente.id());
        assertEquals("Alice", dadosPaciente.nome());
        assertEquals("alice@example.com", dadosPaciente.email());
        assertEquals("123.123.123-12", dadosPaciente.cpf());
    }
    
    @Test
    public void testConstructorWithIndividualValues() {
    	DadosListagemPaciente dadosListagemPaciente = new DadosListagemPaciente(2L, "Dr. Johnson", "dr.johnson@example.com", "123.123.123-12");
    	
    	 assertEquals(2L, dadosListagemPaciente.id());
         assertEquals("Dr. Johnson", dadosListagemPaciente.nome());
         assertEquals("dr.johnson@example.com", dadosListagemPaciente.email());
         assertFalse("coisaBoa".equals(dadosListagemPaciente.cpf()));
    }
}
