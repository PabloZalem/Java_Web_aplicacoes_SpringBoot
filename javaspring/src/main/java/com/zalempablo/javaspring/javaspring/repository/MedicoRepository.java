package com.zalempablo.javaspring.javaspring.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.zalempablo.javaspring.javaspring.entities.Medicos;


public interface MedicoRepository extends JpaRepository<Medicos, Long>{

	Page<Medicos> findAllByAtivoTrue(Pageable pageable);


}
