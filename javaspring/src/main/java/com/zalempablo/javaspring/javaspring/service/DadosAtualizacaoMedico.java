package com.zalempablo.javaspring.javaspring.service;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoMedico(@NotNull Long id,
									String nome,
									String telefone,
									DadosEndereco dadosEndereco
								) {

}
