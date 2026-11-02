package model;

import java.time.LocalDate;

public class DesafioAgendado {

    private int id;
    private String usuarioId;
    private String acaoSlug;
    private LocalDate prazo;
    private boolean vencido;

    public DesafioAgendado() {
    }

    public DesafioAgendado(
            int id,
            String usuarioId,
            String acaoSlug,
            LocalDate prazo,
            boolean vencido) {

        this.id = id;
        this.usuarioId = usuarioId;
        this.acaoSlug = acaoSlug;
        this.prazo = prazo;
        this.vencido = vencido;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getAcaoSlug() {
        return acaoSlug;
    }

    public void setAcaoSlug(String acaoSlug) {
        this.acaoSlug = acaoSlug;
    }

    public LocalDate getPrazo() {
        return prazo;
    }

    public void setPrazo(LocalDate prazo) {
        this.prazo = prazo;
    }

    public boolean isVencido() {
        return vencido;
    }

    public void setVencido(boolean vencido) {
        this.vencido = vencido;
    }
}