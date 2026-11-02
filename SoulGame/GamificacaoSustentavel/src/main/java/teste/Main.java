package teste;

import conexao.ConexaoOracleProjeto;
import dao.AcaoSustentavelDAO;
import dao.RegistroAcaoDAO;
import dao.UsuarioDAO;
import model.AcaoSustentavel;
import model.RegistroAcao;
import model.Usuario;
import service.Ranking;
import service.SistemaGamificacao;

public class Main {

    public static void main(String[] args) {
        System.out.println("Conexao Oracle: " + ConexaoOracleProjeto.testarConexao());

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        AcaoSustentavelDAO acaoDAO = new AcaoSustentavelDAO();
        RegistroAcaoDAO registroDAO = new RegistroAcaoDAO();

        Usuario usuario = new Usuario(
                "Pedro Silva",
                "pedro.tm@teste.com",
                "12345678901",
                "1234",
                "Sao Paulo",
                "Centro",
                0
        );

        AcaoSustentavel acao = new AcaoSustentavel(
                "Reciclagem de materiais",
                "Consumo responsavel",
                40,
                "MEDIA",
                "Separacao de plastico e papel"
        );

        System.out.println("=== REGRAS DE NEGOCIO ===");
        System.out.println("Usuario valido: " + usuario.validarCadastro());
        System.out.println("Acao valida: " + acao.validarAcao());
        System.out.println("Pontuacao calculada: " + acao.calcularPontuacao());

        System.out.println("\n=== CRUD USUARIO ===");
        System.out.println("CREATE: " + usuarioDAO.criar(usuario));
        System.out.println("READ: " + (usuarioDAO.buscarPorId(usuario.getId()) != null));
        usuario.setBairro("Paulista");
        System.out.println("UPDATE: " + usuarioDAO.atualizar(usuario));
        System.out.println("LISTAR: " + usuarioDAO.listarTodos().size());

        System.out.println("\n=== CRUD ACAO ===");
        System.out.println("CREATE: " + acaoDAO.criar(acao));
        System.out.println("READ: " + (acaoDAO.buscarPorId(acao.getId()) != null));
        acao.setDescricao("Reciclagem de plastico, papel e vidro");
        System.out.println("UPDATE: " + acaoDAO.atualizar(acao));
        System.out.println("LISTAR: " + acaoDAO.listarTodas().size());

        SistemaGamificacao sistema = new SistemaGamificacao();
        RegistroAcao registro = sistema.registrarAcao(usuario, acao);

        System.out.println("\n=== CRUD REGISTRO DE ACAO ===");
        System.out.println("CREATE: " + registroDAO.criar(registro));
        System.out.println("READ: " + (registroDAO.buscarPorId(registro.getId()) != null));
        registro.setStatusValidacao("VALIDADO");
        System.out.println("UPDATE: " + registroDAO.atualizar(registro));
        System.out.println("LISTAR: " + registroDAO.listarTodos().size());

        Ranking ranking = new Ranking();
        ranking.adicionarUsuario(usuario);

        System.out.println("\n=== RANKING ===");
        ranking.exibirRanking();

        System.out.println("\n=== DELETE ===");
        System.out.println("REGISTRO: " + registroDAO.excluir(registro.getId()));
        System.out.println("ACAO: " + acaoDAO.excluir(acao.getId()));
        System.out.println("USUARIO: " + usuarioDAO.excluir(usuario.getId()));
        System.out.println("\nTeste finalizado.");
    }
}
