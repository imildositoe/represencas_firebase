package com.example.root.re_presencas.model;

import android.support.annotation.NonNull;

public class Aula {

    private String id;
    private String id_sala;
    private String id_alocacao;
    private String data;

    public Aula() {
    }

    public Aula(String id, String id_sala, String id_alocacao, String data) {
        this.id = id;
        this.id_sala = id_sala;
        this.id_alocacao = id_alocacao;
        this.data = data;
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

    @NonNull
    @Override
    public String toString() {
        return "Aula{" +
                "id='" + id + '\'' +
                ", id_sala='" + id_sala + '\'' +
                ", id_alocacao='" + id_alocacao + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
