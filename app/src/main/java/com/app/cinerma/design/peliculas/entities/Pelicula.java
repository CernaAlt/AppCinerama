package com.app.cinerma.design.peliculas.entities;

import java.util.List;
import java.util.Map;

public class Pelicula {
    private String id;
    private String age;
    // Lista de cines donde se proyecta la película
    private CineHorarios cinesHorarios; // Cambio a lista de CineHorarios
    private String director;
    private String durationMovie;
    private String sinopsis;
    private String status;
    private String title;
    private String url;
    private String urlTrailer;
    private String genre;
    private List<String> idioma;
    private List<String> disponible;

    public Pelicula() {}


    public Pelicula(String age, CineHorarios cinesHorarios, String director, String durationMovie, List<String> disponible, String genre, String id, List<String> idioma, String sinopsis, String status, String title, String url, String urlTrailer) {
        this.age = age;
        this.cinesHorarios = cinesHorarios;
        this.director = director;
        this.durationMovie = durationMovie;
        this.disponible = disponible;
        this.genre = genre;
        this.id = id;
        this.idioma = idioma;
        this.sinopsis = sinopsis;
        this.status = status;
        this.title = title;
        this.url = url;
        this.urlTrailer = urlTrailer;
    }

    public List<String> getDisponible() {
        return disponible;
    }

    public void setDisponible(List<String> disponible) {
        this.disponible = disponible;
    }

    public List<String> getIdioma() {
        return idioma;
    }

    public void setIdioma(List<String> idioma) {
        this.idioma = idioma;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public CineHorarios getCinesHorarios() {
        return cinesHorarios;
    }

    public void setCinesHorarios(CineHorarios cinesHorarios) {
        this.cinesHorarios = cinesHorarios;
    }


    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getDurationMovie() {
        return durationMovie;
    }

    public void setDurationMovie(String durationMovie) {
        this.durationMovie = durationMovie;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlTrailer() {
        return urlTrailer;
    }

    public void setUrlTrailer(String urlTrailer) {
        this.urlTrailer = urlTrailer;
    }

}
