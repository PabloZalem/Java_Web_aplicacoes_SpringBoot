package com.zalempablo.javaspring.javaspring.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.zalempablo.javaspring.javaspring.entities.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Page<Paciente> findAllByAtivoTrue(Pageable paginacao);
   
    @Query("""
    		select p.ativo
			from Paciente p
			where 
			p.id = :id
    		""")
	Boolean findAtivoById(Long id);
}
