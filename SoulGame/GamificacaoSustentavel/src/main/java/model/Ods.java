package model;

public class Ods {

    private int id;
    private String nome;
    private String descricao;
    private String corHex;
    private String icone;

    public Ods() {
    }

    public Ods(
            int id,
            String nome,
            String descricao,
            String corHex,
            String icone) {

        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.corHex = corHex;
        this.icone = icone;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCorHex() {
        return corHex;
    }

    public void setCorHex(String corHex) {
        this.corHex = corHex;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }
}