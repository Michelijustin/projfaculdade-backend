package com.example.comunidade.admin;
import com.example.comunidade.usuario.Usuario;
import com.example.comunidade.Repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminSetup {

    @Bean
    CommandLineRunner initAdmin(UsuarioRepository repo, PasswordEncoder encoder) {
        return args -> {
            if (repo.findByUsername("admin").isEmpty()) {
                Usuario u = new Usuario();
                u.setUsername("admin");
                u.setPassword(encoder.encode("admin123"));
                u.setRole("ADMIN");
                repo.save(u);   // ponto e vírgula aqui!
            }
        };
    }
}
