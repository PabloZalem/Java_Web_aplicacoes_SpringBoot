package com.zalempablo.javaspring.javaspring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.zalempablo.javaspring.javaspring.entities.Usuario;
import com.zalempablo.javaspring.javaspring.repository.UsuarioRepository;

@Service
public class AutenticacaoService implements UserDetailsService{

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public AutenticacaoService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = (Usuario) usuarioRepository.findByLogin(username);
		if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado");
		}
		return usuarioRepository.findByLogin(username);
	}
	
}
