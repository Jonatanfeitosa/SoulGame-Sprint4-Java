package dao;

import conexao.ConexaoOracleProjeto;
import model.DesafioAgendado;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DesafioAgendadoDAO {

    public DesafioAgendado criar(
            int usuarioId,
            String acaoSlug,
            LocalDate prazo) {

        Connection connection = null;
        PreparedStatement buscaAcao = null;
        PreparedStatement insert = null;
        ResultSet resultadoAcao = null;
        ResultSet chavesGeradas = null;

        String sqlBuscaAcao =
                "SELECT id_acao "
                        + "FROM TB_ACAO_SUSTENTAVEL "
                        + "WHERE slug_acao = ?";

        String sqlInsert =
                "INSERT INTO TB_DESAFIO_AGENDADO "
                        + "(id_usuario, id_acao, data_agendamento, status_desafio) "
                        + "VALUES (?, ?, ?, ?)";

        try {
            connection =
                    ConexaoOracleProjeto.conectar();

            buscaAcao =
                    connection.prepareStatement(
                            sqlBuscaAcao
                    );

            buscaAcao.setString(
                    1,
                    acaoSlug
            );

            resultadoAcao =
                    buscaAcao.executeQuery();

            if (!resultadoAcao.next()) {
                return null;
            }

            int acaoId =
                    resultadoAcao.getInt(
                            "id_acao"
                    );

            String[] colunasGeradas =
                    {"ID_DESAFIO"};

            insert =
                    connection.prepareStatement(
                            sqlInsert,
                            colunasGeradas
                    );

            insert.setInt(
                    1,
                    usuarioId
            );

            insert.setInt(
                    2,
                    acaoId
            );

            insert.setDate(
                    3,
                    Date.valueOf(prazo)
            );

            insert.setString(
                    4,
                    "AGENDADO"
            );

            if (insert.executeUpdate() != 1) {
                return null;
            }

            chavesGeradas =
                    insert.getGeneratedKeys();

            int id = 0;

            if (chavesGeradas.next()) {
                id =
                        chavesGeradas.getInt(1);
            }

            return new DesafioAgendado(
                    id,
                    String.valueOf(usuarioId),
                    acaoSlug,
                    prazo,
                    prazo.isBefore(
                            LocalDate.now()
                    )
            );

        } catch (SQLException e) {

            System.out.println(
                    "Erro ao agendar desafio: "
                            + e.getMessage()
            );

            return null;

        } finally {

            fechar(
                    chavesGeradas,
                    insert,
                    null
            );

            fechar(
                    resultadoAcao,
                    buscaAcao,
                    connection
            );
        }
    }

    public List<DesafioAgendado> listarPorUsuario(
            int usuarioId) {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        List<DesafioAgendado> lista =
                new ArrayList<>();

        String sql =
                "SELECT "
                        + "d.id_desafio, "
                        + "d.id_usuario, "
                        + "a.slug_acao, "
                        + "d.data_agendamento "
                        + "FROM TB_DESAFIO_AGENDADO d "
                        + "INNER JOIN TB_ACAO_SUSTENTAVEL a "
                        + "ON a.id_acao = d.id_acao "
                        + "WHERE d.id_usuario = ? "
                        + "AND d.status_desafio = 'AGENDADO' "
                        + "ORDER BY d.data_agendamento";

        try {
            connection =
                    ConexaoOracleProjeto.conectar();

            statement =
                    connection.prepareStatement(sql);

            statement.setInt(
                    1,
                    usuarioId
            );

            resultSet =
                    statement.executeQuery();

            while (resultSet.next()) {

                LocalDate prazo =
                        resultSet
                                .getDate(
                                        "data_agendamento"
                                )
                                .toLocalDate();

                lista.add(
                        new DesafioAgendado(
                                resultSet.getInt(
                                        "id_desafio"
                                ),
                                String.valueOf(
                                        resultSet.getInt(
                                                "id_usuario"
                                        )
                                ),
                                resultSet.getString(
                                        "slug_acao"
                                ),
                                prazo,
                                prazo.isBefore(
                                        LocalDate.now()
                                )
                        )
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "Erro ao listar desafios: "
                            + e.getMessage()
            );

        } finally {
            fechar(
                    resultSet,
                    statement,
                    connection
            );
        }

        return lista;
    }

    public boolean cancelar(
            int usuarioId,
            String acaoSlug) {

        Connection connection = null;
        PreparedStatement statement = null;

        String sql =
                "UPDATE TB_DESAFIO_AGENDADO "
                        + "SET status_desafio = 'CANCELADO' "
                        + "WHERE id_usuario = ? "
                        + "AND id_acao = ("
                        + "SELECT id_acao "
                        + "FROM TB_ACAO_SUSTENTAVEL "
                        + "WHERE slug_acao = ?"
                        + ") "
                        + "AND status_desafio = 'AGENDADO'";

        try {
            connection =
                    ConexaoOracleProjeto.conectar();

            statement =
                    connection.prepareStatement(sql);

            statement.setInt(
                    1,
                    usuarioId
            );

            statement.setString(
                    2,
                    acaoSlug
            );

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Erro ao cancelar desafio: "
                            + e.getMessage()
            );

            return false;

        } finally {
            fechar(
                    null,
                    statement,
                    connection
            );
        }
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