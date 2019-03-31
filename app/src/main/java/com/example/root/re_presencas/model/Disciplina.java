package com.example.root.re_presencas.model;

import android.support.annotation.NonNull;

public class Disciplina {
    private String id;
    private String designacao;

    public Disciplina() {
    }

    public Disciplina(String id, String designacao) {
        this.id = id;
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

    @NonNull
    @Override
    public String toString() {
        return "Disciplina{" +
                "id='" + id + '\'' +
                ", designacao='" + designacao + '\'' +
                '}';
    }
}
