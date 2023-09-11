package com.zalempablo.javaspring.javaspring.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.zalempablo.javaspring.javaspring.entities.Usuario;

@Service
public class TokenService {
	
	@Value("${api.security.token.secret}")
	private String secreto;

	public String gerarToken(Usuario usuario) {
		System.out.println(secreto);
		try {
			var algoritmo = Algorithm.HMAC256(secreto);
			return JWT.create()
					.withIssuer("API medica ")
					.withSubject(usuario.getLogin())
					.withExpiresAt(dataExpericacao())
					.sign(algoritmo);
		}catch(JWTCreationException exception) {
			throw new RuntimeException("Erro ao gerar token jwt", exception);
		}
	}

	private Instant dataExpericacao() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
	}
}
