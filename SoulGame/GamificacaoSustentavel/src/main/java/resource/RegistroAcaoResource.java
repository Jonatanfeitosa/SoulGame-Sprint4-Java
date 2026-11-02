package resource;

import dao.AcaoSustentavelDAO;
import dao.RegistroAcaoDAO;
import dao.UsuarioDAO;

import model.AcaoSustentavel;
import model.RegistroAcao;
import model.Usuario;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import service.SistemaGamificacao;

import java.util.List;

@RestController
@RequestMapping("/api/registros")
public class RegistroAcaoResource {

    private final RegistroAcaoDAO registroDAO =
            new RegistroAcaoDAO();

    private final UsuarioDAO usuarioDAO =
            new UsuarioDAO();

    private final AcaoSustentavelDAO acaoDAO =
            new AcaoSustentavelDAO();

    private final SistemaGamificacao sistemaGamificacao =
            new SistemaGamificacao();

    @GetMapping
    public ResponseEntity<List<RegistroAcao>> listar() {
        return ResponseEntity.ok(
                registroDAO.listarTodos()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegistroAcao> buscarPorId(
            @PathVariable int id) {

        RegistroAcao registro =
                registroDAO.buscarPorId(id);

        if (registro == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(registro);
    }

    @PostMapping
    public ResponseEntity<RegistroAcao> registrarAcao(
            @RequestParam int usuarioId,
            @RequestParam int acaoId) {

        Usuario usuario =
                usuarioDAO.buscarPorId(usuarioId);

        if (usuario == null) {
            return ResponseEntity.badRequest().build();
        }

        AcaoSustentavel acao =
                acaoDAO.buscarPorId(acaoId);

        if (acao == null) {
            return ResponseEntity.badRequest().build();
        }

        RegistroAcao registro =
                sistemaGamificacao.registrarAcao(
                        usuario,
                        acao
                );

        if (registro == null
                || !registro.validarRegistro()) {

            return ResponseEntity.badRequest().build();
        }

        if (!registroDAO.criar(registro)) {
            return ResponseEntity.badRequest().build();
        }

        if (!usuarioDAO.atualizar(usuario)) {
            registroDAO.excluir(registro.getId());
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(registro);
    }
}