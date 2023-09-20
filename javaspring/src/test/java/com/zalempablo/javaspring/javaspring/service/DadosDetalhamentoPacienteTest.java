package com.zalempablo.javaspring.javaspring.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.zalempablo.javaspring.javaspring.entities.Endereco;
import com.zalempablo.javaspring.javaspring.entities.Paciente;

public class DadosDetalhamentoPacienteTest {
	DadosEndereco endereco = new DadosEndereco(
		    "123 Main Street",  // logradouro
		    "Downtown",        // bairro
		    "12345678",         // cep
		    "Cityville",        // cidade
		    "CA",               // uf
		    "101",              // numero
		    "Apt 4B"            // complemento
		);
	
    @Test
    public void testConstructorWithPacienteObject() {
        // Crie um objeto Paciente para teste
        Paciente paciente = new Paciente(1L, "João", "joao@example.com", "1234-5678", "123.456.789-00",new Endereco(endereco), true);

        // Crie um objeto DadosDetalhamentoPaciente com base no objeto Paciente
        DadosDetalhamentoPaciente detalhamentoPaciente = new DadosDetalhamentoPaciente(paciente);

        // Use assert para verificar cada campo
        assertEquals(1L, detalhamentoPaciente.id());
        assertEquals("João", detalhamentoPaciente.nome());
        assertEquals("joao@example.com", detalhamentoPaciente.email());
        assertEquals("123.456.789-00", detalhamentoPaciente.cpf());
        assertEquals("1234-5678", detalhamentoPaciente.telefone());

        // Verifique o objeto Endereco individualmente
        Endereco endereco = detalhamentoPaciente.endereco();
        assertEquals("123 Main Street", endereco.getLogradouro());
        assertEquals("Downtown", endereco.getBairro());
        assertEquals("12345678", endereco.getCep());
        assertEquals("Cityville", endereco.getCidade());
        assertEquals("CA", endereco.getUf());
        assertEquals("101", endereco.getNumero());
        assertEquals("Apt 4B", endereco.getComplemento());
    }
}

