package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoOracleProjeto {

    private static final String URL_PADRAO =
            "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";

    private static final String USUARIO_PADRAO =
            "rm570452";

    private static final String SENHA_PADRAO =
            "130398";

    private ConexaoOracleProjeto() {
    }

    public static Connection conectar() throws SQLException {

        String url = obterVariavelAmbiente(
                "DB_URL",
                URL_PADRAO
        );

        String usuario = obterVariavelAmbiente(
                "DB_USER",
                USUARIO_PADRAO
        );

        String senha = obterVariavelAmbiente(
                "DB_PASSWORD",
                SENHA_PADRAO
        );

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