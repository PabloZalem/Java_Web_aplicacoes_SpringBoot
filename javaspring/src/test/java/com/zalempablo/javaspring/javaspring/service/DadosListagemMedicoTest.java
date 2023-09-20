package com.zalempablo.javaspring.javaspring.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import com.zalempablo.javaspring.javaspring.entities.Endereco;
import com.zalempablo.javaspring.javaspring.entities.Medicos;

public class DadosListagemMedicoTest {

    @Test
    public void testConstructorWithMedicosObject() {
        Medicos medicos = new Medicos(1L
        		, "Dr. Smith"
        		, "dr.smith@example.com"
        		, "12345"
        		, Especialidade.CARDIOLOGIA
        		, new Endereco(new DadosEndereco("123 Main St", "Centro", "12345678", "Cidade Exemplo", "EX", "42", "Apto 301"))     
        		);
        DadosListagemMedico dadosListagemMedico = new DadosListagemMedico(medicos);

        assertEquals(1L, dadosListagemMedico.id());
        assertEquals("Dr. Smith", dadosListagemMedico.nome());
        assertEquals("dr.smith@example.com", dadosListagemMedico.email());
        assertEquals("12345", dadosListagemMedico.crm());
        assertEquals(Especialidade.CARDIOLOGIA, dadosListagemMedico.especialidade());
    }

    @Test
    public void testConstructorWithIndividualValues() {
        DadosListagemMedico dadosListagemMedico = new DadosListagemMedico(2L, "Dr. Johnson", "dr.johnson@example.com", "54321", Especialidade.CARDIOLOGIA);
        
        assertEquals(2L, dadosListagemMedico.id());
        assertEquals("Dr. Johnson", dadosListagemMedico.nome());
        assertEquals("dr.johnson@example.com", dadosListagemMedico.email());
        assertFalse("coisaBoa".equals(dadosListagemMedico.crm()));
        assertEquals(Especialidade.CARDIOLOGIA, dadosListagemMedico.especialidade());
    }
}
