package com.app.cinerma.design.peliculas.entities;

public class Movie {
    private int id;
    private String title;
    private String imageUrl;
    private String urlImagenDetail;
    private String sinopsis;
    private String genreMovie;
    private int ranting;
    private String primiere;
    private String director;
    private String urlVideo;
    private String durationMovie;

    public Movie(int id, String title, String imageUrl, String urlImagenDetail, String sinopsis, String genreMovie, int ranting, String primiere, String director, String urlVideo, String durationMovie) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.urlImagenDetail = urlImagenDetail;
        this.sinopsis = sinopsis;
        this.genreMovie = genreMovie;
        this.ranting = ranting;
        this.primiere = primiere;
        this.director = director;
        this.urlVideo = urlVideo;
        this.durationMovie = durationMovie;
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

    public int getRanting() {
        return ranting;
    }

    public void setRanting(int ranting) {
        this.ranting = ranting;
    }

    public String getPrimiere() {
        return primiere;
    }

    public void setPrimiere(String primiere) {
        this.primiere = primiere;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getUrlVideo() {
        return urlVideo;
    }

    public void setUrlVideo(String urlVideo) {
        this.urlVideo = urlVideo;
    }

    public String getDurationMovie() {
        return durationMovie;
    }

    public void setDurationMovie(String durationMovie) {
        this.durationMovie = durationMovie;
    }
}