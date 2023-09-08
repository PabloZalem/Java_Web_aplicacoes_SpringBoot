package com.zalempablo.javaspring.javaspring.entities;

import com.zalempablo.javaspring.javaspring.service.DadosAtualizarMedico;
import com.zalempablo.javaspring.javaspring.service.DadosCadastroMedico;
import com.zalempablo.javaspring.javaspring.service.Especialidade;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "Medico")
@Table(name = "medicos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medicos {
	
	public Medicos(DadosCadastroMedico dados) {
		this.ativo = true;
		this.nome = dados.nome();
		this.email = dados.email();
		this.telefone = dados.telefone();
		this.crm = dados.crm();
		this.especialidade = dados.especialidade();
		this.endereco = new Endereco(dados.endereco());
	}
	
	public Medicos(Long id, String nome, String email, String crm, Especialidade especialidade) {
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.crm = crm;
		this.especialidade = especialidade;
	}
	
	public Medicos() {
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String email;
	private String crm;
	private String telefone;
	
	@Enumerated(EnumType.STRING)
	private Especialidade especialidade;
	
	@Embedded
	private Endereco endereco;

	private Boolean ativo;
	
	public String getNome() {
		return nome;
	}

	public String getEmail() {
		return email;
	}

	public String getCrm() {
		return crm;
	}

	public Especialidade getEspecialidade() {
		return especialidade;
	}

	public Long getId() {
		return id;
	}
	
	public Endereco getEndereco() {
		return endereco;
	}
	
	public String getTelefone() {
		return telefone;
	}

	public void atualizarInformacoes(@Valid DadosAtualizarMedico dados) {
		if(dados.nome() != null) {
			this.nome = dados.nome();	
		}
		if(dados.telefone() != null) {
			this.telefone = dados.telefone();
		}
		if(dados.dadosEndereco() != null) {
			this.endereco.atualizarInformacoes(dados.dadosEndereco());
		}
	}

	public void excluir() {
		this.ativo = false;
	}
}
