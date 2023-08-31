package com.zalempablo.javawebproject.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record InforacoesDaSerie(@JsonAlias("Title") String titulo, 
							@JsonAlias("totalSeasons") Integer temporadas, 
							@JsonAlias("imdbRating") String avaliacao) {
	
}
