package com.example.comunidade.servicoController;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.example.comunidade.Repository.ServicoRepository;
import com.example.comunidade.servico.Servico;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/servicos")

public class ServicoController {

    private final ServicoRepository repo;

    public ServicoController(ServicoRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Servico> listar() {
        return repo.findAll();
    }

    @PostMapping
    public Servico criar(@RequestBody Servico servico, Authentication auth) {
        if (auth != null) {
            servico.setCriador(auth.getName()); // associar serviço ao usuário logado
        } else {
            throw new RuntimeException("Usuário não autenticado"); // só logados podem criar
        }
        return repo.save(servico);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id, Authentication auth) {
        Servico s = repo.findById(id).orElseThrow();

        boolean isAdmin = auth != null && auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        boolean isCriador = auth != null && auth.getName().equals(s.getCriador());

        if (isAdmin || isCriador) {
            repo.deleteById(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }


}

