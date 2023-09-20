package com.zalempablo.javaspring.javaspring.service;

import org.junit.jupiter.api.Test;
import org.springframework.validation.FieldError;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DadosErroDeValidacoTest {

	@Test
    public void testConstrutorComFieldError() {
        // Crie um objeto FieldError de exemplo com os dados corretos
        FieldError fieldError = new FieldError("nome", "nome do campo", "Mensagem de erro");

        // Crie um objeto DadosErroDeValidaco usando o construtor com FieldError
        DadosErroDeValidaco dadosErro = new DadosErroDeValidaco(fieldError);

        // Verifique se os valores do objeto criado correspondem aos valores esperados
        assertEquals("nome do campo", dadosErro.campo());
        assertEquals("Mensagem de erro", dadosErro.mensagem());
    }

    @Test
    public void testConstrutorComValoresDiretos() {
        // Crie um objeto DadosErroDeValidaco diretamente
        DadosErroDeValidaco dadosErro = new DadosErroDeValidaco("campo", "Mensagem de erro");

        // Verifique se os valores do objeto criado correspondem aos valores esperados
        assertEquals("campo", dadosErro.campo());
        assertEquals("Mensagem de erro", dadosErro.mensagem());
    }
}
