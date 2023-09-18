package com.zalempablo.javaspring.javaspring.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import com.zalempablo.javaspring.javaspring.entities.Paciente;

@DataJpaTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class PacienteRepositoryTest {

	@Autowired
    private PacienteRepository pacienteRepository;
    
    @BeforeEach
    public void setUp() {
        pacienteRepository = mock(PacienteRepository.class);
    }

    @Test
    public void testFindAllByAtivoTrue() {
        // Criar uma lista de pacientes ativos para simular a consulta
        List<Paciente> pacientesAtivos = new ArrayList<>();
        pacientesAtivos.add(new Paciente(1L, "Paciente1", "paciente1@example.com", "123456789", "1234567890", null, true));
        pacientesAtivos.add(new Paciente(2L, "Paciente2", "paciente2@example.com", "987654321", "0987654321", null, true));

        // Criar um objeto Page com os pacientes ativos
        Page<Paciente> page = new PageImpl<>(pacientesAtivos);

        // Configurar o comportamento do mock
        when(pacienteRepository.findAllByAtivoTrue(any(Pageable.class))).thenReturn(page);

        // Chamar o método e verificar o resultado
        Page<Paciente> result = pacienteRepository.findAllByAtivoTrue(mock(Pageable.class));
        assertEquals(2, result.getTotalElements());
        assertTrue(result.getContent().get(0).getAtivo());
        assertTrue(result.getContent().get(1).getAtivo());
    }

    @Test
    public void testFindAtivoById() {
        // Criar um paciente ativo para simular a consulta
        Paciente paciente = new Paciente(1L, "Paciente1", "paciente1@example.com", "123456789", "1234567890", null, true);

        // Configurar o comportamento do mock
        when(pacienteRepository.findAtivoById(eq(1L))).thenReturn(true);

        // Chamar o método e verificar o resultado
        Boolean result = pacienteRepository.findAtivoById(1L);
        assertTrue(result);
    }
    
    @Test
    public void testFindAllByAtivoTrue_NoActivePatients() {
        // Criar uma lista vazia de pacientes ativos para simular a consulta
        List<Paciente> pacientesAtivos = new ArrayList<>();

        // Criar um objeto Page vazio
        Page<Paciente> page = new PageImpl<>(pacientesAtivos);

        // Configurar o comportamento do mock
        when(pacienteRepository.findAllByAtivoTrue(any(Pageable.class))).thenReturn(page);

        // Chamar o método e verificar se não há pacientes ativos
        Page<Paciente> result = pacienteRepository.findAllByAtivoTrue(mock(Pageable.class));
        assertEquals(0, result.getTotalElements());
    }
    
    @Test
    public void testFindAtivoById_PatientNotFound() {
        // Configurar o comportamento do mock para retornar null (paciente não encontrado)
        when(pacienteRepository.findAtivoById(eq(1L))).thenReturn(null);

        // Chamar o método e verificar se o resultado é nulo
        Boolean result = pacienteRepository.findAtivoById(1L);
        assertNull(result);
    }
}

