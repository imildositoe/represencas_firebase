package com.example.root.re_presencas.model;

import android.support.annotation.NonNull;

public class Alocacao {

    private String id;
    private String id_docente;
    private String id_disciplina;
    private int ano;
    private String periodo;
    private int nr_aulas;
    private boolean estado_semestre;


    public Alocacao() {
    }

    public Alocacao(String id, String id_docente, String id_disciplina, int ano, String periodo, int nr_aulas, boolean estado_semestre) {
        this.id = id;
        this.id_docente = id_docente;
        this.id_disciplina = id_disciplina;
        this.ano = ano;
        this.periodo = periodo;
        this.nr_aulas = nr_aulas;
        this.estado_semestre = estado_semestre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_docente() {
        return id_docente;
    }

    public void setId_docente(String id_docente) {
        this.id_docente = id_docente;
    }

    public String getId_disciplina() {
        return id_disciplina;
    }

    public void setId_disciplina(String id_disciplina) {
        this.id_disciplina = id_disciplina;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public int getNr_aulas() {
        return nr_aulas;
    }

    public void setNr_aulas(int nr_aulas) {
        this.nr_aulas = nr_aulas;
    }

    public boolean isEstado_semestre() {
        return estado_semestre;
    }

    public void setEstado_semestre(boolean estado_semestre) {
        this.estado_semestre = estado_semestre;
    }

    @NonNull
    @Override
    public String toString() {
        return "Alocacao{" +
                "id='" + id + '\'' +
                ", id_docente='" + id_docente + '\'' +
                ", id_disciplina='" + id_disciplina + '\'' +
                ", ano=" + ano +
                ", periodo='" + periodo + '\'' +
                ", nr_aulas=" + nr_aulas +
                ", estado_semestre=" + estado_semestre +
                '}';
    }
}
