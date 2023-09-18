package com.zalempablo.javaspring.javaspring.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.zalempablo.javaspring.javaspring.entities.Usuario;
import com.zalempablo.javaspring.javaspring.service.AutenticacaoService;

public class UsuarioRepositoryTest {

	@Autowired
	private AutenticacaoService autenticacaoService;
	
	@Autowired
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    public void setUp() {
        usuarioRepository = mock(UsuarioRepository.class);
        autenticacaoService = new AutenticacaoService(usuarioRepository);
    }

    @Test
    public void testLoadUserByUsername() {
        // Criar um usuário de exemplo
        Usuario usuario = new Usuario(1L, "ana.souza@voll.med", "123456");

        // Configurar comportamento do mock para retornar o usuário quando findByLogin for chamado
        when(usuarioRepository.findByLogin("ana.souza@voll.med")).thenReturn(usuario);

        // Chamar o método de serviço para carregar o usuário pelo login
        UserDetails userDetails = autenticacaoService.loadUserByUsername("ana.souza@voll.med");

        // Verificar se o usuário retornado é o mesmo que o esperado
        assertEquals(usuario.getUsername(), userDetails.getUsername());
        assertEquals(usuario.getPassword(), userDetails.getPassword());
    }

    @Test
    public void testLoadUserByUsernameNotFound() {
        // Configurar comportamento do mock para retornar null quando findByLogin for chamado
        when(usuarioRepository.findByLogin("usuario_inexistente")).thenReturn(null);

        // Chamar o método de serviço para carregar o usuário pelo login
        assertThrows(UsernameNotFoundException.class, () -> {
        	autenticacaoService.loadUserByUsername("usuario_inexistente");
        });
    }
}


