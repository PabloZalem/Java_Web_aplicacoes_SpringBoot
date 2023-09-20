package com.zalempablo.javaspring.javaspring.validacoes;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.zalempablo.javaspring.javaspring.exception.ValidacaoException;
import com.zalempablo.javaspring.javaspring.repository.MedicoRepository;
import com.zalempablo.javaspring.javaspring.service.DadosAgendamentoConsulta;
import com.zalempablo.javaspring.javaspring.service.Especialidade;

public class ValidadorMedicoAtivoTest {

    @InjectMocks
    private ValidadorMedicoAtivo validadorMedicoAtivo;

    @Mock
    private MedicoRepository medicoRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testValidarMedicoAtivo() {
        // Configurando o comportamento simulado do MedicoRepository
        when(medicoRepository.findAtivoById(1L)).thenReturn(true);

        DadosAgendamentoConsulta dados = new DadosAgendamentoConsulta(1L, 2L, LocalDateTime.now().plusDays(1), Especialidade.CARDIOLOGIA);

        // Deve passar sem lançar uma exceção
        assertDoesNotThrow(() -> validadorMedicoAtivo.validar(dados));
    }

    @Test
    public void testValidarMedicoInativo() {
        // Configurando o comportamento simulado do MedicoRepository
        when(medicoRepository.findAtivoById(1L)).thenReturn(false);

        DadosAgendamentoConsulta dados = new DadosAgendamentoConsulta(1L, 2L, LocalDateTime.now().plusDays(1), Especialidade.CARDIOLOGIA);

        // Deve lançar uma ValidacaoException
        assertThrows(ValidacaoException.class, () -> validadorMedicoAtivo.validar(dados));
    }

    @Test
    public void testValidarSemIdMedico() {
        DadosAgendamentoConsulta dados = new DadosAgendamentoConsulta(null, 2L, LocalDateTime.now().plusDays(1), Especialidade.CARDIOLOGIA);

        // Não deve lançar uma exceção porque não há validação necessária sem um ID de médico
        assertDoesNotThrow(() -> validadorMedicoAtivo.validar(dados));
    }
}

