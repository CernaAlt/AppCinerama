package com.app.cinerma.design.peliculas.entities;

import java.util.List;

public class Movie {
    private int id;
    private String title;
    private String imageUrl;
    private String urlImagenDetail;
    private String sinopsis;
    private String genreMovie;
    private List<String> status;
    private String director;
    private String primiere;

    private String durationMovie;
    private String age;
    private List<String> formats;
    private String trailerUrl;
    private List<String> cinemas;
    private List<String> languages;

    public Movie(int id, String title, String urlImagenDetail, String imageUrl, String sinopsis, String genreMovie, List<String> status, String director, String primiere, String durationMovie, String age, List<String> formats, String trailerUrl, List<String> cinemas, List<String> languages) {
        this.id = id;
        this.title = title;
        this.urlImagenDetail = urlImagenDetail;
        this.imageUrl = imageUrl;
        this.sinopsis = sinopsis;
        this.genreMovie = genreMovie;
        this.status = status;
        this.director = director;
        this.primiere = primiere;
        this.durationMovie = durationMovie;
        this.age = age;
        this.formats = formats;
        this.trailerUrl = trailerUrl;
        this.cinemas = cinemas;
        this.languages = languages;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUrlImagenDetail() {
        return urlImagenDetail;
    }

    public void setUrlImagenDetail(String urlImagenDetail) {
        this.urlImagenDetail = urlImagenDetail;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getGenreMovie() {
        return genreMovie;
    }

    public void setGenreMovie(String genreMovie) {
        this.genreMovie = genreMovie;
    }

    public List<String> getStatus() {
        return status;
    }

    public void setStatus(List<String> status) {
        this.status = status;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getPrimiere() {
        return primiere;
    }

    public void setPrimiere(String primiere) {
        this.primiere = primiere;
    }

    public String getDurationMovie() {
        return durationMovie;
    }

    public void setDurationMovie(String durationMovie) {
        this.durationMovie = durationMovie;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public List<String> getFormats() {
        return formats;
    }

    public void setFormats(List<String> formats) {
        this.formats = formats;
    }

    public List<String> getCinemas() {
        return cinemas;
    }

    public void setCinemas(List<String> cinemas) {
        this.cinemas = cinemas;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public void setTrailerUrl(String trailerUrl) {
        this.trailerUrl = trailerUrl;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }
}