
package dao;

import conexao.ConexaoOracleProjeto;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.MockedStatic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class RegistroAcaoDAOTest {

    @Test
    void deveRetornarNaoEncontradoQuandoRegistroNaoExiste()
            throws Exception {

        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);

        when(connection.prepareStatement(anyString()))
                .thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        try (MockedStatic<ConexaoOracleProjeto> conexao =
                     mockStatic(ConexaoOracleProjeto.class)) {

            conexao.when(ConexaoOracleProjeto::conectar)
                    .thenReturn(connection);

            RegistroAcaoDAO.ResultadoExclusao resultado =
                    new RegistroAcaoDAO().excluirComEstorno(999999);

            assertEquals(
                    RegistroAcaoDAO.ResultadoExclusao.NAO_ENCONTRADO,
                    resultado
            );

            verify(connection).setAutoCommit(false);
            verify(connection).rollback();
            verify(connection, never()).commit();
            verify(connection).close();
        }
    }

    @Test
    void deveImpedirExclusaoQuandoExistemDependencias()
            throws Exception {

        Connection connection = mock(Connection.class);
        PreparedStatement consultaRegistro = mock(PreparedStatement.class);
        PreparedStatement consultaDependencias = mock(PreparedStatement.class);

        ResultSet registro = mock(ResultSet.class);
        ResultSet dependencias = mock(ResultSet.class);

        when(connection.prepareStatement(anyString()))
                .thenReturn(consultaRegistro, consultaDependencias);

        when(consultaRegistro.executeQuery()).thenReturn(registro);
        when(consultaDependencias.executeQuery()).thenReturn(dependencias);

        when(registro.next()).thenReturn(true);
        when(registro.getInt("ID_USUARIO")).thenReturn(1);
        when(registro.getInt("PONTOS_OBTIDOS")).thenReturn(50);
        when(dependencias.next()).thenReturn(true);

        try (MockedStatic<ConexaoOracleProjeto> conexao =
                     mockStatic(ConexaoOracleProjeto.class)) {

            conexao.when(ConexaoOracleProjeto::conectar)
                    .thenReturn(connection);

            RegistroAcaoDAO.ResultadoExclusao resultado =
                    new RegistroAcaoDAO().excluirComEstorno(10);

            assertEquals(
                    RegistroAcaoDAO.ResultadoExclusao.POSSUI_DEPENDENCIAS,
                    resultado
            );

            verify(connection).setAutoCommit(false);
            verify(connection).rollback();
            verify(connection, never()).commit();
            verify(connection, times(2))
                    .prepareStatement(anyString());

            verify(consultaRegistro, never()).executeUpdate();
            verify(consultaDependencias, never()).executeUpdate();
            verify(connection).close();
        }
    }

    @Test
    void deveExcluirRegistroEstornarPontosEConfirmarTransacao()
            throws Exception {

        Connection connection = mock(Connection.class);

        PreparedStatement consultaRegistro = mock(PreparedStatement.class);
        PreparedStatement consultaDependencias = mock(PreparedStatement.class);
        PreparedStatement exclusao = mock(PreparedStatement.class);
        PreparedStatement estorno = mock(PreparedStatement.class);

        ResultSet registro = mock(ResultSet.class);
        ResultSet dependencias = mock(ResultSet.class);

        when(connection.prepareStatement(anyString()))
                .thenReturn(
                        consultaRegistro,
                        consultaDependencias,
                        exclusao,
                        estorno
                );

        when(consultaRegistro.executeQuery()).thenReturn(registro);
        when(consultaDependencias.executeQuery()).thenReturn(dependencias);

        when(registro.next()).thenReturn(true);
        when(registro.getInt("ID_USUARIO")).thenReturn(1);
        when(registro.getInt("PONTOS_OBTIDOS")).thenReturn(30);
        when(dependencias.next()).thenReturn(false);

        when(exclusao.executeUpdate()).thenReturn(1);
        when(estorno.executeUpdate()).thenReturn(1);

        try (MockedStatic<ConexaoOracleProjeto> conexao =
                     mockStatic(ConexaoOracleProjeto.class)) {

            conexao.when(ConexaoOracleProjeto::conectar)
                    .thenReturn(connection);

            RegistroAcaoDAO.ResultadoExclusao resultado =
                    new RegistroAcaoDAO().excluirComEstorno(10);

            assertEquals(
                    RegistroAcaoDAO.ResultadoExclusao.EXCLUIDO,
                    resultado
            );

            verify(connection).setAutoCommit(false);
            verify(consultaRegistro).setInt(1, 10);
            verify(consultaDependencias).setInt(1, 10);
            verify(consultaDependencias).setInt(2, 10);
            verify(exclusao).setInt(1, 10);
            verify(estorno).setInt(1, 30);
            verify(estorno).setInt(2, 1);

            InOrder ordem = inOrder(exclusao, estorno, connection);
            ordem.verify(exclusao).executeUpdate();
            ordem.verify(estorno).executeUpdate();
            ordem.verify(connection).commit();

            verify(connection, never()).rollback();
            verify(connection).close();
        }
    }

    @Test
    void deveExecutarRollbackQuandoEstornoFalha()
            throws Exception {

        Connection connection = mock(Connection.class);

        PreparedStatement consultaRegistro = mock(PreparedStatement.class);
        PreparedStatement consultaDependencias = mock(PreparedStatement.class);
        PreparedStatement exclusao = mock(PreparedStatement.class);
        PreparedStatement estorno = mock(PreparedStatement.class);

        ResultSet registro = mock(ResultSet.class);
        ResultSet dependencias = mock(ResultSet.class);

        when(connection.prepareStatement(anyString()))
                .thenReturn(
                        consultaRegistro,
                        consultaDependencias,
                        exclusao,
                        estorno
                );

        when(consultaRegistro.executeQuery()).thenReturn(registro);
        when(consultaDependencias.executeQuery()).thenReturn(dependencias);

        when(registro.next()).thenReturn(true);
        when(registro.getInt("ID_USUARIO")).thenReturn(1);
        when(registro.getInt("PONTOS_OBTIDOS")).thenReturn(30);
        when(dependencias.next()).thenReturn(false);

        when(exclusao.executeUpdate()).thenReturn(1);
        when(estorno.executeUpdate())
                .thenThrow(new SQLException("Falha simulada no estorno"));

        try (MockedStatic<ConexaoOracleProjeto> conexao =
                     mockStatic(ConexaoOracleProjeto.class)) {

            conexao.when(ConexaoOracleProjeto::conectar)
                    .thenReturn(connection);

            RegistroAcaoDAO.ResultadoExclusao resultado =
                    new RegistroAcaoDAO().excluirComEstorno(10);

            assertEquals(
                    RegistroAcaoDAO.ResultadoExclusao.ERRO,
                    resultado
            );

            InOrder ordem = inOrder(exclusao, estorno, connection);
            ordem.verify(exclusao).executeUpdate();
            ordem.verify(estorno).executeUpdate();
            ordem.verify(connection).rollback();

            verify(connection, never()).commit();
            verify(connection).close();
        }
    }
}
