package dao;

import conexao.ConexaoOracleProjeto;
import model.Ods;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OdsDAO {

    public List<Ods> listarTodos() {

        List<Ods> lista = new ArrayList<>();

        String sql =
                "SELECT id_ods, nome_ods, descricao_ods, cor_hex, icone "
                        + "FROM TB_ODS "
                        + "ORDER BY id_ods";

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
                        new Ods(
                                resultSet.getInt("id_ods"),
                                resultSet.getString("nome_ods"),
                                resultSet.getString("descricao_ods"),
                                resultSet.getString("cor_hex"),
                                resultSet.getString("icone")
                        )
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "Erro ao listar ODS: "
                            + e.getMessage()
            );
        }

        return lista;
    }
}