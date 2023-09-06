package com.zalempablo.javaspring.javaspring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zalempablo.javaspring.javaspring.entities.Medicos;
import com.zalempablo.javaspring.javaspring.repository.MedicoRepository;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("medicos")
public class MedicoController {
	
	@Autowired
	private MedicoRepository medicoRepository;
	

	@PostMapping
	@Transactional
	public void cadastrar(@RequestBody DadosCadastroMedico dados) {
		medicoRepository.save(new Medicos(dados));
	}
	
	
}
