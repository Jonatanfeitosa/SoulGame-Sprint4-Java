package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class Campeonato {

    private int id;
    private String nome;
    private String escopo;
    private String estado;
    private Integer odsId;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String premiacao;
    private Integer usuarioCriadorId;
    private String status;

    public Campeonato() {
    }

    public Campeonato(
            int id,
            String nome,
            String escopo,
            String estado,
            Integer odsId,
            LocalDate dataInicio,
            LocalDate dataFim,
            String premiacao,
            Integer usuarioCriadorId,
            String status) {

        this.id = id;
        this.nome = nome;
        this.escopo = escopo;
        this.estado = estado;
        this.odsId = odsId;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.premiacao = premiacao;
        this.usuarioCriadorId = usuarioCriadorId;
        this.status = status;
    }

    public boolean validar() {
        return nome != null
                && !nome.isBlank()
                && escopo != null
                && !escopo.isBlank()
                && dataInicio != null
                && dataFim != null
                && !dataFim.isBefore(dataInicio);
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

    public String getEscopo() {
        return escopo;
    }

    public void setEscopo(String escopo) {
        this.escopo = escopo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @JsonIgnore
    public Integer getOdsId() {
        return odsId;
    }

    public void setOdsId(Integer odsId) {
        this.odsId = odsId;
    }

    @JsonProperty("odsRelacionadoId")
    public Integer getOdsRelacionadoId() {
        return odsId;
    }

    @JsonProperty("odsRelacionadoId")
    public void setOdsRelacionadoId(Integer odsRelacionadoId) {
        this.odsId = odsRelacionadoId;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public String getPremiacao() {
        return premiacao;
    }

    public void setPremiacao(String premiacao) {
        this.premiacao = premiacao;
    }

    @JsonIgnore
    public Integer getUsuarioCriadorId() {
        return usuarioCriadorId;
    }

    public void setUsuarioCriadorId(Integer usuarioCriadorId) {
        this.usuarioCriadorId = usuarioCriadorId;
    }

    @JsonProperty("usuarioAdminId")
    public Integer getUsuarioAdminId() {
        return usuarioCriadorId;
    }

    @JsonProperty("usuarioAdminId")
    public void setUsuarioAdminId(Integer usuarioAdminId) {
        this.usuarioCriadorId = usuarioAdminId;
    }

    public String getStatus() {
        if ("ATIVO".equalsIgnoreCase(status)) {
            LocalDate hoje = LocalDate.now();

            if (dataInicio != null && hoje.isBefore(dataInicio)) {
                return "AGENDADO";
            }

            return "EM_ANDAMENTO";
        }

        return status;
    }

    public void setStatus(String status) {
        if ("AGENDADO".equalsIgnoreCase(status)
                || "EM_ANDAMENTO".equalsIgnoreCase(status)) {
            this.status = "ATIVO";
        } else {
            this.status = status;
        }
    }

    @JsonProperty("emAndamento")
    public boolean isEmAndamento() {
        LocalDate hoje = LocalDate.now();

        return dataInicio != null
                && dataFim != null
                && !hoje.isBefore(dataInicio)
                && !hoje.isAfter(dataFim)
                && !"ENCERRADO".equalsIgnoreCase(status);
    }
}