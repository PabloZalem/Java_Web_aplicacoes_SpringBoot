package com.zalempablo.javaspring.javaspring.entities;

import com.zalempablo.javaspring.javaspring.service.DadosEndereco;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Endereco {
	
	public Endereco() {
	}
	
	public Endereco(DadosEndereco dados) {
		this.logadouro = dados.logadouro();
		this.bairro = dados.bairro();
		this.cep = dados.cep();
		this.numero = dados.numero();
		this.uf = dados.uf();
		this.cidade = dados.cidade();
		this.complemento = dados.complemento();
	}
	private String logadouro;
	private String bairro;
	private String cep;
	private String numero;
	private String complemento;
	private String cidade;
	private String uf;
}
