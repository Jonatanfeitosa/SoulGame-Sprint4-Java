package model;

public class AtualizacaoPerfil {

    private String nome;
    private String telefone;
    private String cidade;
    private String estado;
    private String avatarTipo;
    private String avatarPersonagemId;
    private String avatarFotoBase64;

    public AtualizacaoPerfil() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public String getAvatarTipo() {
        return avatarTipo;
    }

    public void setAvatarTipo(String avatarTipo) {
        this.avatarTipo = avatarTipo;
    }

    public String getAvatarPersonagemId() {
        return avatarPersonagemId;
    }

    public void setAvatarPersonagemId(String avatarPersonagemId) {
        this.avatarPersonagemId = avatarPersonagemId;
    }

    public String getAvatarFotoBase64() {
        return avatarFotoBase64;
    }

    public void setAvatarFotoBase64(String avatarFotoBase64) {
        this.avatarFotoBase64 = avatarFotoBase64;
    }
}