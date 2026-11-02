package resource;

import dao.OdsDAO;
import model.Ods;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ods")
public class OdsResource {

    private final OdsDAO odsDAO =
            new OdsDAO();

    @GetMapping
    public ResponseEntity<Map<String, List<Ods>>> listar() {

        List<Ods> ods =
                odsDAO.listarTodos();

        Map<String, List<Ods>> resposta =
                new HashMap<>();

        resposta.put(
                "ods",
                ods
        );

        return ResponseEntity.ok(resposta);
    }
}