package service;

import model.AcaoSustentavel;
import model.RegistroAcao;
import model.Usuario;

public class SistemaGamificacao {

    public RegistroAcao registrarAcao(
            Usuario usuario,
            AcaoSustentavel acao) {

        if (usuario == null
                || acao == null
                || !usuario.validarCadastro()
                || !acao.validarAcao()) {

            return null;
        }

        int pontos = acao.calcularPontuacao();

        usuario.adicionarPontos(pontos);

        return new RegistroAcao(
                acao.getId(),
                usuario.getId(),
                "PENDENTE",
                pontos
        );
    }
}