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

import com.zalempablo.javaspring.javaspring.entities.Paciente;
import com.zalempablo.javaspring.javaspring.repository.PacienteRepository;
import com.zalempablo.javaspring.javaspring.service.DadosAtualizacaoPaciente;
import com.zalempablo.javaspring.javaspring.service.DadosCadastroPaciente;
import com.zalempablo.javaspring.javaspring.service.DadosDetalhamentoPaciente;
import com.zalempablo.javaspring.javaspring.service.DadosListagemPaciente;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("pacientes")
@SecurityRequirement(name="bearer-key")
public class PacienteController {

	/*
	 	Dependency Inversion Principle (Princípio da Inversão de Dependência):
		A classe depende de uma abstração (PacienteRepository), e essa dependência é injetada por meio de injeção de dependência.
	 */
    @Autowired
    private PacienteRepository repository;

    /*
     	Single Responsibility Principle (Princípio da Responsabilidade Única):
		O método cadastrar parece ter uma única responsabilidade, que é cadastrar um novo paciente.
     */
    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroPaciente dados, UriComponentsBuilder uriBuilder) {
        var paciente = new Paciente(dados);
        repository.save(paciente);

        var uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoPaciente(paciente));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemPaciente>> listar(@PageableDefault(size=10,sort={"nome"}) org.springframework.data.domain.Pageable pageable) {
        var page = repository.findAllByAtivoTrue(pageable).map(DadosListagemPaciente::new);
        return ResponseEntity.ok(page);
    }

    
    /*
     	Single Responsibility Principle (Princípio da Responsabilidade Única):
		O método cadastrar parece ter uma única responsabilidade, que é cadastrar um novo paciente.
     */
    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoPaciente dados) {
        var paciente = repository.getReferenceById(dados.id());
        paciente.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {
        var paciente = repository.getReferenceById(id);
        paciente.excluir();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        var paciente = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
    }


}
