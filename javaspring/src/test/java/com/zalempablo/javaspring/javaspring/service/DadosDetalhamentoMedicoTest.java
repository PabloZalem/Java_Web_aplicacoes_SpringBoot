package com.zalempablo.javaspring.javaspring.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.zalempablo.javaspring.javaspring.entities.Endereco;
import com.zalempablo.javaspring.javaspring.entities.Medicos;

public class DadosDetalhamentoMedicoTest {

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
	public void testConstructorWithMedicosObject() {
	    // Crie um objeto Medicos para teste
	    Medicos medicos = new Medicos(1L, "Dr. Smith", "dr.smith@example.com", "12345", Especialidade.DERMATOLOGIA,
	            new Endereco(endereco));

	    // Crie um objeto DadosDetalhamentoMedico com base no objeto Medicos
	    DadosDetalhamentoMedico detalhamentoMedico = new DadosDetalhamentoMedico(medicos);

	    // Use assertAll para verificar todos os campos
	    assertAll("Verifique se os campos foram atribuídos corretamente",
	        () -> assertEquals(1L, detalhamentoMedico.id()),
	        () -> assertEquals("Dr. Smith", detalhamentoMedico.nome()),
	        () -> assertEquals("dr.smith@example.com", detalhamentoMedico.email()),
	        () -> assertEquals("12345", detalhamentoMedico.crm()),
	        () -> assertEquals(Especialidade.DERMATOLOGIA, detalhamentoMedico.especialidade()),
	        () -> {
	            // Verifique cada campo individualmente do Endereco
	            Endereco enderecoMedico = detalhamentoMedico.endereco();
	            assertEquals("123 Main Street", enderecoMedico.getLogradouro());
	            assertEquals("Downtown", enderecoMedico.getBairro());
	            assertEquals("12345678", enderecoMedico.getCep());
	            assertEquals("Cityville", enderecoMedico.getCidade());
	            assertEquals("CA", enderecoMedico.getUf());
	            assertEquals("101", enderecoMedico.getNumero());
	            assertEquals("Apt 4B", enderecoMedico.getComplemento());
	        }
	    );
	}

}

