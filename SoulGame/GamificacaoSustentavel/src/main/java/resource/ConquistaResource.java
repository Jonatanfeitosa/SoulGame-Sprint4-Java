package resource;

import dao.ConquistaDAO;
import model.Conquista;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/conquistas")
public class ConquistaResource {

    private final ConquistaDAO conquistaDAO =
            new ConquistaDAO();

    @GetMapping
    public ResponseEntity<Map<String, List<Conquista>>> listar(
            @RequestParam(required = false) Integer usuarioId) {

        List<Conquista> conquistas;

        if (usuarioId == null) {
            conquistas =
                    conquistaDAO.listarTodas();
        } else {
            conquistas =
                    conquistaDAO.listarPorUsuario(
                            usuarioId
                    );
        }

        Map<String, List<Conquista>> resposta =
                new HashMap<>();

        resposta.put(
                "conquistas",
                conquistas
        );

        return ResponseEntity.ok(resposta);
    }
}