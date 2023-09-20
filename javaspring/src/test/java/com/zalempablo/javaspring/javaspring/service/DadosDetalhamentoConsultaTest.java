package com.zalempablo.javaspring.javaspring.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import com.zalempablo.javaspring.javaspring.entities.Consulta;
import com.zalempablo.javaspring.javaspring.entities.Medicos;
import com.zalempablo.javaspring.javaspring.entities.Paciente;

public class DadosDetalhamentoConsultaTest {
	@Mock
    private Medicos medico;

    @Mock
    private Paciente paciente;

    @InjectMocks
    private DadosDetalhamentoConsulta dadosDetalhamentoConsulta;

    @Autowired
    @Mock
    private Consulta consulta;

    @BeforeEach
    public void setUp() {
    	 // Inicializa os objetos mockados e a classe a ser testada
        MockitoAnnotations.initMocks(this);

        // Configuração comum para os testes
        when(medico.getId()).thenReturn(2L);
        when(paciente.getId()).thenReturn(3L);

     // Crie uma consulta válida para uso nos testes
        LocalDateTime dataConsulta = LocalDateTime.of(2023, 9, 20, 12, 0); // Substitua com a data desejada
        Consulta consulta = new Consulta(1L, medico, paciente, dataConsulta);
        dadosDetalhamentoConsulta = new DadosDetalhamentoConsulta(consulta);

    }

    @Test
    public void testConstructor() {
        // Testa se o construtor cria uma instância corretamente
        assertNotNull(dadosDetalhamentoConsulta);
    }

    @Test
    public void testGetId() {
        // Testa se o método getId() retorna o valor correto
        assertEquals(1L, dadosDetalhamentoConsulta.id());
    }

    @Test
    public void testGetIdMedico() {
        // Testa se o método getIdMedico() retorna o valor correto
        assertEquals(2L, dadosDetalhamentoConsulta.idMedico());
    }

    @Test
    public void testGetIdPaciente() {
        // Testa se o método getIdPaciente() retorna o valor correto
        assertEquals(3L, dadosDetalhamentoConsulta.idPaciente());
    }

    @Test
    public void testGetData() {
        // Testa se o método getData() retorna o valor correto
        assertEquals(LocalDateTime.of(2023, 9, 20, 12, 0), dadosDetalhamentoConsulta.data());
    }
}
