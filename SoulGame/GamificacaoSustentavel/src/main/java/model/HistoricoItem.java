package model;

import java.time.LocalDateTime;

public class HistoricoItem {

    private int registroId;
    private String acaoSlug;
    private String nomeAcao;
    private int pontosObtidos;
    private LocalDateTime dataHora;

    public HistoricoItem() {
    }

    public HistoricoItem(
            int registroId,
            String acaoSlug,
            String nomeAcao,
            int pontosObtidos,
            LocalDateTime dataHora) {

        this.registroId = registroId;
        this.acaoSlug = acaoSlug;
        this.nomeAcao = nomeAcao;
        this.pontosObtidos = pontosObtidos;
        this.dataHora = dataHora;
    }

    public int getRegistroId() {
        return registroId;
    }

    public void setRegistroId(int registroId) {
        this.registroId = registroId;
    }

    public String getAcaoSlug() {
        return acaoSlug;
    }

    public void setAcaoSlug(String acaoSlug) {
        this.acaoSlug = acaoSlug;
    }

    public String getNomeAcao() {
        return nomeAcao;
    }

    public void setNomeAcao(String nomeAcao) {
        this.nomeAcao = nomeAcao;
    }

    public int getPontosObtidos() {
        return pontosObtidos;
    }

    public void setPontosObtidos(int pontosObtidos) {
        this.pontosObtidos = pontosObtidos;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }
}