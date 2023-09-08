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
		this.logradouro = dados.logradouro();
		this.bairro = dados.bairro();
		this.cep = dados.cep();
		this.numero = dados.numero();
		this.uf = dados.uf();
		this.cidade = dados.cidade();
		this.complemento = dados.complemento();
	}
	private String logradouro;
	private String bairro;
	private String cep;
	private String numero;
	private String complemento;
	private String cidade;
	private String uf;
	
	public void atualizarInformacoes(DadosEndereco dadosEndereco) {
		if(dadosEndereco.logradouro() != null) {
			this.logradouro = dadosEndereco.logradouro();
		}
		if(dadosEndereco.bairro() != null) {
			this.bairro = dadosEndereco.bairro();
		}
		if(dadosEndereco.cep() != null) {
			this.cep = dadosEndereco.cep();
		}
		if(dadosEndereco.uf() != null) {
			this.uf = dadosEndereco.uf();
		}
		if(dadosEndereco.cidade() != null) {
			this.cidade = dadosEndereco.cidade();
		}
		if(dadosEndereco.numero() != null) {
			this.numero = dadosEndereco.numero();
		}
		if(dadosEndereco.complemento() != null) {
			this.complemento = dadosEndereco.complemento();
		}
	}
}
