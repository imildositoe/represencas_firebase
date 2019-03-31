package com.example.root.re_presencas.model;

public class Sala {

    private String id;
    private String designacao;
    private String latitude;
    private String longitude;

    public Sala() {
    }

    public Sala(String id, String designacao, String latitude, String longitude) {
        this.id = id;
        this.designacao = designacao;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Sala(String designacao, String latitude, String longitude) {
        this.designacao = designacao;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Sala{" +
                "id='" + id + '\'' +
                ", designacao='" + designacao + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }
}
