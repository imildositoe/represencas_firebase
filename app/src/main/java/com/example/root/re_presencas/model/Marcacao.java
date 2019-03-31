package com.example.root.re_presencas.model;

import java.util.Date;

public class Marcacao {

    private String id;
    private String isPresente;
    private Date data;
    private String idAula;

    public Marcacao() {
    }

    public Marcacao(String id, String isPresente, Date data, String idAula) {
        this.id = id;
        this.isPresente = isPresente;
        this.data = data;
        this.idAula = idAula;
    }

    public Marcacao(String isPresente, Date data, String idAula) {
        this.isPresente = isPresente;
        this.data = data;
        this.idAula = idAula;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsPresente() {
        return isPresente;
    }

    public void setIsPresente(String isPresente) {
        this.isPresente = isPresente;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getIdAula() {
        return idAula;
    }

    public void setIdAula(String idAula) {
        this.idAula = idAula;
    }
}
