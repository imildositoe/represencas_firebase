package com.example.root.re_presencas.model;

public class Periodo {

    private String id;
    private String designacao;

    public Periodo() {
    }

    public Periodo(String id, String designacao) {
        this.id = id;
        this.designacao = designacao;
    }

    public Periodo(String designacao) {
        this.designacao = designacao;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesignacao() {
        return designacao;
    }

    public void setDesignacao(String designacao) {
        this.designacao = designacao;
    }
}
