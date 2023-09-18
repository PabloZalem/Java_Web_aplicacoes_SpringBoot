package com.zalempablo.javaspring.javaspring.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zalempablo.javaspring.javaspring.service.DadosAutenticacao;

@SpringBootTest
@AutoConfigureMockMvc
public class AutenticacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private AutenticacaoController autenticacaoController;
    
    @Autowired
    private AuthenticationManager authenticationManager;

    
    @Test
    @WithMockUser // Simula um usuário autenticado
    public void testEfetuarLogin() throws Exception {
        DadosAutenticacao dadosAutenticacao = new DadosAutenticacao("ana.souza@voll.med", "123456");

        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                .content(asJsonString(dadosAutenticacao))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").exists());
    }

    // Método auxiliar para converter objeto em JSON
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    
    @Test
    public void testEfetuarLoginWithInvalidData() {
        // Criar uma instância do AutenticacaoController
        AutenticacaoController autenticacaoController = new AutenticacaoController();

        // Preparar dados de autenticação inválidos
        DadosAutenticacao dadosAutenticacao = new DadosAutenticacao("ana.souzavoll.med", "123456");

        // Executar o método a ser testado
        ResponseEntity responseEntity = autenticacaoController.efetuarLogin(dadosAutenticacao);

        // Verificar se o resultado está correto (espera-se um erro 400)
        assertEquals(400, responseEntity.getStatusCodeValue());
    }
}
