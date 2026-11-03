package model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class Usuario {

    private int id;
    private String nome;
    private String email;
    private String cpf;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String senha;

    private String telefone;
    private String cidade;
    private String estado;
    private String bairro;
    private String tipo;
    private int totalPontos;
    private int nivel;
    private int streakDias;
    private String codigoIndicacao;
    private Integer indicadoPor;
    private String avatarUrl;
    private String avatarTipo;
    private LocalDate dataNascimento;

    public Usuario() {
    }

    public Usuario(
            int id,
            String nome,
            String email,
            String cpf,
            String senha,
            String telefone,
            String cidade,
            String estado,
            String bairro,
            String tipo,
            int totalPontos,
            int nivel,
            int streakDias,
            String codigoIndicacao,
            Integer indicadoPor,
            String avatarUrl,
            String avatarTipo,
            LocalDate dataNascimento) {

        this.id = id;
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.senha = senha;
        this.telefone = telefone;
        this.cidade = cidade;
        this.estado = estado;
        this.bairro = bairro;
        this.tipo = tipo;
        this.totalPontos = totalPontos;
        this.nivel = nivel;
        this.streakDias = streakDias;
        this.codigoIndicacao = codigoIndicacao;
        this.indicadoPor = indicadoPor;
        this.avatarUrl = avatarUrl;
        this.avatarTipo = avatarTipo;
        this.dataNascimento = dataNascimento;
    }

    public Usuario(
            String nome,
            String email,
            String cpf,
            String senha,
            String cidade,
            String bairro,
            int totalPontos) {

        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.senha = senha;
        this.telefone = null;
        this.cidade = cidade;
        this.estado = null;
        this.bairro = bairro;
        this.tipo = "soulgamer";
        this.totalPontos = totalPontos;
        this.nivel = 1;
        this.streakDias = 0;
        this.codigoIndicacao = null;
        this.indicadoPor = null;
        this.avatarUrl = null;
        this.avatarTipo = null;
        this.dataNascimento = null;
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
                && !tipo.isBlank()
                && totalPontos >= 0
                && nivel >= 1
                && streakDias >= 0;
    }

    public void adicionarPontos(int pontos) {
        if (pontos > 0) {
            totalPontos += pontos;
        }
    }

    public boolean isEhValidador() {
        return "validador".equalsIgnoreCase(tipo);
    }

    public boolean isEhMultiplicador() {
        return "soulfluencer".equalsIgnoreCase(tipo);
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
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

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getTotalPontos() {
        return totalPontos;
    }

    public void setTotalPontos(int totalPontos) {
        this.totalPontos = totalPontos;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getStreakDias() {
        return streakDias;
    }

    public void setStreakDias(int streakDias) {
        this.streakDias = streakDias;
    }

    public String getCodigoIndicacao() {
        return codigoIndicacao;
    }

    public void setCodigoIndicacao(String codigoIndicacao) {
        this.codigoIndicacao = codigoIndicacao;
    }

    public Integer getIndicadoPor() {
        return indicadoPor;
    }

    public void setIndicadoPor(Integer indicadoPor) {
        this.indicadoPor = indicadoPor;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getAvatarTipo() {
        return avatarTipo;
    }

    public void setAvatarTipo(String avatarTipo) {
        this.avatarTipo = avatarTipo;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
}