package resource;

import dao.AcaoSustentavelDAO;
import model.AcaoSustentavel;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/acoes")
public class AcaoSustentavelResource {

    private final AcaoSustentavelDAO acaoDAO =
            new AcaoSustentavelDAO();

    @GetMapping
    public ResponseEntity<List<AcaoSustentavel>> listar() {
        return ResponseEntity.ok(acaoDAO.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AcaoSustentavel> buscarPorId(
            @PathVariable int id) {

        AcaoSustentavel acao = acaoDAO.buscarPorId(id);

        if (acao == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(acao);
    }

    @PostMapping
    public ResponseEntity<AcaoSustentavel> criar(
            @RequestBody AcaoSustentavel acao) {

        if (!acao.validarAcao()) {
            return ResponseEntity.badRequest().build();
        }

        if (!acaoDAO.criar(acao)) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(acao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AcaoSustentavel> atualizar(
            @PathVariable int id,
            @RequestBody AcaoSustentavel acao) {

        if (acaoDAO.buscarPorId(id) == null) {
            return ResponseEntity.notFound().build();
        }

        acao.setId(id);

        if (!acao.validarAcao()) {
            return ResponseEntity.badRequest().build();
        }

        if (!acaoDAO.atualizar(acao)) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(acao);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable int id) {

        if (acaoDAO.buscarPorId(id) == null) {
            return ResponseEntity.notFound().build();
        }

        if (!acaoDAO.excluir(id)) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.noContent().build();
    }
}