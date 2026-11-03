
package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoOracleProjeto {

    private static final String URL_PADRAO =
            "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";

    private ConexaoOracleProjeto() {
    }

    public static Connection conectar() throws SQLException {

        String url = obterVariavelAmbiente(
                "DB_URL",
                URL_PADRAO
        );

        String usuario = obterVariavelObrigatoria("DB_USER");
        String senha = obterVariavelObrigatoria("DB_PASSWORD");

        return DriverManager.getConnection(
                url,
                usuario,
                senha
        );
    }

    private static String obterVariavelAmbiente(
            String nome,
            String valorPadrao) {

        String valor = System.getenv(nome);

        if (valor == null || valor.isBlank()) {
            return valorPadrao;
        }

        return valor;
    }

    private static String obterVariavelObrigatoria(
            String nome) throws SQLException {

        String valor = System.getenv(nome);

        if (valor == null || valor.isBlank()) {
            throw new SQLException(
                    "Variavel de ambiente obrigatoria nao configurada: "
                            + nome
            );
        }

        return valor;
    }

    public static boolean testarConexao() {

        Connection connection = null;

        try {

            connection = conectar();

            return connection != null
                    && !connection.isClosed();

        } catch (SQLException e) {

            System.out.println(
                    "Erro ao testar Oracle: "
                            + e.getMessage()
            );

            return false;

        } finally {

            if (connection != null) {

                try {

                    connection.close();

                } catch (SQLException e) {

                    System.out.println(
                            "Erro ao fechar Oracle: "
                                    + e.getMessage()
                    );
                }
            }
        }
    }
}
