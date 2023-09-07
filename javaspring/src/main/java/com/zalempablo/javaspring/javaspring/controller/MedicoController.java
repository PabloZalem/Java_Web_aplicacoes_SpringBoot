package com.zalempablo.javaspring.javaspring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zalempablo.javaspring.javaspring.entities.Medicos;
import com.zalempablo.javaspring.javaspring.repository.MedicoRepository;
import com.zalempablo.javaspring.javaspring.service.DadosAtualizarMedico;
import com.zalempablo.javaspring.javaspring.service.DadosCadastroMedico;
import com.zalempablo.javaspring.javaspring.service.DadosListagemMedico;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("medicos")
public class MedicoController {
	
	@Autowired
	private MedicoRepository medicoRepository;
	

	@PostMapping
	@Transactional
	public void cadastrar(@RequestBody @Valid DadosCadastroMedico dados) {
		medicoRepository.save(new Medicos(dados));
	}
	
	@GetMapping
	public Page<DadosListagemMedico> list(@PageableDefault(size=10,sort={"nome"}) org.springframework.data.domain.Pageable pageable){
		return medicoRepository.findAllByAtivoTrue(pageable).map(DadosListagemMedico::new);
	}
	
	@PutMapping
	@Transactional
	public void atualizar(@RequestBody @Valid DadosAtualizarMedico dados) {
		var medico = medicoRepository.getReferenceById(dados.id());
		medico.atualizarInformacoes(dados);
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public void deletar(@PathVariable Long id) {
		var medico =  medicoRepository.getReferenceById(id);
		medico.excluir();
	}
}
