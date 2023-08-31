package com.zalempablo.javawebproject.service;

public interface I_ConverteDados {
	
	<T> T obterDados(String json, Class<T> classe);
	//Ele vai receber um JSON
	//Vou tentar converter no converte dados
}	
