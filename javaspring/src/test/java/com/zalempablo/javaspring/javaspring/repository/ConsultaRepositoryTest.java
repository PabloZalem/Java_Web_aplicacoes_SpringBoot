package com.zalempablo.javaspring.javaspring.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import com.zalempablo.javaspring.javaspring.entities.Consulta;
import com.zalempablo.javaspring.javaspring.entities.Medicos;
import com.zalempablo.javaspring.javaspring.entities.Paciente;

@DataJpaTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class ConsultaRepositoryTest {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void testExistsByPacienteIdAndDataBetween() {
        // Given
        LocalDateTime primeiroHorario = LocalDateTime.now();
        LocalDateTime ultimoHorario = LocalDateTime.now().plusHours(1);

        Paciente paciente = new Paciente(/* criar paciente com ID e outros dados necessários */);
        Medicos medico = new Medicos(/* criar médico com ID e outros dados necessários */);
        Consulta consulta = new Consulta(null, medico, paciente, LocalDateTime.now());

        testEntityManager.persistAndFlush(paciente);
        testEntityManager.persistAndFlush(medico);
        testEntityManager.persistAndFlush(consulta);

        // When
        boolean exists = consultaRepository.existsByPacienteIdAndDataBetween(paciente.getId(), primeiroHorario, ultimoHorario);

        // Then
        assertThat(exists).isTrue();
    }

    @Test
    public void testExistsByMedicosIdAndData() {
        // Given
        LocalDateTime data = LocalDateTime.now();

        Medicos medico = new Medicos(/* criar médico com ID e outros dados necessários */);
        Consulta consulta = new Consulta(null, medico, null, data);

        testEntityManager.persistAndFlush(medico);
        testEntityManager.persistAndFlush(consulta);

        // When
        boolean exists = consultaRepository.existsByMedicosIdAndData(medico.getId(), data);

        // Then
        assertThat(exists).isTrue();
    }
    
    @Test
    public void testDeleteConsulta() {
        // Given
        Consulta consulta = new Consulta(/* criar consulta com dados necessários */);
        testEntityManager.persistAndFlush(consulta);

        // When
        consultaRepository.deleteById(consulta.getId());
        Optional<Consulta> consultaOptional = consultaRepository.findById(consulta.getId());

        // Then
        assertThat(consultaOptional).isEmpty(); 
    }
    
    
    @Test
    public void testFindConsultaById() {
        // Given
        Consulta consulta = new Consulta(/* criar consulta com dados necessários */);
        testEntityManager.persistAndFlush(consulta);

        // When
        Optional<Consulta> consultaEncontrada = consultaRepository.findById(consulta.getId());

        // Then
        assertThat(consultaEncontrada).isPresent();
        assertThat(consultaEncontrada.get()).isEqualTo(consulta);
    }
    
    //@Test
    public void testValidation() {
        // Given
        Consulta consultaInvalida = new Consulta(/* criar consulta com dados inválidos */);

        // When & Then (Use assertThrows para verificar se a exceção apropriada é lançada)
        assertThrows(DataIntegrityViolationException.class, () -> {
            consultaRepository.saveAndFlush(consultaInvalida);
        });
    }


}
