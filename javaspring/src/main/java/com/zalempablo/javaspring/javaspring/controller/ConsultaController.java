package com.zalempablo.javaspring.javaspring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zalempablo.javaspring.javaspring.service.AgendaDeConsulta;
import com.zalempablo.javaspring.javaspring.service.DadosAgendamentoConsulta;
import com.zalempablo.javaspring.javaspring.service.DadosDetalhamentoConsulta;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("consultas")
@SecurityRequirement(name="bearer-key")
public class ConsultaController {
	
	@Autowired
	private AgendaDeConsulta agendaDeConsulta;
	
	@PostMapping
	@Transactional
	public ResponseEntity agendar(@RequestBody @Valid DadosAgendamentoConsulta dados) {
		var dto = agendaDeConsulta.agendar(dados);
		return ResponseEntity.ok(dto);
	}
}
