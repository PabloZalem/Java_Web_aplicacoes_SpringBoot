package com.zalempablo.javaspring.javaspring.validacoes;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.zalempablo.javaspring.javaspring.exception.ValidacaoException;
import com.zalempablo.javaspring.javaspring.repository.ConsultaRepository;
import com.zalempablo.javaspring.javaspring.service.DadosAgendamentoConsulta;
import com.zalempablo.javaspring.javaspring.service.Especialidade;

public class ValidadorMedicoComOutraConsultaNoMesmoHorarioTest {

    @Mock
    private ConsultaRepository consultaRepository;
    
   
    private ValidadorMedicoComOutraConsultaNoMesmoHorario validador;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        validador = new ValidadorMedicoComOutraConsultaNoMesmoHorario();
        validador.consultaRepository = consultaRepository;
    }

    @Test
    public void testValidarQuandoMedicoNaoPossuiOutraConsulta() {
        // Arrange
        DadosAgendamentoConsulta dadosAgendamento = new DadosAgendamentoConsulta(1L, 2L, LocalDateTime.now(), Especialidade.GINECOLOGIA);
        when(consultaRepository.existsByMedicosIdAndData(anyLong(), any(LocalDateTime.class))).thenReturn(false);
        
        // Act
        validador.validar(dadosAgendamento);
        
        // Assert
        // Nenhuma exceção deve ser lançada
    }
    
    @Test
    public void testValidarQuandoMedicoPossuiOutraConsulta() {
        // Arrange
        DadosAgendamentoConsulta dadosAgendamento = new DadosAgendamentoConsulta(1L, 2L, LocalDateTime.now(), Especialidade.ORTOPEDIA);
        when(consultaRepository.existsByMedicosIdAndData(anyLong(), any(LocalDateTime.class))).thenReturn(true);
        
        // Act
        // Assert
        assertThrows(ValidacaoException.class, () -> validador.validar(dadosAgendamento));
    }
}

