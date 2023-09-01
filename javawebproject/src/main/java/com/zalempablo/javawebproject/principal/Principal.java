package com.zalempablo.javawebproject.principal;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.zalempablo.javawebproject.model.InformacoesDaSerie;
import com.zalempablo.javawebproject.model.InformacoesDaTemporada;
import com.zalempablo.javawebproject.model.InformacoesDoEpisodio;
import com.zalempablo.javawebproject.service.ConsumoApi;
import com.zalempablo.javawebproject.service.ConverterDados;

public class Principal {
	
	private Scanner sc = new Scanner(System.in);
	
	private final String ENDERECO = "https://www.omdbapi.com/?t=";
	private final String APIKEY = "&apikey=6585022c";
	private ConsumoApi consumoApi = new ConsumoApi();
	private ConverterDados converterDados = new ConverterDados();
	
	public void exibirMenu() {
		System.out.println("Digite o nome da série: ");
		var nomeSerie  = sc.nextLine();
		var json = consumoApi.obterDados(ENDERECO + nomeSerie.replace("", "+") + APIKEY);
		
		InformacoesDaSerie informacoesDaSerie = converterDados.obterDados(json, InformacoesDaSerie.class);
		System.out.println(informacoesDaSerie);
		
		InformacoesDoEpisodio informacoesDoEpisodio = converterDados.obterDados(json, InformacoesDoEpisodio.class);
		System.out.println(informacoesDoEpisodio);
		
		List<InformacoesDaTemporada> list = new ArrayList<>();		
		for (int i = 0; i <= informacoesDaSerie.temporadas(); i++) {			
		json = consumoApi.obterDados(ENDERECO + nomeSerie.replace("", "+") +"&season="+ i + APIKEY);
		InformacoesDaTemporada informacoesDaTemporada = converterDados.obterDados(json, InformacoesDaTemporada.class);
		list.add(informacoesDaTemporada);
		}
		 list.forEach(System.out::println);
		 list.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));
		 //list.forEach(System.out::println); isso é igual a list.forEach(t -> System.out.println(t));
	}
}
