package com.example.root.re_presencas.model;

import android.support.annotation.NonNull;

public class Inscricao {

    private String id;
    private int ano;
    private String id_estudante;
    private String id_disciplina;
    private String periodo;

    public Inscricao() {
    }

    public Inscricao(String id, int ano, String id_estudante, String id_disciplina, String periodo) {
        this.id = id;
        this.ano = ano;
        this.id_estudante = id_estudante;
        this.id_disciplina = id_disciplina;
        this.periodo = periodo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getId_estudante() {
        return id_estudante;
    }

    public void setId_estudante(String id_estudante) {
        this.id_estudante = id_estudante;
    }

    public String getId_disciplina() {
        return id_disciplina;
    }

    public void setId_disciplina(String id_disciplina) {
        this.id_disciplina = id_disciplina;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    @NonNull
    @Override
    public String toString() {
        return "Inscricao{" +
                "id='" + id + '\'' +
                ", ano=" + ano +
                ", id_estudante='" + id_estudante + '\'' +
                ", id_disciplina='" + id_disciplina + '\'' +
                ", periodo='" + periodo + '\'' +
                '}';
    }
}
