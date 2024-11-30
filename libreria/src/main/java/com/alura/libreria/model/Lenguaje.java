package com.alura.libreria.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public class Lenguaje {
    private String español;
    private String ingles;
    private String frances;
    private String portugues;

    public String getEspañol() {
        return español;
    }

    public void setEspañol(String español) {
        this.español = español;
    }

    public String getIngles() {
        return ingles;
    }

    public void setIngles(String ingles) {
        this.ingles = ingles;
    }

    public String getFrances() {
        return frances;
    }

    public void setFrances(String frances) {
        this.frances = frances;
    }

    public String getPortugues() {
        return portugues;
    }

    public void setPortugues(String portugues) {
        this.portugues = portugues;
    }


}
