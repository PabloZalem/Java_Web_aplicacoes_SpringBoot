package com.zalempablo.javawebproject.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoApi {
	
	public String obterDados(String endereco) {
		//Endereco para buscar o endereco
		
		//Cliente
        HttpClient client = HttpClient.newHttpClient();
        
        //Criacao de endereco para poder enviar a requisicao
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endereco))
                .build();
        
        //Resposta que vou ter
        HttpResponse<String> response = null;
        try {
            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //DEVOLVER O CORPO DA RESPOSTA
        String json = response.body();
        return json;
    }
}
