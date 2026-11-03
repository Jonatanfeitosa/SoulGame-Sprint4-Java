package model;

import interfaces.Calculavel;

public class AcaoSustentavel implements Calculavel {

    private int id;
    private String nome;
    private String objetivoSustentavel;
    private int pontuacaoBase;
    private String nivelDificuldade;
    private String descricao;
    private String slugAcao;
    private Integer odsId;

    public AcaoSustentavel() {
    }

    public AcaoSustentavel(
            int id,
            String nome,
            String objetivoSustentavel,
            int pontuacaoBase,
            String nivelDificuldade,
            String descricao,
            String slugAcao,
            Integer odsId) {

        this.id = id;
        this.nome = nome;
        this.objetivoSustentavel = objetivoSustentavel;
        this.pontuacaoBase = pontuacaoBase;
        this.nivelDificuldade = nivelDificuldade;
        this.descricao = descricao;
        this.slugAcao = slugAcao;
        this.odsId = odsId;
    }

    public AcaoSustentavel(
            String nome,
            String objetivoSustentavel,
            int pontuacaoBase,
            String nivelDificuldade,
            String descricao,
            String slugAcao,
            Integer odsId) {

        this(
                0,
                nome,
                objetivoSustentavel,
                pontuacaoBase,
                nivelDificuldade,
                descricao,
                slugAcao,
                odsId
        );
    }

    public boolean validarAcao() {
        return nome != null
                && !nome.isBlank()
                && objetivoSustentavel != null
                && !objetivoSustentavel.isBlank()
                && pontuacaoBase > 0
                && ("BAIXA".equalsIgnoreCase(nivelDificuldade)
                || "MEDIA".equalsIgnoreCase(nivelDificuldade)
                || "ALTA".equalsIgnoreCase(nivelDificuldade))
                && slugAcao != null
                && !slugAcao.isBlank()
                && odsId != null
                && odsId > 0;
    }

    @Override
    public int calcularPontuacao() {

        if (!validarAcao()) {
            return 0;
        }

        if ("ALTA".equalsIgnoreCase(nivelDificuldade)) {
            return pontuacaoBase + 20;
        }

        if ("MEDIA".equalsIgnoreCase(nivelDificuldade)) {
            return pontuacaoBase + 10;
        }

        return pontuacaoBase;
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

    public String getObjetivoSustentavel() {
        return objetivoSustentavel;
    }

    public void setObjetivoSustentavel(String objetivoSustentavel) {
        this.objetivoSustentavel = objetivoSustentavel;
    }

    public int getPontuacaoBase() {
        return pontuacaoBase;
    }

    public void setPontuacaoBase(int pontuacaoBase) {
        this.pontuacaoBase = pontuacaoBase;
    }

    public String getNivelDificuldade() {
        return nivelDificuldade;
    }

    public void setNivelDificuldade(String nivelDificuldade) {
        this.nivelDificuldade = nivelDificuldade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getSlugAcao() {
        return slugAcao;
    }

    public void setSlugAcao(String slugAcao) {
        this.slugAcao = slugAcao;
    }

    public Integer getOdsId() {
        return odsId;
    }

    public void setOdsId(Integer odsId) {
        this.odsId = odsId;
    }
}