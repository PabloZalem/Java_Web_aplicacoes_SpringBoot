package com.zalempablo.javaspring.javaspring.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.zalempablo.javaspring.javaspring.entities.Usuario;

@Service
public class TokenService {
	
	@Value("${api.security.token.secret}")
	private String secreto;

	private static final String ISSUER = "API Voll.med";

	public String gerarToken(Usuario usuario) {
	    try {
	        var algoritmo = Algorithm.HMAC256(secreto);
	        return JWT.create()
	                .withIssuer(ISSUER)
	                .withSubject(usuario.getLogin())
	                .withExpiresAt(dataExpiracao())
	                .sign(algoritmo);
	    } catch (JWTCreationException exception){
	        throw new RuntimeException("erro ao gerar token jwt", exception);
	    }
	}

	public String getSubject(String tokenJWT) {
	    try {
	        var algoritmo = Algorithm.HMAC256(secreto);
	        return JWT.require(algoritmo)
	                .withIssuer(ISSUER)
	                .build()
	                .verify(tokenJWT)
	                .getSubject();
	    } catch (JWTVerificationException exception) {
	        throw new RuntimeException("Token JWT inválido ou expirado!" + tokenJWT);
	    }
	}

	private Instant dataExpiracao() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
	}
}
