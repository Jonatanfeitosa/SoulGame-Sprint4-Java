package resource;

import dao.AcaoSustentavelDAO;
import dao.RegistroAcaoDAO;
import dao.UsuarioDAO;

import model.AcaoSustentavel;
import model.RegistroAcao;
import model.RegistroAcaoResposta;
import model.SolicitacaoRegistroAcao;
import model.Usuario;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import service.SistemaGamificacao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<?> registrarAcao(
            @RequestBody SolicitacaoRegistroAcao solicitacao) {

        if (solicitacao == null
                || !solicitacao.validar()) {

            return erro(
                    HttpStatus.BAD_REQUEST,
                    "Dados do registro ou evidencia invalidos."
            );
        }

        Usuario usuario =
                usuarioDAO.buscarPorId(
                        solicitacao.getUsuarioId()
                );

        if (usuario == null) {
            return erro(
                    HttpStatus.BAD_REQUEST,
                    "Usuario nao encontrado."
            );
        }

        AcaoSustentavel acao =
                acaoDAO.buscarPorSlug(
                        solicitacao.getAcaoSlug()
                );

        if (acao == null) {
            return erro(
                    HttpStatus.BAD_REQUEST,
                    "Acao sustentavel nao encontrada."
            );
        }

        RegistroAcao registro =
                sistemaGamificacao.registrarAcao(
                        usuario,
                        acao
                );

        if (registro == null
                || !registro.validarRegistro()) {

            return erro(
                    HttpStatus.BAD_REQUEST,
                    "Nao foi possivel registrar a acao."
            );
        }

        if (!registroDAO.criar(registro)) {
            return erro(
                    HttpStatus.BAD_REQUEST,
                    "Erro ao salvar o registro."
            );
        }

        if (!usuarioDAO.atualizar(usuario)) {
            registroDAO.excluir(registro.getId());

            return erro(
                    HttpStatus.BAD_REQUEST,
                    "Erro ao atualizar a pontuacao do usuario."
            );
        }

        RegistroAcaoResposta registroResposta =
                new RegistroAcaoResposta(
                        registro.getId(),
                        solicitacao.getAcaoSlug(),
                        registro.getStatusValidacao(),
                        registro.getPontosObtidos()
                );

        Map<String, Object> resposta =
                new HashMap<>();

        resposta.put(
                "registro",
                registroResposta
        );

        resposta.put(
                "usuario",
                usuario
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(resposta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(
            @PathVariable int id,
            @RequestBody RegistroAcao dados) {

        RegistroAcao existente =
                registroDAO.buscarPorId(id);

        if (existente == null) {
            return ResponseEntity.notFound().build();
        }

        if (dados == null
                || dados.getLatitude() == null
                || dados.getLongitude() == null) {

            return erro(
                    HttpStatus.BAD_REQUEST,
                    "Latitude e longitude sao obrigatorias."
            );
        }

        double latitude = dados.getLatitude();
        double longitude = dados.getLongitude();

        if (!Double.isFinite(latitude)
                || !Double.isFinite(longitude)
                || latitude < -90
                || latitude > 90
                || longitude < -180
                || longitude > 180) {

            return erro(
                    HttpStatus.BAD_REQUEST,
                    "Coordenadas geograficas invalidas."
            );
        }

        existente.setLatitude(latitude);
        existente.setLongitude(longitude);

        if (!registroDAO.atualizar(existente)) {
            return erro(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Nao foi possivel atualizar o registro."
            );
        }

        return ResponseEntity.ok(existente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(
            @PathVariable int id) {

        RegistroAcao existente =
                registroDAO.buscarPorId(id);

        if (existente == null) {
            return ResponseEntity.notFound().build();
        }

        if (existente.getPontosObtidos() != 0) {
            return erro(
                    HttpStatus.CONFLICT,
                    "Registro possui pontos. Exclusao exige estorno transacional."
            );
        }

        if (!registroDAO.excluir(id)) {
            return erro(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Nao foi possivel excluir o registro."
            );
        }

        return ResponseEntity.noContent().build();
    }

    private ResponseEntity<Map<String, String>> erro(
            HttpStatus status,
            String mensagem) {

        Map<String, String> resposta =
                new HashMap<>();

        resposta.put(
                "erro",
                mensagem
        );

        return ResponseEntity
                .status(status)
                .body(resposta);
    }
}
