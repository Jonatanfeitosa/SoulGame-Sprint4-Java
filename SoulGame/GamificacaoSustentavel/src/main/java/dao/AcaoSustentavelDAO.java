package dao;

import conexao.ConexaoOracleProjeto;
import model.AcaoSustentavel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AcaoSustentavelDAO {

    public boolean criar(AcaoSustentavel acao) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        String sql = "INSERT INTO TB_ACAO_SUSTENTAVEL "
                + "(nome_acao, objetivo_sustentavel, pontuacao_base, "
                + "nivel_dificuldade, descricao_acao) "
                + "VALUES (?, ?, ?, ?, ?)";

        try {
            connection = ConexaoOracleProjeto.conectar();

            String[] colunasGeradas = {"ID_ACAO"};
            statement = connection.prepareStatement(sql, colunasGeradas);

            preencher(statement, acao, false);

            if (statement.executeUpdate() != 1) {
                return false;
            }

            resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {
                acao.setId(resultSet.getInt(1));
            }

            return true;

        } catch (SQLException e) {
            System.out.println("Erro ao criar acao: " + e.getMessage());
            return false;

        } finally {
            fechar(resultSet, statement, connection);
        }
    }

    public AcaoSustentavel buscarPorId(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        String sql =
                "SELECT * FROM TB_ACAO_SUSTENTAVEL WHERE id_acao = ?";

        try {
            connection = ConexaoOracleProjeto.conectar();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return montar(resultSet);
            }

            return null;

        } catch (SQLException e) {
            System.out.println("Erro ao buscar acao: " + e.getMessage());
            return null;

        } finally {
            fechar(resultSet, statement, connection);
        }
    }

    public AcaoSustentavel buscarPorSlug(String slug) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        String sql =
                "SELECT * FROM TB_ACAO_SUSTENTAVEL WHERE slug_acao = ?";

        try {
            connection = ConexaoOracleProjeto.conectar();
            statement = connection.prepareStatement(sql);
            statement.setString(1, slug);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return montar(resultSet);
            }

            return null;

        } catch (SQLException e) {
            System.out.println(
                    "Erro ao buscar acao por slug: " + e.getMessage()
            );
            return null;

        } finally {
            fechar(resultSet, statement, connection);
        }
    }

    public List<AcaoSustentavel> listarTodas() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        List<AcaoSustentavel> lista = new ArrayList<>();

        String sql =
                "SELECT * FROM TB_ACAO_SUSTENTAVEL ORDER BY id_acao";

        try {
            connection = ConexaoOracleProjeto.conectar();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                lista.add(montar(resultSet));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar acoes: " + e.getMessage());

        } finally {
            fechar(resultSet, statement, connection);
        }

        return lista;
    }

    public boolean atualizar(AcaoSustentavel acao) {
        Connection connection = null;
        PreparedStatement statement = null;

        String sql = "UPDATE TB_ACAO_SUSTENTAVEL SET "
                + "nome_acao = ?, objetivo_sustentavel = ?, "
                + "pontuacao_base = ?, nivel_dificuldade = ?, "
                + "descricao_acao = ? WHERE id_acao = ?";

        try {
            connection = ConexaoOracleProjeto.conectar();
            statement = connection.prepareStatement(sql);

            preencher(statement, acao, true);

            return statement.executeUpdate() == 1;

        } catch (SQLException e) {
            System.out.println(
                    "Erro ao atualizar acao: " + e.getMessage()
            );
            return false;

        } finally {
            fechar(null, statement, connection);
        }
    }

    public boolean excluir(int id) {
        Connection connection = null;
        PreparedStatement statement = null;

        String sql =
                "DELETE FROM TB_ACAO_SUSTENTAVEL WHERE id_acao = ?";

        try {
            connection = ConexaoOracleProjeto.conectar();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            return statement.executeUpdate() == 1;

        } catch (SQLException e) {
            System.out.println("Erro ao excluir acao: " + e.getMessage());
            return false;

        } finally {
            fechar(null, statement, connection);
        }
    }

    private void preencher(
            PreparedStatement statement,
            AcaoSustentavel acao,
            boolean incluirId) throws SQLException {

        statement.setString(1, acao.getNome());
        statement.setString(2, acao.getObjetivoSustentavel());
        statement.setInt(3, acao.getPontuacaoBase());
        statement.setString(
                4,
                acao.getNivelDificuldade().toUpperCase()
        );
        statement.setString(5, acao.getDescricao());

        if (incluirId) {
            statement.setInt(6, acao.getId());
        }
    }

    private AcaoSustentavel montar(ResultSet resultSet)
            throws SQLException {

        return new AcaoSustentavel(
                resultSet.getInt("id_acao"),
                resultSet.getString("nome_acao"),
                resultSet.getString("objetivo_sustentavel"),
                resultSet.getInt("pontuacao_base"),
                resultSet.getString("nivel_dificuldade"),
                resultSet.getString("descricao_acao")
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
                        "Erro ao fechar ResultSet: " + e.getMessage()
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
                        "Erro ao fechar Connection: " + e.getMessage()
                );
            }
        }
    }
}