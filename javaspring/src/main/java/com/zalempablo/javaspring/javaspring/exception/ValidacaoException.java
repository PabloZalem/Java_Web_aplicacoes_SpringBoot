package com.zalempablo.javaspring.javaspring.exception;

public class ValidacaoException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ValidacaoException(String string) {
		super(string);
	}
}
/*
	Princípio da Responsabilidade Única (Single Responsibility Principle - SRP):
	A classe ValidacaoException tem uma única responsabilidade, que é 
	representar exceções relacionadas a validações de regras de negócio. 
	Ela não está tentando fazer mais do que isso.
	
	Princípio Aberto/Fechado (Open/Closed Principle - OCP):
	A classe está fechada para modificação e aberta para extensão. 
	Isso significa que, para adicionar novos tipos de validações ou requisitos 
	específicos, você pode estender a classe sem modificar seu código existente.
*/