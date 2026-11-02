package resource;

import dao.UsuarioDAO;
import model.Usuario;
import service.Ranking;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioResource {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @PostMapping
    public ResponseEntity<Usuario> criar(
            @RequestBody Usuario usuario) {

        if (usuario == null || !usuario.validarCadastro()) {
            return ResponseEntity.badRequest().build();
        }

        boolean criado = usuarioDAO.criar(usuario);

        if (criado) {
            return ResponseEntity
                    .status(201)
                    .body(usuario);
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(
            @PathVariable int id) {

        Usuario usuario = usuarioDAO.buscarPorId(id);

        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos() {
        return ResponseEntity.ok(
                usuarioDAO.listarTodos()
        );
    }

    @GetMapping("/ranking")
    public ResponseEntity<List<Usuario>> ranking() {

        List<Usuario> usuarios =
                usuarioDAO.listarTodos();

        Ranking ranking = new Ranking();

        for (Usuario usuario : usuarios) {
            ranking.adicionarUsuario(usuario);
        }

        return ResponseEntity.ok(
                ranking.obterRanking()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizar(
            @PathVariable int id,
            @RequestBody Usuario usuario) {

        if (usuario == null) {
            return ResponseEntity.badRequest().build();
        }

        usuario.setId(id);

        if (!usuario.validarCadastro()) {
            return ResponseEntity.badRequest().build();
        }

        if (usuarioDAO.atualizar(usuario)) {
            return ResponseEntity.ok(usuario);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable int id) {

        if (usuarioDAO.excluir(id)) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}