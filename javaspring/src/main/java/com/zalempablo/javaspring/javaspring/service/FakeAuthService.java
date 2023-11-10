package com.zalempablo.javaspring.javaspring.service;

import java.util.Base64;

import org.springframework.stereotype.Service;

@Service
public class FakeAuthService {
	
	/*
	 	Princípio da Responsabilidade Única (Single Responsibility Principle - SRP):
	 	 Este método está envolvido em gerar um token com base em um par de nome de usuário e senha
	 */
    public String generateToken(String username, String password) {
        // Verificar se o login e a senha são válidos (coloque sua lógica de autenticação aqui)
        if ("ana.souza@voll.med".equals(username) && "123456".equals(password)) {
            // Se o login e senha forem válidos, gere um token simples
            String tokenData = username + ":" + password;
            byte[] tokenBytes = tokenData.getBytes();
            String token = Base64.getEncoder().encodeToString(tokenBytes);

            return token;
        } else {
            // Se o login e senha não forem válidos, retorne null ou uma mensagem de erro
            return null; // Ou lançar uma exceção, dependendo da sua lógica de autenticação
        }
    }
}

