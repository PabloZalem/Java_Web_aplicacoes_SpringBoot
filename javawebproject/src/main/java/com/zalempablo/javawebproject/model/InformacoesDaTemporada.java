package com.zalempablo.javawebproject.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record InformacoesDaTemporada(@JsonAlias("Season") Integer numero,
									@JsonAlias("Episodes") List<InformacoesDoEpisodio> episodios){
	
}
