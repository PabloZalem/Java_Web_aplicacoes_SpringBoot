package com.zalempablo.javawebproject.principal;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.zalempablo.javawebproject.model.Episodio;
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
		 
		 List<String> nomes = Arrays.asList("Pablo", "Pedro", "Michelle");
		 nomes.stream()
		 .sorted()
         .limit(3)
         .filter(n -> n.startsWith("N"))
         .map(n -> n.toUpperCase())
         .forEach(System.out::println);
		 
		 
		 List<InformacoesDoEpisodio> informacoesDoEpisodios = list.stream()
				 													.flatMap(t -> t.episodios().stream())
				 													.collect(Collectors.toList());
		 
		 informacoesDoEpisodios.stream()
		 						.sorted(Comparator.comparing(InformacoesDoEpisodio::avaliacao)
		 						.reversed())
		 						.limit(5)
		 						.forEach(System.out::println);
		 
		 informacoesDoEpisodios.stream()
		 	.filter(e -> !e.titulo().equalsIgnoreCase("N/A"))
			.sorted(Comparator.comparing(InformacoesDoEpisodio::avaliacao)
			.reversed())
			.limit(5)
			.forEach(System.out::println);
		 
		 
		 informacoesDoEpisodios.stream()
		 	.filter(e -> !e.titulo().equalsIgnoreCase("N/A"))
			.sorted(Comparator.comparing(InformacoesDoEpisodio::avaliacao)
			.reversed())
			.limit(10)
			.map(e -> e.titulo().toUpperCase())
			.forEach(System.out::println);

		 
		 informacoesDoEpisodios.stream()
		 	.filter(e -> !e.titulo().equalsIgnoreCase("N/A"))
		 	.peek(e -> System.out.println("Primeiro filtro do N/A " + e))
			.sorted(Comparator.comparing(InformacoesDoEpisodio::avaliacao)
			.reversed())
			.peek(e -> System.out.println("Ordenacao " + e))
			.limit(10)
			.peek(e -> System.out.println("Limete " + e))
			.map(e -> e.titulo().toUpperCase())
			.peek(e -> System.out.println("Mapeamento: " + e))
			.forEach(System.out::println);
		 
		 
		 List<Episodio> episodio = list.stream()
					.flatMap(t -> t.episodios().stream())
					.map(d -> new Episodio(d.numero() , d))
					.collect(Collectors.toList());	 
		 episodio.forEach(System.out::println);
		 
		 
		 System.out.println("Digite um trecho do titulo do episodio");
		 var trecho = sc.nextLine();
		 Optional<Episodio> findFirst = episodio.stream()
		 		.filter(e -> e.getTitulo().contains(trecho))
		 		.findFirst();
		 if(findFirst.isPresent()) {
			 System.out.println("Episodio Encontrado");
			 System.out.println("Temporada: " + findFirst.get().getTemporada());
		 }else {
			 System.out.println("Episodio nao encontrado");
		 }
		 
		 
		 System.out.println("Digite um trecho do titulo do episodio");
		 var trecho2 = sc.nextLine();
		 Optional<Episodio> findFirst2 = episodio.stream()
		 		.filter(e -> e.getTitulo().toUpperCase().contains(trecho2.toUpperCase()))
		 		.findFirst();
		 if(findFirst2.isPresent()) {
			 System.out.println("Episodio Encontrado");
			 System.out.println("Temporada: " + findFirst2.get().getTemporada());
		 }else {
			 System.out.println("Episodio nao encontrado");
		 }
		
		 
		 System.out.println("A partir de que ano você deseja ver os episodios: ");
		 var ano = sc.nextInt();
		 sc.nextLine();
		 
		 LocalDate localDate = LocalDate.of(ano, 1, 1);
		 
		 DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		 episodio.stream()
		     .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(localDate))
		     .forEach(e -> System.out.println(
		         "Temporada: " + e.getTemporada() +
		         " Episódio: " + e.getTitulo() +
		         " Data lançamento: " + e.getDataLancamento().format(formatador)
		     ));

		 
		Map<Integer, Double> avaliacoesPorTemporada = episodio.stream()
														.collect(Collectors.groupingBy(Episodio::getTemporada,
														Collectors.averagingDouble(Episodio::getAvaliacao)));
		System.out.println(avaliacoesPorTemporada);
		
		Map<Integer, Double> avaliacoesPorTemporadaMap = episodio.stream()
				.filter(e -> e.getAvaliacao() > 0.8)
				.collect(Collectors.groupingBy(Episodio::getTemporada,
				Collectors.averagingDouble(Episodio::getAvaliacao)));
		System.out.println(avaliacoesPorTemporadaMap);
		
		
		DoubleSummaryStatistics doubleSummaryStatistics = episodio.stream()
			.filter(e -> e.getAvaliacao() > 0.0)
			.collect(Collectors.summarizingDouble(Episodio::getAvaliacao));
		System.out.println(doubleSummaryStatistics);
		System.out.println("Media: " + doubleSummaryStatistics.getAverage());
		System.out.println("Melhor episodio: " + doubleSummaryStatistics.getMax());
		System.out.println("Pior episodio: " + doubleSummaryStatistics.getMin());
		System.out.println("Quantidade: " + doubleSummaryStatistics.getCount() );
		
	}
	
}
