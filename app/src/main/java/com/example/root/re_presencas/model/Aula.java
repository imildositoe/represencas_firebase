package com.example.root.re_presencas.model;

import android.support.annotation.NonNull;

public class Aula {

    private String id;
    private String id_sala;
    private String id_alocacao;
    private String data;
    private boolean is_selado;

    public Aula() {
    }

    public Aula(String id, String id_sala, String id_alocacao, String data, boolean is_selado) {
        this.id = id;
        this.id_sala = id_sala;
        this.id_alocacao = id_alocacao;
        this.data = data;
        this.is_selado = is_selado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_sala() {
        return id_sala;
    }

    public void setId_sala(String id_sala) {
        this.id_sala = id_sala;
    }

    public String getId_alocacao() {
        return id_alocacao;
    }

    public void setId_alocacao(String id_alocacao) {
        this.id_alocacao = id_alocacao;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isIs_selado() {
        return is_selado;
    }

    public void setIs_selado(boolean is_selado) {
        this.is_selado = is_selado;
    }


    @NonNull
    @Override
    public String toString() {
        return "Aula{" +
                "id='" + id + '\'' +
                ", id_sala='" + id_sala + '\'' +
                ", id_alocacao='" + id_alocacao + '\'' +
                ", data='" + data + '\'' +
                ", is_selado=" + is_selado +
                '}';
    }
}
