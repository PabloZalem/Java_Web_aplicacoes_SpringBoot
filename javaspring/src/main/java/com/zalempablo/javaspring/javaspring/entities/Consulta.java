package com.zalempablo.javaspring.javaspring.entities;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "consultas")
@Entity(name = "Consulta")
//@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Consulta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "medicos")
	private Medicos medicos;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "paciente")
	private Paciente paciente;

	private LocalDateTime data;

	public Consulta() {
	}

	public Consulta(Long id, Medicos medicos, Paciente paciente, LocalDateTime data) {
		super();
		this.id = id;
		this.medicos = medicos;
		this.paciente = paciente;
		this.data = data;
	}

	public Long getId() {
		return id;
	}

	public Medicos getMedicos() {
		return medicos;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public LocalDateTime getData() {
		return data;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Consulta other = (Consulta) obj;
		return Objects.equals(id, other.id);
	}
	
	
}
