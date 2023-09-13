package com.zalempablo.javaspring.javaspring.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.zalempablo.javaspring.javaspring.service.AgendaDeConsulta;
import com.zalempablo.javaspring.javaspring.service.DadosAgendamentoConsulta;
import com.zalempablo.javaspring.javaspring.service.DadosDetalhamentoConsulta;
import com.zalempablo.javaspring.javaspring.service.Especialidade;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class ConsultaControllerTest {
	
	@Autowired
	private MockMvc mockMvc; 
	
	@Autowired
	private JacksonTester<DadosAgendamentoConsulta> dadosAgendamentJacksonTester;
	
	@Autowired
	private JacksonTester<DadosDetalhamentoConsulta> dadosDetalhamentoJacksonTester;
	
	@MockBean
	private AgendaDeConsulta agendaDeConsulta;
	
	@Test
	@DisplayName("Deveria devolver 400 quando informacoes sao invalidas")
	@WithMockUser
	void agendar_cenario1() throws Exception {
		var response = mockMvc.perform(post("/consultas"))
				.andReturn().getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	@DisplayName("Deveria devolver 200 quando informacoes sao validas")
	@WithMockUser
	void agendar_cenario2() throws Exception {
		var data = LocalDateTime.now().plusHours(1);
		var especialidade = Especialidade.CARDIOLOGIA;
		var dadosDetalhamento = new DadosDetalhamentoConsulta(null, 2l, 5l, data);
		
		when(agendaDeConsulta.agendar(any())).thenReturn(dadosDetalhamento); 
		
		var response = mockMvc.perform(
				post("/consultas")
				.contentType(MediaType.APPLICATION_JSON)
				.content(dadosAgendamentJacksonTester.write(
						new DadosAgendamentoConsulta(2l, 5l, data, especialidade)
						).getJson())
				)
				.andReturn().getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		
		
		var jsonEsperado = dadosDetalhamentoJacksonTester.write(
					dadosDetalhamento
				).getJson();
		
		assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
	}
}
