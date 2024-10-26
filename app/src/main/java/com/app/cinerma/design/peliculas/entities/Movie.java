package com.app.cinerma.design.peliculas.entities;
import com.app.cinerma.design.cines.entities.CineHorario;

import java.util.List;

public class Movie {
    private String id;
    private String title;
    private String url;
    private List<String> idioma;
    private String sinopsis;
    private String genre;
    private List<String> status;
    private String director;
    private String durationMovie;
    private String age;
    private String urlTrailer;
    private List<CineHorario> cineHorarios;
    private List<String> disponible;

    public Movie() {
    }

    public List<CineHorario> getCineHorarios() {
        return cineHorarios;
    }

    public void setCineHorarios(List<CineHorario> cineHorarios) {
        this.cineHorarios = cineHorarios;
    }


    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public List<String> getDisponible() {
        return disponible;
    }

    public void setDisponible(List<String> disponible) {
        this.disponible = disponible;
    }

    public String getDurationMovie() {
        return durationMovie;
    }

    public void setDurationMovie(String durationMovie) {
        this.durationMovie = durationMovie;
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

    public List<String> getIdioma() {
        return idioma;
    }

    public void setIdioma(List<String> idioma) {
        this.idioma = idioma;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public List<String> getStatus() {
        return status;
    }

    public void setStatus(List<String> status) {
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