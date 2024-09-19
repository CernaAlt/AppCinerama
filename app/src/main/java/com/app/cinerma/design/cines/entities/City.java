package com.app.cinerma.design.cines.entities;

import java.util.List;

public class City {
    private int id;
    private String name;
    private List<Integer> idMovies;
    private List<Integer> idCinemas;

    public City(int id, List<Integer> idMovies, String name, List<Integer> idCinemas) {
        this.id = id;
        this.idMovies = idMovies;
        this.name = name;
        this.idCinemas = idCinemas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getIdMovies() {
        return idMovies;
    }

    public void setIdMovies(List<Integer> idMovies) {
        this.idMovies = idMovies;
    }

    public List<Integer> getIdCinemas() {
        return idCinemas;
    }

    public void setIdCinemas(List<Integer> idCinemas) {
        this.idCinemas = idCinemas;
    }
}
