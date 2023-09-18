package com.zalempablo.javaspring.javaspring.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zalempablo.javaspring.javaspring.entities.Consulta;
import com.zalempablo.javaspring.javaspring.entities.Medicos;
import com.zalempablo.javaspring.javaspring.entities.Paciente;
import com.zalempablo.javaspring.javaspring.service.DadosCadastroMedico;
import com.zalempablo.javaspring.javaspring.service.DadosCadastroPaciente;
import com.zalempablo.javaspring.javaspring.service.DadosEndereco;
import com.zalempablo.javaspring.javaspring.service.Especialidade;

@DataJpaTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class MedicoRepositoryTest {
	
	@Autowired
	private MedicoRepository medicoRepository;
	
	@Autowired
	private TestEntityManager testEntityManager;
	
	@Test
	@DisplayName("Deveria devolver null quando unico medico cadastrado nao esta disponivel na data")
	void escolherMedicoAleatorioLivreNaDataCenario1() {
		//Given
		var proximaSegundaAs10 = LocalDate.now()
				.with(TemporalAdjusters.next(DayOfWeek.MONDAY))
				.atTime(10,0);
		
		//When
		var medico = cadastrarMedico("Medico", "medico@medico", "123455", Especialidade.CARDIOLOGIA);
		var paciente = cadastrarPaciente("Paciente", "paciente@paciente", "00000000000");
		cadastrarConsulta(medico, paciente, proximaSegundaAs10);
		
		//Then
		var medicoLivre = medicoRepository.escolherMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA, proximaSegundaAs10);
		assertThat(medicoLivre).isNull();
	}
	
	@Test
	@DisplayName("Deveria devolver medico quando ele estiver disponivel na data")
	void escolherMedicoAleatorioLivreNaDataCenario2() {
	     var proximaSegundaAs10 = LocalDate.now()
	                    .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
	                    .atTime(10, 0);
	    var medico = cadastrarMedico("Medico", "medico@voll.med", "123456", Especialidade.CARDIOLOGIA);

	    var medicoLivre = medicoRepository.escolherMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA, proximaSegundaAs10);
	    assertThat(medicoLivre).isEqualTo(medico);
	}
	
    @Test
    void testFindAtivoById_MedicoAtivo() {
        // Cenário: Médico com ID ativo
        var medico = cadastrarMedico("Medico", "medico@voll.med", "123456", Especialidade.CARDIOLOGIA);

        // Ação
        var ativo = medicoRepository.findAtivoById(medico.getId());

        // Verificação
        assertThat(ativo).isTrue();
    }
	
    @Test
    void testFindAtivoById_MedicoNaoAtivo() {
        // Cenário: Médico com ID não ativo
        var medico = cadastrarMedico("Medico", "medico@voll.med", "123456", Especialidade.CARDIOLOGIA);
        medico.setAtivo(false);
        testEntityManager.persistAndFlush(medico);

        // Ação
        var ativo = medicoRepository.findAtivoById(medico.getId());

        // Verificação
        assertThat(ativo).isFalse();
    }
    
    @Test
    void testFindAtivoById_MedicoNaoExiste() {
        // Cenário: Médico com ID não existente
        var idNaoExistente = 999L;

        // Ação
        var ativo = medicoRepository.findAtivoById(idNaoExistente);

        // Verificação
        assertThat(ativo).isNull();
    }
    
    //@Test
    //@JsonIgnore
    void testEscolherMedicoAleatorioLivreNaData_EspecialidadeNula() {
        // Cenário: Especialidade nula
        var proximaSegundaAs10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10,0);

        // Ação e Verificação
        assertThrows(IllegalArgumentException.class, () -> {
            medicoRepository.escolherMedicoAleatorioLivreNaData(null, proximaSegundaAs10);
        });
    }
    
    //@Test
    //@JsonIgnore
    void testEscolherMedicoAleatorioLivreNaData_DataNula() {
        // Cenário: Data nula
        var especialidade = Especialidade.CARDIOLOGIA;

        // Ação e Verificação
        assertThrows(IllegalArgumentException.class, () -> {
            medicoRepository.escolherMedicoAleatorioLivreNaData(especialidade, null);
        });
    }
    
    @Test
    void testEscolherMedicoAleatorioLivreNaData_DataSemCorrespondencia() {
        // Cenário: Data sem correspondência
        var proximaSegundaAs10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);
        var medico = cadastrarMedico("Medico", "medico@voll.med", "123456", Especialidade.CARDIOLOGIA);

        // Cenário: Consulta em uma data diferente
        var outraData = proximaSegundaAs10.plusDays(1);

        // Ação
        var medicoLivre = medicoRepository.escolherMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA, outraData);

        // Verificação
        assertThat(medicoLivre).isEqualTo(medico);
    }
    
    @Test
    void testEscolherMedicoAleatorioLivreNaData_MultiplosMedicosDisponiveis() {
        // Cenário: Vários médicos disponíveis na data
        var proximaSegundaAs10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);
        var medico1 = cadastrarMedico("Medico1", "medico1@voll.med", "123456", Especialidade.CARDIOLOGIA);
        var medico2 = cadastrarMedico("Medico2", "medico2@voll.med", "123457", Especialidade.CARDIOLOGIA);

        // Ação
        var medicoLivre = medicoRepository.escolherMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA, proximaSegundaAs10);

        // Verificação
        assertThat(medicoLivre).isIn(medico1, medico2);
    }
    
    
    
	private void cadastrarConsulta(Medicos medico, Paciente paciente, LocalDateTime data) {
		testEntityManager.persist(new Consulta(null, medico, paciente, data));
	}

	private Medicos cadastrarMedico(String nome, String email, String crm, Especialidade especialidade) {
	    var medico = new Medicos(dadosMedico(nome, email, crm, especialidade));
	    testEntityManager.persist(medico);
	    return medico;
	}

	private Paciente cadastrarPaciente(String nome, String email, String cpf) {
	    var paciente = new Paciente(dadosPaciente(nome, email, cpf));
	    testEntityManager.persist(paciente);
	    return paciente;
	}

	private DadosCadastroMedico dadosMedico(String nome, String email, String crm, Especialidade especialidade) {
	    return new DadosCadastroMedico(
	            nome,
	            email,
	            "61999999999",
	            crm,
	            especialidade,
	            dadosEndereco()
	    );
	}

	private DadosCadastroPaciente dadosPaciente(String nome, String email, String cpf) {
	    return new DadosCadastroPaciente(
	            nome,
	            email,
	            "61999999999",
	            cpf,
	            dadosEndereco()
	    );
	}

	private DadosEndereco dadosEndereco() {
	    return new DadosEndereco(
	            "rua xpto",
	            "bairro",
	            "00000000",
	            "Brasilia",
	            "DF",
	            null,
	            null
	    );
	}
}
