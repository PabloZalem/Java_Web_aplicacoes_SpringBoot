package com.zalempablo.javaspring.javaspring.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class DadosAutenticacaoTest {

    @Test
    public void testConstrutor() {
        DadosAutenticacao dados = new DadosAutenticacao("usuario", "senha");
        assertEquals("usuario", dados.login());
        assertEquals("senha", dados.senha());
    }

    @Test
    public void testEquals() {
        DadosAutenticacao dados1 = new DadosAutenticacao("usuario1", "senha1");
        DadosAutenticacao dados2 = new DadosAutenticacao("usuario1", "senha1");
        DadosAutenticacao dados3 = new DadosAutenticacao("usuario2", "senha2");

        assertEquals(dados1, dados2); // Deve ser igual
        assertNotEquals(dados1, dados3); // Deve ser diferente
    }

    @Test
    public void testHashCode() {
        DadosAutenticacao dados1 = new DadosAutenticacao("usuario1", "senha1");
        DadosAutenticacao dados2 = new DadosAutenticacao("usuario1", "senha1");
        DadosAutenticacao dados3 = new DadosAutenticacao("usuario2", "senha2");

        assertEquals(dados1.hashCode(), dados2.hashCode()); // Deve ser igual
        assertNotEquals(dados1.hashCode(), dados3.hashCode()); // Deve ser diferente
    }
}

