package com.zalempablo.javaspring.javaspring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.zalempablo.javaspring.javaspring.entities.Usuario;
import com.zalempablo.javaspring.javaspring.repository.UsuarioRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AutenticacaoServiceTest {

    @InjectMocks
    private AutenticacaoService autenticacaoService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoadUserByUsername_UserExists() {
        // Defina o comportamento esperado para o repositório de usuário
        String username = "usuarioteste";
        Usuario usuario = new Usuario(1L, username, "senha");
        Mockito.when(usuarioRepository.findByLogin(username)).thenReturn(usuario);

        UserDetails userDetails = autenticacaoService.loadUserByUsername(username);

        assertEquals(username, userDetails.getUsername());
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        // Defina o comportamento esperado para o repositório de usuário
        String username = "usuarioteste";
        Mockito.when(usuarioRepository.findByLogin(username)).thenReturn(null);

        // Verifique se a exceção UsernameNotFoundException é lançada
        assertThrows(UsernameNotFoundException.class, () -> {
            autenticacaoService.loadUserByUsername(username);
        });
    }

    @Test
    public void testLoadUserByUsername_UserDetailsCorrect() {
        // Defina o comportamento esperado para o repositório de usuário
        String username = "usuarioteste";
        Usuario usuario = new Usuario(1L, username, "senha");
        Mockito.when(usuarioRepository.findByLogin(username)).thenReturn(usuario);

        UserDetails userDetails = autenticacaoService.loadUserByUsername(username);

        assertEquals(username, userDetails.getUsername());
        // Você também pode adicionar mais verificações para os detalhes do usuário, se necessário.
    }
}
