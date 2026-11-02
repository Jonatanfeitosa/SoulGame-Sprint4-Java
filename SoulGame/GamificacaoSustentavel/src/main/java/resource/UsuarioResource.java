package resource;

import dao.UsuarioDAO;
import model.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioResource {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @PostMapping
    public ResponseEntity<Usuario> criar(@RequestBody Usuario usuario) {
        boolean criado = usuarioDAO.criar(usuario);

        if (criado) {
            return ResponseEntity.status(201).body(usuario);
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable int id) {
        Usuario usuario = usuarioDAO.buscarPorId(id);

        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos() {
        return ResponseEntity.ok(usuarioDAO.listarTodos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizar(
            @PathVariable int id,
            @RequestBody Usuario usuario) {

        usuario.setId(id);

        if (usuarioDAO.atualizar(usuario)) {
            return ResponseEntity.ok(usuario);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable int id) {
        if (usuarioDAO.excluir(id)) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}