package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoOracleProjeto {
    private static final String URL = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
    private static final String USUARIO = "rm569584";
    private static final String SENHA = "030699";

    private ConexaoOracleProjeto() {
    }

    public static Connection conectar() throws SQLException {
        if (URL.startsWith("COLOCAR_")
                || USUARIO.startsWith("COLOCAR_")
                || SENHA.startsWith("COLOCAR_")) {
            throw new SQLException(
                    "Preencha URL, USUARIO e SENHA do Oracle em ConexaoOracleProjeto.java."
            );
        }

        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }

    public static boolean testarConexao() {
        Connection connection = null;

        try {
            connection = conectar();
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            System.out.println("Erro ao testar Oracle: " + e.getMessage());
            return false;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Erro ao fechar Oracle: " + e.getMessage());
                }
            }
        }
    }
}
