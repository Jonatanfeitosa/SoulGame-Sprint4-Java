package model;

public class InscricaoCampeonato {

    private int campeonatoId;
    private int usuarioId;

    public InscricaoCampeonato() {
    }

    public InscricaoCampeonato(
            int campeonatoId,
            int usuarioId) {

        this.campeonatoId = campeonatoId;
        this.usuarioId = usuarioId;
    }

    public boolean validar() {
        return campeonatoId > 0
                && usuarioId > 0;
    }

    public int getCampeonatoId() {
        return campeonatoId;
    }

    public void setCampeonatoId(int campeonatoId) {
        this.campeonatoId = campeonatoId;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }
}