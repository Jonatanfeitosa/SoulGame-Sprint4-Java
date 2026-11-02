package model;

public class Usuario {

    private int id;
    private String nome;
    private String email;
    private String cpf;
    private String senha;
    private String cidade;
    private String bairro;
    private int totalPontos;

    public Usuario() {
    }

    public Usuario(int id, String nome, String email, String cpf, String senha,
                   String cidade, String bairro, int totalPontos) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.senha = senha;
        this.cidade = cidade;
        this.bairro = bairro;
        this.totalPontos = totalPontos;
    }

    public Usuario(String nome, String email, String cpf, String senha,
                   String cidade, String bairro, int totalPontos) {
        this(0, nome, email, cpf, senha, cidade, bairro, totalPontos);
    }

    public boolean validarCadastro() {
        return nome != null && !nome.isBlank()
                && email != null && email.contains("@")
                && cpf != null && cpf.length() == 11
                && senha != null && !senha.isBlank()
                && cidade != null && !cidade.isBlank()
                && bairro != null && !bairro.isBlank()
                && totalPontos >= 0;
    }

    public void adicionarPontos(int pontos) {
        if (pontos > 0) {
            totalPontos += pontos;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public int getTotalPontos() {
        return totalPontos;
    }

    public void setTotalPontos(int totalPontos) {
        this.totalPontos = totalPontos;
    }
}