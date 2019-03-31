package com.example.root.re_presencas.model;

import java.util.Date;

public class Inscricao {

    private String id;
    private String idEstudante;
    private String idDisciplina;
    private Date ano;

    public Inscricao() {
    }

    public Inscricao(String id, String idEstudante, String idDisciplina, Date ano) {
        this.id = id;
        this.idEstudante = idEstudante;
        this.idDisciplina = idDisciplina;
        this.ano = ano;
    }

    public Inscricao(String idEstudante, String idDisciplina, Date ano) {
        this.idEstudante = idEstudante;
        this.idDisciplina = idDisciplina;
        this.ano = ano;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdEstudante() {
        return idEstudante;
    }

    public void setIdEstudante(String idEstudante) {
        this.idEstudante = idEstudante;
    }

    public String getIdDisciplina() {
        return idDisciplina;
    }

    public void setIdDisciplina(String idDisciplina) {
        this.idDisciplina = idDisciplina;
    }

    public Date getAno() {
        return ano;
    }

    public void setAno(Date ano) {
        this.ano = ano;
    }
}
