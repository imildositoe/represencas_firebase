package com.example.root.re_presencas.model;

import android.support.annotation.NonNull;

public class Docente {

    private String id;
    private String foto;
    private String nome;
    private String email;
    private String senha;

    public Docente() {
    }

    public Docente(String id, String foto, String nome, String email, String senha) {
        this.id = id;
        this.foto = foto;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public Docente(String id, String nome, String email, String senha) {
        this.id = id;
        this.foto = foto;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }


    public Docente(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @NonNull
    @Override
    public String toString() {
        return "Docente{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                '}';
    }
}
