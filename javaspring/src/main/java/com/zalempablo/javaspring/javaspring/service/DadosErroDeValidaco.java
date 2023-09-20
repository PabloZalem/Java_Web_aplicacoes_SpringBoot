package com.zalempablo.javaspring.javaspring.service;

import org.springframework.validation.FieldError;

public record DadosErroDeValidaco(	String campo,
									String mensagem) {
	
		public DadosErroDeValidaco(FieldError error) {
			this(error.getField(), error.getDefaultMessage());
		}
}
