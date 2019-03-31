package com.example.root.re_presencas.model;

public class Aula {

    private String id;
    private String idSala;
    private String idAlocacao;
    private String idInscricao;

    public Aula() {
    }

    public Aula(String id, String idSala, String idAlocacao, String idInscricao) {
        this.id = id;
        this.idSala = idSala;
        this.idAlocacao = idAlocacao;
        this.idInscricao = idInscricao;
    }

    public Aula(String idSala, String idAlocacao, String idInscricao) {
        this.idSala = idSala;
        this.idAlocacao = idAlocacao;
        this.idInscricao = idInscricao;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdSala() {
        return idSala;
    }

    public void setIdSala(String idSala) {
        this.idSala = idSala;
    }

    public String getIdAlocacao() {
        return idAlocacao;
    }

    public void setIdAlocacao(String idAlocacao) {
        this.idAlocacao = idAlocacao;
    }

    public String getIdInscricao() {
        return idInscricao;
    }

    public void setIdInscricao(String idInscricao) {
        this.idInscricao = idInscricao;
    }
}
