package com.app.cinerma.design.peliculas.entities;

public class Pelicula {
    private String title;
    private  String description;
    private String genero;
    private int year;

    //construtior Basio
    public Pelicula() {
    }

    public Pelicula(String title, String description, String genero, int year) {
        this.title = title;
        this.description = description;
        this.genero = genero;
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
