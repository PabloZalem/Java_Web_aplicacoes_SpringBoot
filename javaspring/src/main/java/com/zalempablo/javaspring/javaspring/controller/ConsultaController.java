package com.zalempablo.javaspring.javaspring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zalempablo.javaspring.javaspring.service.AgendaDeConsulta;
import com.zalempablo.javaspring.javaspring.service.DadosAgendamentoConsulta;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("consultas")
@SecurityRequirement(name="bearer-key")
public class ConsultaController {
	/*
	 Dependency Inversion Principle (Princípio da Inversão de Dependência):
	A classe depende de uma interface (AgendaDeConsulta), e essa dependência é injetada por meio de injeção de dependência.
	 */
	
	@Autowired
	private AgendaDeConsulta agendaDeConsulta;
	
	
	/*
	 Single Responsibility Principle (Princípio da Responsabilidade Única):
	A classe parece ter uma única responsabilidade: agendar consultas, conforme indicado pelo método agendar.
	 */
	@PostMapping
	@Transactional
	public ResponseEntity agendar(@RequestBody @Valid DadosAgendamentoConsulta dados) {
		var dto = agendaDeConsulta.agendar(dados);
		return ResponseEntity.ok(dto);
	}
}
