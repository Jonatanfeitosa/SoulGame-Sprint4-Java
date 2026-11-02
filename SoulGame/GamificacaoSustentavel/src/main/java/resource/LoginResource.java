package resource;

import dao.UsuarioDAO;
import model.Login;
import model.Usuario;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
public class LoginResource {

    private final UsuarioDAO usuarioDAO =
            new UsuarioDAO();

    @PostMapping
    public ResponseEntity<Usuario> login(
            @RequestBody Login login) {

        if (login == null || !login.validarLogin()) {
            return ResponseEntity.badRequest().build();
        }

        Usuario usuario =
                usuarioDAO.buscarPorEmailESenha(
                        login.getEmail(),
                        login.getSenha()
                );

        if (usuario == null) {
            return ResponseEntity
                    .status(401)
                    .build();
        }

        return ResponseEntity.ok(usuario);
    }
}