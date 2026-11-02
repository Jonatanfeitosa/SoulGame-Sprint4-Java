package resource;

import dao.RegistroAcaoDAO;
import model.HistoricoItem;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/historico")
public class HistoricoResource {

    private final RegistroAcaoDAO registroDAO =
            new RegistroAcaoDAO();

    @GetMapping
    public ResponseEntity<Map<String, List<HistoricoItem>>> listar(
            @RequestParam int usuarioId) {

        List<HistoricoItem> historico =
                registroDAO.listarHistoricoPorUsuario(
                        usuarioId
                );

        Map<String, List<HistoricoItem>> resposta =
                new HashMap<>();

        resposta.put(
                "historico",
                historico
        );

        return ResponseEntity.ok(resposta);
    }
}