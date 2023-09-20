package com.zalempablo.javaspring.javaspring.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.zalempablo.javaspring.javaspring.service.DadosCadastroPaciente;
import com.zalempablo.javaspring.javaspring.service.DadosEndereco;
import com.zalempablo.javaspring.javaspring.service.DadosErroDeValidaco;

public class TratamentoDeErroTest {

    @InjectMocks
    private TratamentoDeErro tratamentoDeErro;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testTratarErro404() {
        ResponseEntity response = tratamentoDeErro.tratarErro404();
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void testTratarErro400_MethodArgumentNotValidException_DadosCadastroPacienteInvalido() {
        // Criar uma instância de DadosCadastroPaciente com dados inválidos
        DadosCadastroPaciente dadosInvalidos = new DadosCadastroPaciente(
            "", // Nome em branco, violando a validação @NotBlank
            "email-invalido", // E-mail inválido, violando a validação @Email
            "(11) 1234-5678", // Telefone inválido
            "12345", // CPF inválido
            new DadosEndereco("", "123", "Oz", "Centro", "São Paulo", "SP", "01234567") // Endereço com logradouro em branco
        );

        // Criar um BindingResult simulado com os erros de validação
        BindingResult bindingResult = new BeanPropertyBindingResult(dadosInvalidos, "dadosCadastroPaciente");
        bindingResult.addError(new FieldError("dadosCadastroPaciente", "nome", "O campo 'nome' é obrigatório."));
        bindingResult.addError(new FieldError("dadosCadastroPaciente", "email", "E-mail inválido."));
        bindingResult.addError(new FieldError("dadosCadastroPaciente", "telefone", "Telefone inválido."));
        bindingResult.addError(new FieldError("dadosCadastroPaciente", "cpf", "CPF inválido."));
        bindingResult.addError(new FieldError("dadosCadastroPaciente.endereco", "logradouro", "Logradouro em branco."));

        // Simular a exceção MethodArgumentNotValidException com o BindingResult
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);

        // Chamar o método para tratar o erro
        ResponseEntity response = tratamentoDeErro.tratarErro400(ex);

        // Verificar se a resposta tem o status HTTP 400
        assertEquals(400, response.getStatusCodeValue());

        // Verificar o corpo da resposta para conter informações sobre os erros de validação
        List<DadosErroDeValidaco> errorList = (List<DadosErroDeValidaco>) response.getBody();
        assertNotNull(errorList);
        assertTrue(errorList.stream().anyMatch(error -> error.campo().equals("nome")));
        assertTrue(errorList.stream().anyMatch(error -> error.mensagem().equals("O campo 'nome' é obrigatório.")));
        
     }
    

    @Test
    public void testTratarErroDaRegraDeNegocio() {
        ValidacaoException ex = new ValidacaoException("Erro de validação");

        ResponseEntity response = tratamentoDeErro.tratarErroDaRegraDeNegocio(ex);
        assertEquals(400, response.getStatusCodeValue());
        // Verifique o corpo da resposta, se necessário
    }

    @Test
    public void testTratarErro401() {
        ResponseEntity response = tratamentoDeErro.tratarErro401(new RuntimeException(), null);
        assertEquals(401, response.getStatusCodeValue());
        // Verifique o corpo da resposta, se necessário
    }
}
