package dao;

import conexao.ConexaoOracleProjeto;
import model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public boolean criar(Usuario usuario) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        String sql = "INSERT INTO TB_USUARIO "
                + "(nome_usuario, email_usuario, cpf_usuario, senha_usuario, "
                + "cidade_usuario, bairro_usuario, total_pontos_usuario) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            connection = ConexaoOracleProjeto.conectar();

            String[] colunasGeradas = {"ID_USUARIO"};
            statement = connection.prepareStatement(sql, colunasGeradas);

            preencher(statement, usuario, false);

            if (statement.executeUpdate() != 1) {
                return false;
            }

            resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {
                usuario.setId(resultSet.getInt(1));
            }

            return true;

        } catch (SQLException e) {
            System.out.println(
                    "Erro ao criar usuario: " + e.getMessage()
            );
            return false;

        } finally {
            fechar(resultSet, statement, connection);
        }
    }

    public Usuario buscarPorId(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        String sql =
                "SELECT * FROM TB_USUARIO WHERE id_usuario = ?";

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
            System.out.println(
                    "Erro ao buscar usuario: " + e.getMessage()
            );
            return null;

        } finally {
            fechar(resultSet, statement, connection);
        }
    }

    public Usuario buscarPorEmailESenha(
            String email,
            String senha) {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        String sql = "SELECT * FROM TB_USUARIO "
                + "WHERE email_usuario = ? "
                + "AND senha_usuario = ?";

        try {
            connection = ConexaoOracleProjeto.conectar();
            statement = connection.prepareStatement(sql);

            statement.setString(1, email);
            statement.setString(2, senha);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return montar(resultSet);
            }

            return null;

        } catch (SQLException e) {
            System.out.println(
                    "Erro ao realizar login: " + e.getMessage()
            );
            return null;

        } finally {
            fechar(resultSet, statement, connection);
        }
    }

    public List<Usuario> listarTodos() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        List<Usuario> lista = new ArrayList<>();

        String sql = "SELECT * FROM TB_USUARIO "
                + "ORDER BY total_pontos_usuario DESC";

        try {
            connection = ConexaoOracleProjeto.conectar();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                lista.add(montar(resultSet));
            }

        } catch (SQLException e) {
            System.out.println(
                    "Erro ao listar usuarios: " + e.getMessage()
            );

        } finally {
            fechar(resultSet, statement, connection);
        }

        return lista;
    }

    public boolean atualizar(Usuario usuario) {
        Connection connection = null;
        PreparedStatement statement = null;

        String sql = "UPDATE TB_USUARIO SET "
                + "nome_usuario = ?, "
                + "email_usuario = ?, "
                + "cpf_usuario = ?, "
                + "senha_usuario = ?, "
                + "cidade_usuario = ?, "
                + "bairro_usuario = ?, "
                + "total_pontos_usuario = ? "
                + "WHERE id_usuario = ?";

        try {
            connection = ConexaoOracleProjeto.conectar();
            statement = connection.prepareStatement(sql);

            preencher(statement, usuario, true);

            return statement.executeUpdate() == 1;

        } catch (SQLException e) {
            System.out.println(
                    "Erro ao atualizar usuario: " + e.getMessage()
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
                "DELETE FROM TB_USUARIO WHERE id_usuario = ?";

        try {
            connection = ConexaoOracleProjeto.conectar();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            return statement.executeUpdate() == 1;

        } catch (SQLException e) {
            System.out.println(
                    "Erro ao excluir usuario: " + e.getMessage()
            );
            return false;

        } finally {
            fechar(null, statement, connection);
        }
    }

    private void preencher(
            PreparedStatement statement,
            Usuario usuario,
            boolean incluirId) throws SQLException {

        statement.setString(1, usuario.getNome());
        statement.setString(2, usuario.getEmail());
        statement.setString(3, usuario.getCpf());
        statement.setString(4, usuario.getSenha());
        statement.setString(5, usuario.getCidade());
        statement.setString(6, usuario.getBairro());
        statement.setInt(7, usuario.getTotalPontos());

        if (incluirId) {
            statement.setInt(8, usuario.getId());
        }
    }

    private Usuario montar(ResultSet resultSet)
            throws SQLException {

        return new Usuario(
                resultSet.getInt("id_usuario"),
                resultSet.getString("nome_usuario"),
                resultSet.getString("email_usuario"),
                resultSet.getString("cpf_usuario"),
                resultSet.getString("senha_usuario"),
                resultSet.getString("cidade_usuario"),
                resultSet.getString("bairro_usuario"),
                resultSet.getInt("total_pontos_usuario")
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