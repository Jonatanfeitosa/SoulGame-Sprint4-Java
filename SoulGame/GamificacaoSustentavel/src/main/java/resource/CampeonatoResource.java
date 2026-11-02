package resource;

import dao.CampeonatoDAO;
import model.Campeonato;
import model.InscricaoCampeonato;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/campeonatos")
public class CampeonatoResource {

    private final CampeonatoDAO campeonatoDAO =
            new CampeonatoDAO();

    @GetMapping
    public ResponseEntity<Map<String, List<Campeonato>>> listar() {

        List<Campeonato> campeonatos =
                campeonatoDAO.listarTodos();

        Map<String, List<Campeonato>> resposta =
                new HashMap<>();

        resposta.put(
                "campeonatos",
                campeonatos
        );

        return ResponseEntity.ok(resposta);
    }

    @PostMapping
    public ResponseEntity<Campeonato> criar(
            @RequestBody Campeonato campeonato) {

        if (campeonato == null
                || !campeonato.validar()) {

            return ResponseEntity
                    .badRequest()
                    .build();
        }

        Campeonato criado =
                campeonatoDAO.criar(
                        campeonato
                );

        if (criado == null) {

            return ResponseEntity
                    .badRequest()
                    .build();
        }

        return ResponseEntity
                .status(201)
                .body(criado);
    }

    @PostMapping("/inscrever")
    public ResponseEntity<Map<String, Boolean>> inscrever(
            @RequestBody InscricaoCampeonato inscricao) {

        if (inscricao == null
                || !inscricao.validar()) {

            return ResponseEntity
                    .badRequest()
                    .build();
        }

        boolean inscrito =
                campeonatoDAO.inscrever(
                        inscricao.getCampeonatoId(),
                        inscricao.getUsuarioId()
                );

        Map<String, Boolean> resposta =
                new HashMap<>();

        resposta.put(
                "inscrito",
                inscrito
        );

        if (!inscrito) {

            return ResponseEntity
                    .badRequest()
                    .body(resposta);
        }

        return ResponseEntity.ok(resposta);
    }
}