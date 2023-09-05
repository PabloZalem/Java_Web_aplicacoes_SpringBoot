package com.zalempablo.projeto.tableafipe.service;

import java.util.List;

public interface ConverterDados {
	<T> T obterDados(String json, Class<T> classe);
	//Ele vai receber um JSON
	//Vou tentar converter no converte dados
	
	<T> List<T> obterLista(String json, Class<T> classe);
}
