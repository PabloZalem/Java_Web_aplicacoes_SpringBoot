package com.zalempablo.javaspring.javaspring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zalempablo.javaspring.javaspring.entities.Usuario;
import com.zalempablo.javaspring.javaspring.security.TokenService;
import com.zalempablo.javaspring.javaspring.service.DadosAutenticacao;
import com.zalempablo.javaspring.javaspring.service.DadosTokenJWT;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {
	//(AuthenticationManager e TokenService), seguindo o DIP.
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private TokenService tokenService;
	
	@PostMapping
	public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacao dadosAutenticacao) {
		try {
			var authenticationToken = new UsernamePasswordAuthenticationToken(dadosAutenticacao.login(),
					dadosAutenticacao.senha());
			var authentication = authenticationManager.authenticate(authenticationToken);

			var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());

			return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
		} catch (Exception exception) {
			exception.printStackTrace();
			return ResponseEntity.badRequest().body(exception.getMessage());
		}
	}
}
