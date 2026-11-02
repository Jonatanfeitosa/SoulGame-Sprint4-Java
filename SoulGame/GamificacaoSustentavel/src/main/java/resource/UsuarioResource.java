package resource;

import dao.UsuarioDAO;
import model.CadastroUsuario;
import model.Usuario;
import service.Ranking;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioResource {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @PostMapping
    public ResponseEntity<Usuario> criar(
            @RequestBody CadastroUsuario cadastro) {

        if (cadastro == null
                || !cadastro.validarCadastro()) {

            return ResponseEntity
                    .badRequest()
                    .build();
        }

        Integer indicadoPor = null;

        if (cadastro.getIndicadoPorCodigo() != null
                && !cadastro.getIndicadoPorCodigo().isBlank()) {

            Usuario indicador =
                    usuarioDAO.buscarPorCodigoIndicacao(
                            cadastro.getIndicadoPorCodigo()
                    );

            if (indicador == null) {
                return ResponseEntity
                        .badRequest()
                        .build();
            }

            indicadoPor = indicador.getId();
        }

        Usuario usuario = new Usuario();

        usuario.setNome(
                cadastro.getNome()
        );

        usuario.setEmail(
                cadastro.getEmail()
        );

        usuario.setSenha(
                cadastro.getSenha()
        );

        usuario.setTelefone(
                cadastro.getTelefone()
        );

        usuario.setDataNascimento(
                cadastro.getDataNascimento()
        );

        usuario.setCidade(
                cadastro.getCidade()
        );

        usuario.setEstado(
                cadastro.getEstado()
        );

        usuario.setTipo(
                cadastro.getTipo()
        );

        usuario.setCpf(null);
        usuario.setBairro(null);

        usuario.setTotalPontos(0);
        usuario.setNivel(1);
        usuario.setStreakDias(0);

        usuario.setCodigoIndicacao(
                gerarCodigoIndicacao()
        );

        usuario.setIndicadoPor(
                indicadoPor
        );

        usuario.setAvatarUrl(null);
        usuario.setAvatarTipo(null);

        if (!usuario.validarCadastro()) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }

        if (usuarioDAO.criar(usuario)) {
            return ResponseEntity
                    .status(201)
                    .body(usuario);
        }

        return ResponseEntity
                .badRequest()
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(
            @PathVariable int id) {

        Usuario usuario =
                usuarioDAO.buscarPorId(id);

        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        }

        return ResponseEntity
                .notFound()
                .build();
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos() {

        return ResponseEntity.ok(
                usuarioDAO.listarTodos()
        );
    }

    @GetMapping("/ranking")
    public ResponseEntity<Map<String, List<Usuario>>> ranking() {

        List<Usuario> usuarios =
                usuarioDAO.listarTodos();

        Ranking ranking =
                new Ranking();

        for (Usuario usuario : usuarios) {
            ranking.adicionarUsuario(usuario);
        }

        Map<String, List<Usuario>> resposta =
                new HashMap<>();

        resposta.put(
                "ranking",
                ranking.obterRanking()
        );

        return ResponseEntity.ok(resposta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizar(
            @PathVariable int id,
            @RequestBody Usuario usuario) {

        if (usuario == null) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }

        usuario.setId(id);

        if (!usuario.validarCadastro()) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }

        if (usuarioDAO.atualizar(usuario)) {
            return ResponseEntity.ok(usuario);
        }

        return ResponseEntity
                .notFound()
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable int id) {

        if (usuarioDAO.excluir(id)) {
            return ResponseEntity
                    .noContent()
                    .build();
        }

        return ResponseEntity
                .notFound()
                .build();
    }

    private String gerarCodigoIndicacao() {

        return UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 8)
                .toUpperCase();
    }
}