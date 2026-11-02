package resource;

import dao.DesafioAgendadoDAO;
import model.AgendamentoDesafio;
import model.DesafioAgendado;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/desafios-agendados")
public class DesafioAgendadoResource {

    private final DesafioAgendadoDAO desafioDAO =
            new DesafioAgendadoDAO();

    @GetMapping
    public ResponseEntity<Map<String, List<DesafioAgendado>>> listar(
            @RequestParam int usuarioId) {

        List<DesafioAgendado> desafios =
                desafioDAO.listarPorUsuario(
                        usuarioId
                );

        Map<String, List<DesafioAgendado>> resposta =
                new HashMap<>();

        resposta.put(
                "desafiosAgendados",
                desafios
        );

        return ResponseEntity.ok(resposta);
    }

    @PostMapping
    public ResponseEntity<DesafioAgendado> agendar(
            @RequestBody AgendamentoDesafio dados) {

        if (dados == null
                || !dados.validar()) {

            return ResponseEntity
                    .badRequest()
                    .build();
        }

        int usuarioId;

        try {
            usuarioId =
                    Integer.parseInt(
                            dados.getUsuarioId()
                    );

        } catch (NumberFormatException e) {

            return ResponseEntity
                    .badRequest()
                    .build();
        }

        DesafioAgendado desafio =
                desafioDAO.criar(
                        usuarioId,
                        dados.getAcaoSlug(),
                        dados.getPrazo()
                );

        if (desafio == null) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }

        return ResponseEntity
                .status(201)
                .body(desafio);
    }

    @DeleteMapping
    public ResponseEntity<Map<String, Boolean>> cancelar(
            @RequestParam int usuarioId,
            @RequestParam String acaoSlug) {

        boolean cancelado =
                desafioDAO.cancelar(
                        usuarioId,
                        acaoSlug
                );

        Map<String, Boolean> resposta =
                new HashMap<>();

        resposta.put(
                "cancelado",
                cancelado
        );

        return ResponseEntity.ok(resposta);
    }
}