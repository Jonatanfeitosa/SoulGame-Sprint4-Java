package resource;

import dao.AcaoSustentavelDAO;
import model.AcaoSustentavel;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/atividades")
public class AtividadeResource {

    private final AcaoSustentavelDAO acaoDAO =
            new AcaoSustentavelDAO();

    @GetMapping
    public ResponseEntity<Map<String, List<Map<String, Object>>>> listar() {

        List<AcaoSustentavel> acoes =
                acaoDAO.listarTodas();

        List<Map<String, Object>> atividades =
                new ArrayList<>();

        for (AcaoSustentavel acao : acoes) {

            Map<String, Object> atividade =
                    new HashMap<>();

            atividade.put(
                    "id",
                    gerarSlug(acao)
            );

            atividade.put(
                    "idInterno",
                    acao.getId()
            );

            atividade.put(
                    "nome",
                    acao.getNome()
            );

            atividade.put(
                    "objetivoSustentavel",
                    acao.getObjetivoSustentavel()
            );

            atividade.put(
                    "pontuacaoBase",
                    acao.getPontuacaoBase()
            );

            atividade.put(
                    "nivelDificuldade",
                    acao.getNivelDificuldade()
            );

            atividade.put(
                    "descricao",
                    acao.getDescricao()
            );

            atividade.put(
                    "pontuacaoCalculada",
                    acao.calcularPontuacao()
            );

            atividade.put(
                    "idOds",
                    null
            );

            atividades.add(atividade);
        }

        Map<String, List<Map<String, Object>>> resposta =
                new HashMap<>();

        resposta.put(
                "atividades",
                atividades
        );

        return ResponseEntity.ok(resposta);
    }

    private String gerarSlug(
            AcaoSustentavel acao) {

        return acao.getNome()
                .toLowerCase()
                .trim()
                .replaceAll("[áàãâä]", "a")
                .replaceAll("[éèêë]", "e")
                .replaceAll("[íìîï]", "i")
                .replaceAll("[óòõôö]", "o")
                .replaceAll("[úùûü]", "u")
                .replaceAll("ç", "c")
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("^-|-$", "");
    }
}