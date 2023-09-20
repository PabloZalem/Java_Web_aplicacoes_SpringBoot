package com.zalempablo.javaspring.javaspring.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zalempablo.javaspring.javaspring.service.DadosCadastroPaciente;
import com.zalempablo.javaspring.javaspring.service.DadosEndereco;

@SpringBootTest
@AutoConfigureMockMvc
public class PacienteControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    DadosCadastroPaciente paciente = DadosCadastroPaciente.criar(
    	    "João da Silva",
    	    "joao@example.com",
    	    "(11) 99999-9999",
    	    "123.456.789-00",
    	    new DadosEndereco("Rua das Flores", "NeverLand" ,"123", "Centro", "São Paulo", "SP", "01234567")
    	);

    
    public void testCadastrarPacienteComToken() throws Exception {
        // Simule a geração de um token de autenticação falso
        String fakeToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmEuc291emFAdm9sbC5tZWQiLCJpc3MiOiJBUEkgVm9sbC5tZWQiLCJleHAiOjE2OTUxNTMyNjF9.PhShInmuXyKx7g_hpDnUfP-joRmPqGpBJtpcI1JKs04";

        // Dados do paciente que você deseja criar
        DadosCadastroPaciente dadosPaciente = new DadosCadastroPaciente(
            "João da Silva",
            "joao@example.com",
            "(11) 99999-9999",
            "123.456.789-00",
            new DadosEndereco("rua 1", "bairro", "12345678", "Brasília", "DF", "1", "casa 1"));

        String jsonDadosPaciente = asJsonString(dadosPaciente);

        mockMvc.perform(MockMvcRequestBuilders.post("/pacientes")
                .header("Authorization", "Bearer " + fakeToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonDadosPaciente))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    public static String asJsonString(Object object) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }
         
    @Test
    public void testDadosCadastroPaciente_Valido() {
        // Criar uma instância de DadosEndereco válida
        DadosEndereco endereco = new DadosEndereco(
            "Rua das Flores","Oz", "123", "Centro", "São Paulo", "SP", "01234567"
        );

        // Criar uma instância de DadosCadastroPaciente com dados válidos
        DadosCadastroPaciente paciente = new DadosCadastroPaciente(
            "João da Silva",
            "joao@example.com",
            "(11) 99999-9999",
            "123.456.789-00",
            endereco
        );

        // Verificar se a instância foi criada com sucesso
        assertNotNull(paciente);
    }
}
