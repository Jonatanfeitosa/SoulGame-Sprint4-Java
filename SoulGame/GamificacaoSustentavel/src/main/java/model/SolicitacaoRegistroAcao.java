package model;

public class SolicitacaoRegistroAcao {

    private int usuarioId;
    private String acaoSlug;
    private String imagemBase64;
    private String tipoEvidencia;

    public SolicitacaoRegistroAcao() {
    }

    public SolicitacaoRegistroAcao(
            int usuarioId,
            String acaoSlug,
            String imagemBase64,
            String tipoEvidencia) {

        this.usuarioId = usuarioId;
        this.acaoSlug = acaoSlug;
        this.imagemBase64 = imagemBase64;
        this.tipoEvidencia = tipoEvidencia;
    }

    public boolean validar() {
        return usuarioId > 0
                && acaoSlug != null
                && !acaoSlug.isBlank()
                && imagemBase64 != null
                && !imagemBase64.isBlank()
                && tipoEvidencia != null
                && !tipoEvidencia.isBlank();
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getAcaoSlug() {
        return acaoSlug;
    }

    public void setAcaoSlug(String acaoSlug) {
        this.acaoSlug = acaoSlug;
    }

    public String getImagemBase64() {
        return imagemBase64;
    }

    public void setImagemBase64(String imagemBase64) {
        this.imagemBase64 = imagemBase64;
    }

    public String getTipoEvidencia() {
        return tipoEvidencia;
    }

    public void setTipoEvidencia(String tipoEvidencia) {
        this.tipoEvidencia = tipoEvidencia;
    }
}