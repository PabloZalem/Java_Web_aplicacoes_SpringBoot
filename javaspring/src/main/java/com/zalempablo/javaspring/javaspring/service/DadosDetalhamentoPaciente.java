package com.zalempablo.javaspring.javaspring.service;

import com.zalempablo.javaspring.javaspring.entities.Endereco;
import com.zalempablo.javaspring.javaspring.entities.Paciente;

public record DadosDetalhamentoPaciente(Long id, String nome, String email, String cpf, String telefone, Endereco endereco) {

    public DadosDetalhamentoPaciente(Paciente paciente) {
        this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getCpf(), paciente.getTelefone(), paciente.getEndereco());
    }
}
