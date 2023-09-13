package com.zalempablo.javaspring.javaspring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.zalempablo.javaspring.javaspring.entities.Medicos;
import com.zalempablo.javaspring.javaspring.repository.MedicoRepository;
import com.zalempablo.javaspring.javaspring.service.DadosDetalhamentoMedico;
import com.zalempablo.javaspring.javaspring.service.DadosAtualizacaoMedico;
import com.zalempablo.javaspring.javaspring.service.DadosCadastroMedico;
import com.zalempablo.javaspring.javaspring.service.DadosListagemMedico;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("medicos")
@SecurityRequirement(name="bearer-key")
public class MedicoController {
	
	@Autowired
	private MedicoRepository medicoRepository;
	

	@PostMapping
	@Transactional
	public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedico dados, 
			UriComponentsBuilder uriComponentsBuilder) {
		var medico = new Medicos(dados);
		medicoRepository.save(medico);
		
		var uri = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
		
		return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
	}
	/*public void cadastrar(@RequestBody @Valid DadosCadastroMedico dados) {
		medicoRepository.save(new Medicos(dados));
	}*/
	
	@GetMapping
	public ResponseEntity<Page<DadosListagemMedico>> list(@PageableDefault(size=10,sort={"nome"}) org.springframework.data.domain.Pageable pageable){
		var page = medicoRepository.findAllByAtivoTrue(pageable).map(DadosListagemMedico::new);
		return ResponseEntity.ok(page);
	}
	/*	public Page<DadosListagemMedico> list(@PageableDefault(size=10,sort={"nome"}) org.springframework.data.domain.Pageable pageable){
		return medicoRepository.findAllByAtivoTrue(pageable).map(DadosListagemMedico::new);
	}*/
	
	@PutMapping
	@Transactional
	public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados) {
		var medico = medicoRepository.getReferenceById(dados.id());
		medico.atualizarInformacoes(dados);		
		return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
	}
	/*public void atualizar(@RequestBody @Valid DadosAtualizarMedico dados) {
		var medico = medicoRepository.getReferenceById(dados.id());
		medico.atualizarInformacoes(dados);
	}*/
	
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity deletar(@PathVariable Long id) {
		var medico =  medicoRepository.getReferenceById(id);
		medico.excluir();
		return ResponseEntity.noContent().build();
	}
	/*public void deletar(@PathVariable Long id) {
	var medico =  medicoRepository.getReferenceById(id);
	medico.excluir();
}*/
	
	@GetMapping("/{id}")
	@Transactional
	public ResponseEntity detalhar(@PathVariable Long id) {
		var medico =  medicoRepository.getReferenceById(id);
		return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
	}
	
	/*@GetMapping("/{id}")
	@Secured("ROLE_ADMIN")
	public ResponseEntity detalhar(@PathVariable Long id) {
    var medico = repository.getReferenceById(id);
    return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
}*/
}
