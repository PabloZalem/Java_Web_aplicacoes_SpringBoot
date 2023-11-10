package com.zalempablo.javaspring.javaspring.service;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

public record DadosAgendamentoConsulta(Long idMedico,
										@NotNull Long idPaciente,
										@NotNull @Future LocalDateTime data,
										Especialidade especialidade) {
}
/*Princípio da Responsabilidade Única (Single Responsibility Principle - SRP):
Esta classe está focada em representar os dados de agendamento de consulta, 
o que é uma única responsabilidade. Portanto, ela segue o princípio SRP.

Princípio Aberto/Fechado (Open/Closed Principle - OCP):
A classe é uma classe de dados imutável (record), o que significa que ela 
é fechada para modificação. Entretanto, é fácil estendê-la adicionando novos 
campos conforme necessário, seguindo o princípio OCP.
 * 
 */
