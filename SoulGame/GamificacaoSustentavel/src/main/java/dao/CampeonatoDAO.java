package dao;

import conexao.ConexaoOracleProjeto;
import model.Campeonato;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CampeonatoDAO {

    public List<Campeonato> listarTodos() {

        List<Campeonato> lista =
                new ArrayList<>();

        String sql =
                "SELECT "
                        + "id_campeonato, "
                        + "nome_campeonato, "
                        + "escopo, "
                        + "estado_campeonato, "
                        + "id_ods_relacionado, "
                        + "data_inicio, "
                        + "data_fim, "
                        + "premiacao, "
                        + "id_usuario_criador, "
                        + "status_campeonato "
                        + "FROM TB_CAMPEONATO "
                        + "ORDER BY data_inicio, id_campeonato";

        try (
                Connection connection =
                        ConexaoOracleProjeto.conectar();

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet resultSet =
                        statement.executeQuery()
        ) {

            while (resultSet.next()) {

                lista.add(
                        montar(resultSet)
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "Erro ao listar campeonatos: "
                            + e.getMessage()
            );
        }

        return lista;
    }

    public Campeonato criar(
            Campeonato campeonato) {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        String sql =
                "INSERT INTO TB_CAMPEONATO "
                        + "(nome_campeonato, "
                        + "escopo, "
                        + "estado_campeonato, "
                        + "id_ods_relacionado, "
                        + "data_inicio, "
                        + "data_fim, "
                        + "premiacao, "
                        + "id_usuario_criador, "
                        + "status_campeonato) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {

            connection =
                    ConexaoOracleProjeto.conectar();

            String[] colunasGeradas =
                    {"ID_CAMPEONATO"};

            statement =
                    connection.prepareStatement(
                            sql,
                            colunasGeradas
                    );

            statement.setString(
                    1,
                    campeonato.getNome()
            );

            statement.setString(
                    2,
                    campeonato.getEscopo()
            );

            statement.setString(
                    3,
                    campeonato.getEstado()
            );

            if (campeonato.getOdsId() == null) {
                statement.setNull(
                        4,
                        java.sql.Types.NUMERIC
                );
            } else {
                statement.setInt(
                        4,
                        campeonato.getOdsId()
                );
            }

            statement.setDate(
                    5,
                    Date.valueOf(
                            campeonato.getDataInicio()
                    )
            );

            statement.setDate(
                    6,
                    Date.valueOf(
                            campeonato.getDataFim()
                    )
            );

            statement.setString(
                    7,
                    campeonato.getPremiacao()
            );

            if (campeonato.getUsuarioCriadorId() == null) {
                statement.setNull(
                        8,
                        java.sql.Types.NUMERIC
                );
            } else {
                statement.setInt(
                        8,
                        campeonato.getUsuarioCriadorId()
                );
            }

            String status =
                    campeonato.getStatus();

            if (status == null
                    || status.isBlank()) {

                status = "ATIVO";
            }

            statement.setString(
                    9,
                    status
            );

            if (statement.executeUpdate() != 1) {
                return null;
            }

            resultSet =
                    statement.getGeneratedKeys();

            if (resultSet.next()) {
                campeonato.setId(
                        resultSet.getInt(1)
                );
            }

            campeonato.setStatus(status);

            return campeonato;

        } catch (SQLException e) {

            System.out.println(
                    "Erro ao criar campeonato: "
                            + e.getMessage()
            );

            return null;

        } finally {

            fechar(
                    resultSet,
                    statement,
                    connection
            );
        }
    }

    public boolean inscrever(
            int campeonatoId,
            int usuarioId) {

        Connection connection = null;
        PreparedStatement verifica = null;
        PreparedStatement insert = null;
        ResultSet resultSet = null;

        String sqlVerifica =
                "SELECT id_participacao "
                        + "FROM TB_CAMPEONATO_PARTICIPANTE "
                        + "WHERE id_campeonato = ? "
                        + "AND id_usuario = ?";

        String sqlInsert =
                "INSERT INTO TB_CAMPEONATO_PARTICIPANTE "
                        + "(id_campeonato, "
                        + "id_usuario, "
                        + "pontos_campeonato, "
                        + "data_inscricao) "
                        + "VALUES (?, ?, ?, ?)";

        try {

            connection =
                    ConexaoOracleProjeto.conectar();

            verifica =
                    connection.prepareStatement(
                            sqlVerifica
                    );

            verifica.setInt(
                    1,
                    campeonatoId
            );

            verifica.setInt(
                    2,
                    usuarioId
            );

            resultSet =
                    verifica.executeQuery();

            if (resultSet.next()) {
                return false;
            }

            resultSet.close();
            resultSet = null;

            verifica.close();
            verifica = null;

            insert =
                    connection.prepareStatement(
                            sqlInsert
                    );

            insert.setInt(
                    1,
                    campeonatoId
            );

            insert.setInt(
                    2,
                    usuarioId
            );

            insert.setInt(
                    3,
                    0
            );

            insert.setTimestamp(
                    4,
                    Timestamp.valueOf(
                            LocalDateTime.now()
                    )
            );

            return insert.executeUpdate() == 1;

        } catch (SQLException e) {

            System.out.println(
                    "Erro ao inscrever usuario no campeonato: "
                            + e.getMessage()
            );

            return false;

        } finally {

            fechar(
                    resultSet,
                    verifica,
                    null
            );

            fechar(
                    null,
                    insert,
                    connection
            );
        }
    }

    private Campeonato montar(
            ResultSet resultSet)
            throws SQLException {

        Number odsNumero =
                (Number) resultSet.getObject(
                        "id_ods_relacionado"
                );

        Number criadorNumero =
                (Number) resultSet.getObject(
                        "id_usuario_criador"
                );

        Integer odsId =
                odsNumero == null
                        ? null
                        : odsNumero.intValue();

        Integer usuarioCriadorId =
                criadorNumero == null
                        ? null
                        : criadorNumero.intValue();

        return new Campeonato(
                resultSet.getInt(
                        "id_campeonato"
                ),
                resultSet.getString(
                        "nome_campeonato"
                ),
                resultSet.getString(
                        "escopo"
                ),
                resultSet.getString(
                        "estado_campeonato"
                ),
                odsId,
                resultSet.getDate(
                        "data_inicio"
                ).toLocalDate(),
                resultSet.getDate(
                        "data_fim"
                ).toLocalDate(),
                resultSet.getString(
                        "premiacao"
                ),
                usuarioCriadorId,
                resultSet.getString(
                        "status_campeonato"
                )
        );
    }

    private void fechar(
            ResultSet resultSet,
            PreparedStatement statement,
            Connection connection) {

        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                System.out.println(
                        "Erro ao fechar ResultSet: "
                                + e.getMessage()
                );
            }
        }

        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                System.out.println(
                        "Erro ao fechar PreparedStatement: "
                                + e.getMessage()
                );
            }
        }

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println(
                        "Erro ao fechar Connection: "
                                + e.getMessage()
                );
            }
        }
    }
}