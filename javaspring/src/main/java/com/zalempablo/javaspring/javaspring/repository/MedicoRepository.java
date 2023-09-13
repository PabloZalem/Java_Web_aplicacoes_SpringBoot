package com.zalempablo.javaspring.javaspring.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.zalempablo.javaspring.javaspring.entities.Medicos;
import com.zalempablo.javaspring.javaspring.service.Especialidade;


public interface MedicoRepository extends JpaRepository<Medicos, Long>{
	
	Page<Medicos> findAllByAtivoTrue(Pageable pageable);

	@Query("""
            select m from Medico m
            where
            m.ativo = true
            and
            m.especialidade = :especialidade
            and
            m.id not in(
                    select c.medico.id from Consulta c
                    where
                    c.data = :data
            )
            order by rand()
            limit 1
            """)
	Medicos escolherMedicoAleatorioLivreNaData(Especialidade especialidade, LocalDateTime data);

	@Query("""
			select n.ativo
			from Medico m
			where 
			n.id = :id
			""")
	Boolean findAtivoById(Long id);
}
