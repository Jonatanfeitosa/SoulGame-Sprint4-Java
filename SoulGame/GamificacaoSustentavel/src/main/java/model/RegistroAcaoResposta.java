package model;

public class RegistroAcaoResposta {

    private int id;
    private String acaoSlug;
    private String statusValidacao;
    private int pontosObtidos;

    public RegistroAcaoResposta() {
    }

    public RegistroAcaoResposta(
            int id,
            String acaoSlug,
            String statusValidacao,
            int pontosObtidos) {

        this.id = id;
        this.acaoSlug = acaoSlug;
        this.statusValidacao = statusValidacao;
        this.pontosObtidos = pontosObtidos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAcaoSlug() {
        return acaoSlug;
    }

    public void setAcaoSlug(String acaoSlug) {
        this.acaoSlug = acaoSlug;
    }

    public String getStatusValidacao() {
        return statusValidacao;
    }

    public void setStatusValidacao(String statusValidacao) {
        this.statusValidacao = statusValidacao;
    }

    public int getPontosObtidos() {
        return pontosObtidos;
    }

    public void setPontosObtidos(int pontosObtidos) {
        this.pontosObtidos = pontosObtidos;
    }
}