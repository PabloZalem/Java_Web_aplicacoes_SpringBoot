package com.zalempablo.javaspring.javaspring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.zalempablo.javaspring.javaspring.entities.Usuario;
import com.zalempablo.javaspring.javaspring.repository.UsuarioRepository;

/*	Princípio da Responsabilidade Única (Single Responsibility Principle - SRP):
	Essa classe está responsável por autenticação de usuários, o que se alinha com o princípio SRP, 
	pois sua responsabilidade é clara e única.
*/
@Service
public class AutenticacaoService implements UserDetailsService{
	
	/*	Princípio da Inversão de Dependência (Dependency Inversion Principle - DIP):
	 	A dependência UsuarioRepository é injetada na classe AutenticacaoService em vez de ser criada internamente.
	 */
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	/*	Princípio da Segregação de Interfaces (Interface Segregation Principle - ISP):
		A classe AutenticacaoService implementa a interface UserDetailsService, 
		que é uma interface do Spring
	 */
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
