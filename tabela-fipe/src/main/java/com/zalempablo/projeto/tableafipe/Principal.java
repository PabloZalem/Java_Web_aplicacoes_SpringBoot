package com.zalempablo.projeto.tableafipe;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.zalempablo.projeto.tableafipe.domain.ConvercaoDeDados;
import com.zalempablo.projeto.tableafipe.domain.Dados;
import com.zalempablo.projeto.tableafipe.domain.Modelos;
import com.zalempablo.projeto.tableafipe.domain.Veiculo;
import com.zalempablo.projeto.tableafipe.service.ConsumoApi;

public class Principal {	
	private final String BASE_URL = "https://parallelum.com.br/fipe/api/v1/";
	private ConsumoApi consumo = new ConsumoApi();
	private ConvercaoDeDados convercaoDeDados = new ConvercaoDeDados(); 
	
	public void exibirMenu() {
	     var menu = """
	             *** OPÇÕES ***
	             Carro
	             Moto
	             Caminhão
	             
	             Digite uma das opções para consulta:
	             
	             """;
	             
	         System.out.println(menu);
	         
	         Scanner leitura = new Scanner(System.in);
	         var opcao = leitura.nextLine();
	         
	         String endereco;
	         
	         if(opcao.toLowerCase().contains("carr")) {
	        	 endereco = BASE_URL + "carros/marcas";
	         }else if (opcao.toLowerCase().contains("mot")){
	        	 endereco = BASE_URL + "motos/marcas";
	         }else {
	        	 endereco = BASE_URL + "caminhoes/marcas";
	         }
	         
	         var json = consumo.obterDados(endereco);
	         System.out.println(json);
	         
	         var marcas = convercaoDeDados.obterLista(json, Dados.class);
	         marcas.stream()
	         	.sorted(Comparator.comparing(t -> t.codigo()))
	         	.forEach(System.out::println);
	         
	         System.out.println("Informe o código da marca para consulta: ");
	         var codigoMarca = leitura.nextLine();
	         
	         endereco = endereco + "/" + codigoMarca + "/modelos"; 
	         json = consumo.obterDados(endereco);
	         
	         var modeloLista = convercaoDeDados.obterDados(json, Modelos.class);
	         System.out.println("Modelos dessa marca: ");
	         modeloLista.modelos().stream()
	         				.sorted(Comparator.comparing(Dados::codigo))
	         				.forEach(System.out::println);
	         
	         System.out.println("Digite um trecho do nome do carro a ser buscado");
	         var nomeDoVeiculo = leitura.nextLine();
	         
	         
	         List<Dados> modelosFiltrados = modeloLista.modelos()
	        		 	.stream()
	        		 	.filter(m -> m.nome().toLowerCase().contains(nomeDoVeiculo.toLowerCase()))
	        		 	.collect(Collectors.toList());
	         System.out.println("Modelos Filtrados: ");
	         modelosFiltrados.forEach(System.out::println);
	         
	         System.out.println("Digite por favor o código do modelo para buscarmos os valores de avaliacao");
	         var codigoModelo = leitura.nextLine();
	         endereco = endereco + "/" + codigoModelo + "/anos";
	         json = consumo.obterDados(endereco);
	         List<Dados> anos = convercaoDeDados.obterLista(json, Dados.class);
	         
	         List<Veiculo> veiculos = new ArrayList<>();
	         for(int i = 0; i < anos.size(); i++) {
	        	 var enderecoAno = endereco + "/" + anos.get(i).codigo();
	        	 json = consumo.obterDados(enderecoAno);
	        	 Veiculo veiculo = convercaoDeDados.obterDados(json, Veiculo.class);
	        	 veiculos.add(veiculo);
	         }
	         
	         System.out.println("Todos os veiculos filtrados por ano");
	         veiculos.forEach(System.out::println);
	}
}
