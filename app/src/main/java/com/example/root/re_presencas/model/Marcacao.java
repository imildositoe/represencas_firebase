package com.example.root.re_presencas.model;

import android.support.annotation.NonNull;

import java.util.Date;

public class Marcacao {

    private String id;
    private String id_aula;
    private String id_inscricao;
    private boolean is_presente;
    private String data;

    public Marcacao() {
    }

    public Marcacao(String id, String id_aula, String id_inscricao, boolean is_presente, String data) {
        this.id = id;
        this.id_aula = id_aula;
        this.id_inscricao = id_inscricao;
        this.is_presente = is_presente;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_aula() {
        return id_aula;
    }

    public void setId_aula(String id_aula) {
        this.id_aula = id_aula;
    }

    public String getId_inscricao() {
        return id_inscricao;
    }

    public void setId_inscricao(String id_inscricao) {
        this.id_inscricao = id_inscricao;
    }

    public boolean isIs_presente() {
        return is_presente;
    }

    public void setIs_presente(boolean is_presente) {
        this.is_presente = is_presente;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @NonNull
    @Override
    public String toString() {
        return "Marcacao{" +
                "id='" + id + '\'' +
                ", id_aula='" + id_aula + '\'' +
                ", id_inscricao='" + id_inscricao + '\'' +
                ", is_presente='" + is_presente + '\'' +
                ", data=" + data +
                '}';
    }
}
