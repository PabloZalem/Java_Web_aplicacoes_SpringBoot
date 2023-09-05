package com.zalempablo.javawebproject.model;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Date;

public class Episodio {
	private Integer temporada;
	private String titulo;
    private Integer numeroDeEpisodios;
    private Double avaliacao;
    private LocalDate dataLancamento;
    
    
	public Episodio(Integer temporada, InformacoesDoEpisodio informacoesDoEpisodio) {
		super();
		this.temporada = temporada;
		this.titulo = informacoesDoEpisodio.titulo();
		this.numeroDeEpisodios = informacoesDoEpisodio.numero();
		
		try {
			this.avaliacao = Double.valueOf(informacoesDoEpisodio.avaliacao());
		}catch (NumberFormatException e) {
			this.avaliacao = 0.0;
		}
		
		try {
			this.dataLancamento = LocalDate.parse(informacoesDoEpisodio.dataLancamento());
		}catch (DateTimeParseException e) {
			this.dataLancamento = null;
		}
	}
	public Integer getTemporada() {
		return temporada;
	}
	public void setTemporada(Integer temporada) {
		this.temporada = temporada;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public Integer getNumeroDeEpisodios() {
		return numeroDeEpisodios;
	}
	public void setNumeroDeEpisodios(Integer numeroDeEpisodios) {
		this.numeroDeEpisodios = numeroDeEpisodios;
	}
	public Double getAvaliacao() {
		return avaliacao;
	}
	public void setAvaliacao(Double avaliacao) {
		this.avaliacao = avaliacao;
	}
	public LocalDate getDataLancamento() {
		return dataLancamento;
	}
	public void setDataLancamento(LocalDate dataLancamento) {
		this.dataLancamento = dataLancamento;
	}
	@Override
	public String toString() {
		return "[temporada=" + temporada + ", titulo=" + titulo + ", numeroDeEpisodios=" + numeroDeEpisodios
				+ ", avaliacao=" + avaliacao + ", dataLancamento=" + dataLancamento + "]";
	}
    
    
}
