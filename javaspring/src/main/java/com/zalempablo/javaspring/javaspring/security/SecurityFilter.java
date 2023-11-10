package com.zalempablo.javaspring.javaspring.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.zalempablo.javaspring.javaspring.repository.UsuarioRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter{
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		var tokenJWT = recuperarToken(request);
		
		if(tokenJWT != null) {
			var subject = tokenService.getSubject(tokenJWT);
			var usuario = usuarioRepository.findByLogin(subject);
			var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		
		filterChain.doFilter(request, response);
	}

	private String recuperarToken(HttpServletRequest request) {
	    var authorizationHeader = request.getHeader("Authorization");
	    if (authorizationHeader != null) {
	    	return authorizationHeader.replace("Bearer ", "").trim();
	    }

	    return null;
	}	
}

/*
 	S - Single Responsibility Principle (Princípio da Responsabilidade Única):
	A classe parece ter uma única responsabilidade, que é filtrar as solicitações e 
	realizar a autenticação com base no token JWT. Isso está alinhado com o 
	princípio S do SOLID.
	
	O - Open/Closed Principle (Princípio Aberto/Fechado):
	A classe é uma subclasse de OncePerRequestFilter, Ela parece estar fechada 
	para modificação (não há extensões ou alterações importantes no código-fonte 
	fornecido), mas aberta para extensões (pode ser estendida para adicionar 
	mais funcionalidades). Portanto, em geral, parece estar alinhada com o princípio O.
	
	L - Liskov Substitution Principle (Princípio da Substituição de Liskov):
	A classe parece seguir esse princípio, pois herda de OncePerRequestFilter 
	e substitui o método doFilterInternal. Isso é consistente com o princípio de 
	substituição de Liskov.
	
	D - Dependency Inversion Principle (Princípio da Inversão de Dependência):
	A classe usa injeção de dependência para obter instâncias de TokenService 
	e UsuarioRepository. Isso é consistente com o princípio da inversão de 
	dependência, pois a classe depende de abstrações (TokenService e 
	UsuarioRepository) em vez de implementações concretas.
 */
