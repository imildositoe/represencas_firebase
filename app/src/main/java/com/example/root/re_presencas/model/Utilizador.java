package com.example.root.re_presencas.model;

public class Utilizador {

    private String id;
    private String email;
    private String senha;
    private String tipoUser;

    public Utilizador() {
    }

    public Utilizador(String id, String email, String senha, String tipoUser) {
        this.id = id;
        this.email = email;
        this.senha = senha;
        this.tipoUser = tipoUser;
    }

    public Utilizador(String email, String senha, String tipoUser) {
        this.email = email;
        this.senha = senha;
        this.tipoUser = tipoUser;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getTipoUser() {
        return tipoUser;
    }

    public void setTipoUser(String tipoUser) {
        this.tipoUser = tipoUser;
    }

    @Override
    public String toString() {
        return "Utilizador{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", tipoUser='" + tipoUser + '\'' +
                '}';
    }
}
