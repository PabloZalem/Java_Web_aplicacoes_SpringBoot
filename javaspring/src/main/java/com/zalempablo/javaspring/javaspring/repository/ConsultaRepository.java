package com.zalempablo.javaspring.javaspring.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zalempablo.javaspring.javaspring.entities.Consulta;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

	boolean existsByPacienteIdAndDataBetween(Long paciente, LocalDateTime primeiroHorario, LocalDateTime ultimoHorario);
	
	boolean existsByMedicosIdAndData(Long idMedico, LocalDateTime dateTime);


}
