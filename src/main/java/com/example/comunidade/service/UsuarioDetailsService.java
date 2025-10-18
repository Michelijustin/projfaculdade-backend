package com.example.comunidade.service;
import com.example.comunidade.usuario.Usuario;
import com.example.comunidade.Repository.UsuarioRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDetailsService implements UserDetailsService {
    private final UsuarioRepository repo;

    public UsuarioDetailsService(UsuarioRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario u = repo.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
        return User.builder()
            .username(u.getUsername())
            .password(u.getPassword())
            .roles(u.getRole()) // USER ou ADMIN
            .build();
    }
}
