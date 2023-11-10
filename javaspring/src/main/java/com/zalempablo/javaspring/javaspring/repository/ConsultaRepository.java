package com.zalempablo.javaspring.javaspring.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zalempablo.javaspring.javaspring.entities.Consulta;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

	boolean existsByPacienteIdAndDataBetween(Long paciente, LocalDateTime primeiroHorario, LocalDateTime ultimoHorario);
	
	boolean existsByMedicosIdAndData(Long idMedico, LocalDateTime dateTime);


}
/*Princípio da Responsabilidade Única (Single Responsibility Principle - SRP):
A interface ConsultaRepository tem a responsabilidade de definir métodos de 
acesso a dados relacionados às consultas. Essa responsabilidade única está 
alinhada com o SRP.

Princípio Aberto/Fechado (Open/Closed Principle - OCP):
A interface ConsultaRepository é uma extensão da interface JpaRepository. 
Ela é fechada para modificação, mas pode ser estendida adicionando novos 
métodos conforme necessário, o que está em conformidade com o princípio OCP.
*/