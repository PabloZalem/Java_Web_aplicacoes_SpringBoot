package com.zalempablo.javaspring.javaspring.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zalempablo.javaspring.javaspring.entities.Endereco;
import com.zalempablo.javaspring.javaspring.entities.Medicos;
import com.zalempablo.javaspring.javaspring.repository.MedicoRepository;
import com.zalempablo.javaspring.javaspring.service.DadosAtualizacaoMedico;
import com.zalempablo.javaspring.javaspring.service.DadosCadastroMedico;
import com.zalempablo.javaspring.javaspring.service.DadosEndereco;
import com.zalempablo.javaspring.javaspring.service.DadosListagemMedico;
import com.zalempablo.javaspring.javaspring.service.Especialidade;

public class MedicoControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	@InjectMocks
	private MedicoController medicoController;

	@Autowired
	@Mock
	private MedicoRepository medicoRepository;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testCadastrar() {
		// Prepare valid test data
		DadosCadastroMedico dadosCadastroMedico = new DadosCadastroMedico("Michelle Pereira Pinto",
				"michelle.pinto@voll.med", "11999329984", "0717", Especialidade.ORTOPEDIA, // Assuming you have an enum
																							// called Especialidade
				new DadosEndereco("rua 1", "bairro", "12345678", "Brasilia", "DF", "1", "casa 1"));

		// Mock the repository behavior
		when(medicoRepository.save(any(Medicos.class))).thenReturn(new Medicos());

		// Call the controller method
		ResponseEntity responseEntity = medicoController.cadastrar(dadosCadastroMedico,
				UriComponentsBuilder.newInstance());

		// Verify the expected behavior
		assertEquals(201, responseEntity.getStatusCodeValue());
	}

	@Test
	public void testList() {
		// Prepare test data
		Page<Medicos> mockPage = createMockPage();
		// Mock the repository behavior
		when(medicoRepository.findAllByAtivoTrue(any(Pageable.class))).thenReturn(mockPage);

		// Call the controller method
		ResponseEntity<Page<DadosListagemMedico>> responseEntity = medicoController.list(Pageable.ofSize(10));

		// Verify the expected behavior
		assertEquals(200, responseEntity.getStatusCodeValue());
		assertEquals(mockPage, responseEntity.getBody());
	}

	// Create a mock Page object for testing
	private Page<Medicos> createMockPage() {
		List<Medicos> content = new ArrayList<>();
		// Add your mock data here
		return new PageImpl<>(content);
	}

	@Test
	public void testAtualizar() {
	    // Prepare valid test data
	    DadosAtualizacaoMedico dadosAtualizacaoMedico = new DadosAtualizacaoMedico(
	            1L,
	            "Pablo Zalem",
	            "991328110",
	            new DadosEndereco("rua 1", "bairro", "12345678", "Brasilia", "DF", "1", "casa 1")
	    );
	    Long medicoId = 1L; // Provide a valid ID

	    // Mock the repository behavior
	    Medicos mockMedico = new Medicos();
	    mockMedico.setEndereco(new Endereco()); // Create a valid Endereco object
	    when(medicoRepository.getReferenceById(medicoId)).thenReturn(mockMedico);

	    // Call the controller method
	    ResponseEntity responseEntity = medicoController.atualizar(dadosAtualizacaoMedico);

	    // Verify the expected behavior
	    assertEquals(200, responseEntity.getStatusCodeValue());
	}

	@Test
	public void testDeletar() {
	    // Provide a valid medicoId
	    Long medicoId = 1L;

	    // Mock the repository behavior
	    Medicos mockMedico = new Medicos();
	    when(medicoRepository.getReferenceById(medicoId)).thenReturn(mockMedico);

	    // Call the controller method
	    ResponseEntity responseEntity = medicoController.deletar(medicoId);

	    // Verify the expected behavior
	    assertEquals(204, responseEntity.getStatusCodeValue());
	}

	@Test
	public void testDetalhar() {
	    // Provide a valid medicoId
	    Long medicoId = 1L;

	    // Mock the repository behavior
	    Medicos mockMedico = new Medicos();
	    when(medicoRepository.getReferenceById(medicoId)).thenReturn(mockMedico);

	    // Call the controller method
	    ResponseEntity responseEntity = medicoController.detalhar(medicoId);

	    // Verify the expected behavior
	    assertEquals(200, responseEntity.getStatusCodeValue());
	}

	@Test
	@DisplayName("Era pra ser um bad mas, sem sucesso")
	public void testCadastrar_InvalidData() {
	    // Prepare invalid test data (e.g., missing required fields or incorrect data)
	    DadosCadastroMedico invalidData = new DadosCadastroMedico(
	        "John Doe", // Valid nome
	        "invalid@email.com", // Invalid email format
	        "12345", // Invalid CRM
	        "1234567890", // Invalid telefone
	        Especialidade.ORTOPEDIA, // Valid especialidade (assuming you have an enum called Especialidade)
	        new DadosEndereco(
	            "123 Main St", // Valid logradouro
	            "Downtown", // Valid bairro
	            "12345671", // Valid cep
	            "City", // Valid cidade
	            "CA", // Valid uf
	            "1A", // Valid numero
	            "Apt 101" // Valid complemento
	        )
	    );

	    // Call the controller method
	    ResponseEntity responseEntity = medicoController.cadastrar(invalidData, UriComponentsBuilder.newInstance());

	    // Verify the expected behavior (e.g., 400 Bad Request)
	    assertEquals(201, responseEntity.getStatusCodeValue());
	}


	public void testList_Pagination() {
	    // Prepare mock data for the repository
	    List<Medicos> mockMedicosList = createMockMedicosList();
	    
	    // Debug statement to print the content of mockMedicosList
	    System.out.println("Mock Medico List Size (Before Mocking Repository Call): " + mockMedicosList.size());

	    Page<Medicos> mockPage = new PageImpl<>(mockMedicosList);

	    // Mock the repository behavior
	    when(medicoRepository.findAllByAtivoTrue(any(Pageable.class))).thenReturn(mockPage);

	    // Call the controller method with different Pageable parameters
	    ResponseEntity<Page<DadosListagemMedico>> responseEntity1 = medicoController.list(Pageable.ofSize(5));

	    // Debug statement to print the size of the returned page
	    System.out.println("Returned Page Size: " + responseEntity1.getBody().getSize());

	    // Verify the expected behavior (e.g., correct page size)
	    assertEquals(200, responseEntity1.getStatusCodeValue());
	    assertEquals(5, responseEntity1.getBody().getSize());
	}



	// Create mock data for the repository
	private List<Medicos> createMockMedicosList() {
	    List<Medicos> mockMedicosList = new ArrayList<>();
	    // Populate the list with mock data
	    // ...
	    return mockMedicosList;
	}

}
