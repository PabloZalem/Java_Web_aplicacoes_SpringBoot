package com.zalempablo.javaspring.javaspring.service;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoMedico(@NotNull Long id,
									String nome,
									String telefone,
									DadosEndereco dadosEndereco
								) {

}
/*
Princípio da Responsabilidade Única (Single Responsibility Principle - SRP):
Esta classe está focada em representar os dados de atualização de um médico, 
o que é uma única responsabilidade. Portanto, ela segue o princípio SRP.

Princípio Aberto/Fechado (Open/Closed Principle - OCP):
Assim como a classe DadosAgendamentoConsulta, a classe DadosAtualizacaoMedico 
é fechada para modificação devido à sua imutabilidade. 
Ela pode ser facilmente estendida adicionando novos campos, seguindo o princípio OCP.
*/