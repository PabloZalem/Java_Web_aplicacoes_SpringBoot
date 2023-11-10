package com.zalempablo.javaspring.javaspring.repository;

import java.time.LocalDateTime;

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
                    select c.medicos.id from Consulta c
                    where
                    c.data = :data
            )
            order by rand()
            limit 1
            """)
	Medicos escolherMedicoAleatorioLivreNaData(Especialidade especialidade, LocalDateTime data);

	@Query("""
			select m.ativo
			from Medico m
			where 
			m.id = :id
			""")
	Boolean findAtivoById(Long id);
}
/* Princípio da Responsabilidade Única (Single Responsibility Principle - SRP):
	A interface MedicoRepository tem a responsabilidade de definir métodos de acesso 
	a dados relacionados aos médicos. Essa responsabilidade única está alinhada 
	com o SRP.
 	
 	Princípio Aberto/Fechado (Open/Closed Principle - OCP):
	A interface MedicoRepository é uma extensão da interface JpaRepository e 
	define métodos específicos para acesso a dados relacionados aos médicos. 
	Ela é fechada para modificação, mas aberta para extensão, seguindo o princípio OCP.
 */
