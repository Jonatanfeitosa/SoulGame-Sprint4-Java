package resource;

import dao.UsuarioDAO;
import model.AtualizacaoPerfil;
import model.Usuario;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/perfil")
public class PerfilResource {

    private final UsuarioDAO usuarioDAO =
            new UsuarioDAO();

    @PutMapping("/{usuarioId}")
    public ResponseEntity<Usuario> atualizar(
            @PathVariable int usuarioId,
            @RequestBody AtualizacaoPerfil dados) {

        Usuario usuario =
                usuarioDAO.buscarPorId(usuarioId);

        if (usuario == null) {
            return ResponseEntity
                    .notFound()
                    .build();
        }

        if (dados == null) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }

        if (dados.getNome() != null) {
            usuario.setNome(
                    dados.getNome()
            );
        }

        if (dados.getTelefone() != null) {
            usuario.setTelefone(
                    dados.getTelefone()
            );
        }

        if (dados.getCidade() != null) {
            usuario.setCidade(
                    dados.getCidade()
            );
        }

        if (dados.getEstado() != null) {
            usuario.setEstado(
                    dados.getEstado()
            );
        }

        if (dados.getAvatarTipo() != null) {
            usuario.setAvatarTipo(
                    dados.getAvatarTipo()
            );
        }

        if (dados.getAvatarFotoBase64() != null
                && !dados.getAvatarFotoBase64().isBlank()) {

            usuario.setAvatarUrl(
                    dados.getAvatarFotoBase64()
            );

        } else if (dados.getAvatarPersonagemId() != null
                && !dados.getAvatarPersonagemId().isBlank()) {

            usuario.setAvatarUrl(
                    dados.getAvatarPersonagemId()
            );
        }

        if (!usuario.validarCadastro()) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }

        if (!usuarioDAO.atualizar(usuario)) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }

        return ResponseEntity.ok(usuario);
    }
}