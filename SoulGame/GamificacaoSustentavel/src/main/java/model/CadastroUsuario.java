package model;

import java.time.LocalDate;

public class CadastroUsuario {

    private String nome;
    private String email;
    private String senha;
    private String telefone;
    private LocalDate dataNascimento;
    private String cidade;
    private String estado;
    private String tipo;
    private String indicadoPorCodigo;

    public CadastroUsuario() {
    }

    public boolean validarCadastro() {
        return nome != null
                && !nome.isBlank()
                && email != null
                && email.contains("@")
                && senha != null
                && !senha.isBlank()
                && cidade != null
                && !cidade.isBlank()
                && tipo != null
                && !tipo.isBlank();
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getIndicadoPorCodigo() {
        return indicadoPorCodigo;
    }

    public void setIndicadoPorCodigo(String indicadoPorCodigo) {
        this.indicadoPorCodigo = indicadoPorCodigo;
    }
}