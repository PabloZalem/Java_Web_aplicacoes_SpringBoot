package com.zalempablo.javaspring.javaspring.controller;

import com.zalempablo.javaspring.javaspring.service.DadosEndereco;


public record DadosCadastroMedico(String nome,
								String email,
								String crm,
								Especialidade especialidade,
								DadosEndereco endereco) {

}
