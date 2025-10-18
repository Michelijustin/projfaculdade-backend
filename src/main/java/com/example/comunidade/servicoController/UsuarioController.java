package com.example.comunidade.servicoController;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.example.comunidade.Repository.UsuarioRepository;
import com.example.comunidade.usuario.Usuario;

/*@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:5173")
public class UsuarioController {
    private final UsuarioRepository repo;
    private final PasswordEncoder encoder;

    public UsuarioController(UsuarioRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    @PostMapping("/registro")
    public Usuario registrar(@RequestBody Usuario usuario) {
        usuario.setPassword(encoder.encode(usuario.getPassword()));
        if (usuario.getRole() == null) usuario.setRole("USER");
        return repo.save(usuario);
    }
}
*/

//importa Authentication e Map se necessário
import org.springframework.security.core.Authentication;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:5173")
public class UsuarioController {
 private final UsuarioRepository repo;
 private final PasswordEncoder encoder;

 public UsuarioController(UsuarioRepository repo, PasswordEncoder encoder) {
     this.repo = repo;
     this.encoder = encoder;
 }

 @PostMapping("/registro")
 public Usuario registrar(@RequestBody Usuario usuario) {
     usuario.setPassword(encoder.encode(usuario.getPassword()));
     if (usuario.getRole() == null) usuario.setRole("USER");
     return repo.save(usuario);
 }

 @GetMapping("/me")
 public Map<String, String> me(Authentication auth) {
     if (auth == null) {
         throw new RuntimeException("Não autenticado");
     }
     // retorna username e role — você pode também retornar id se quiser
     return Map.of(
         "username", auth.getName(),
         "role", auth.getAuthorities().stream().findFirst().map(Object::toString).orElse("ROLE_USER")
     );
 }
}

