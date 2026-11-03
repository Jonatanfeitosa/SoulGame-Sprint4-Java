package dao;

import conexao.ConexaoOracleProjeto;
import model.Conquista;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConquistaDAO {

    public List<Conquista> listarTodas() {

        String sql =
                "SELECT "
                        + "id_conquista, "
                        + "nome_conquista, "
                        + "descricao_conquista, "
                        + "tipo_conquista, "
                        + "criterio_valor, "
                        + "id_ods, "
                        + "slug_acao_relacionada, "
                        + "status_conquista "
                        + "FROM TB_CONQUISTA "
                        + "WHERE status_conquista = 'ATIVA' "
                        + "ORDER BY id_conquista";

        return executarConsulta(sql, null);
    }

    public List<Conquista> listarPorUsuario(int usuarioId) {

        String sql =
                "SELECT "
                        + "c.id_conquista, "
                        + "c.nome_conquista, "
                        + "c.descricao_conquista, "
                        + "c.tipo_conquista, "
                        + "c.criterio_valor, "
                        + "c.id_ods, "
                        + "c.slug_acao_relacionada, "
                        + "c.status_conquista "
                        + "FROM TB_CONQUISTA c "
                        + "INNER JOIN TB_USUARIO_CONQUISTA uc "
                        + "ON uc.id_conquista = c.id_conquista "
                        + "WHERE uc.id_usuario = ? "
                        + "AND c.status_conquista = 'ATIVA' "
                        + "ORDER BY c.id_conquista";

        return executarConsulta(sql, usuarioId);
    }

    private List<Conquista> executarConsulta(
            String sql,
            Integer usuarioId) {

        List<Conquista> lista =
                new ArrayList<>();

        try (
                Connection connection =
                        ConexaoOracleProjeto.conectar();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            if (usuarioId != null) {
                statement.setInt(1, usuarioId);
            }

            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

                while (resultSet.next()) {

                    Number criterioNumero =
                            (Number) resultSet.getObject(
                                    "criterio_valor"
                            );

                    Number odsNumero =
                            (Number) resultSet.getObject(
                                    "id_ods"
                            );

                    Integer criterioValor =
                            criterioNumero == null
                                    ? null
                                    : criterioNumero.intValue();

                    Integer odsId =
                            odsNumero == null
                                    ? null
                                    : odsNumero.intValue();

                    lista.add(
                            new Conquista(
                                    resultSet.getInt(
                                            "id_conquista"
                                    ),
                                    resultSet.getString(
                                            "nome_conquista"
                                    ),
                                    resultSet.getString(
                                            "descricao_conquista"
                                    ),
                                    resultSet.getString(
                                            "tipo_conquista"
                                    ),
                                    criterioValor,
                                    odsId,
                                    resultSet.getString(
                                            "slug_acao_relacionada"
                                    ),
                                    resultSet.getString(
                                            "status_conquista"
                                    )
                            )
                    );
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Erro ao listar conquistas: "
                            + e.getMessage()
            );
        }

        return lista;
    }
}