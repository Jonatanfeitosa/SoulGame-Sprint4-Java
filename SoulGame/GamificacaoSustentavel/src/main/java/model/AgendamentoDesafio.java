package model;

import java.time.LocalDate;

public class AgendamentoDesafio {

    private String usuarioId;
    private String acaoSlug;
    private LocalDate prazo;

    public AgendamentoDesafio() {
    }

    public boolean validar() {
        return usuarioId != null
                && !usuarioId.isBlank()
                && acaoSlug != null
                && !acaoSlug.isBlank()
                && prazo != null;
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
}