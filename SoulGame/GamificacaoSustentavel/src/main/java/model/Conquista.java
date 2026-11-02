package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Conquista {

    private int id;
    private String nome;
    private String descricao;
    private String tipo;
    private Integer criterioValor;
    private Integer odsId;
    private String acaoSlug;
    private String status;

    public Conquista() {
    }

    public Conquista(
            int id,
            String nome,
            String descricao,
            String tipo,
            Integer criterioValor,
            Integer odsId,
            String acaoSlug,
            String status) {

        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.tipo = tipo;
        this.criterioValor = criterioValor;
        this.odsId = odsId;
        this.acaoSlug = acaoSlug;
        this.status = status;
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

    @JsonIgnore
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @JsonProperty("tipoCriterio")
    public String getTipoCriterio() {
        return tipo;
    }

    @JsonProperty("tipoCriterio")
    public void setTipoCriterio(String tipoCriterio) {
        this.tipo = tipoCriterio;
    }

    @JsonIgnore
    public Integer getCriterioValor() {
        return criterioValor;
    }

    public void setCriterioValor(Integer criterioValor) {
        this.criterioValor = criterioValor;
    }

    @JsonProperty("valorCriterio")
    public Integer getValorCriterio() {
        return criterioValor;
    }

    @JsonProperty("valorCriterio")
    public void setValorCriterio(Integer valorCriterio) {
        this.criterioValor = valorCriterio;
    }

    @JsonIgnore
    public Integer getOdsId() {
        return odsId;
    }

    public void setOdsId(Integer odsId) {
        this.odsId = odsId;
    }

    @JsonIgnore
    public String getAcaoSlug() {
        return acaoSlug;
    }

    public void setAcaoSlug(String acaoSlug) {
        this.acaoSlug = acaoSlug;
    }

    @JsonIgnore
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("icone")
    public String getIcone() {
        if (odsId != null) {
            return "ods" + odsId;
        }

        return "conquista";
    }
}