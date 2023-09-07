package com.zalempablo.javaspring.javaspring.service;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizarMedico(@NotNull Long id,
									String nome,
									String telefone,
									DadosEndereco dadosEndereco
								) {

}
