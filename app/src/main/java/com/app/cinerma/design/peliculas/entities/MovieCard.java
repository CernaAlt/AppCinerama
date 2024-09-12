package com.app.cinerma.design.peliculas.entities;

public class MovieCard {
    private String imageUrl;

    // Constructor
    public MovieCard(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    // Getter
    public String getImageUrl() {
        return imageUrl;
    }

    // Setter (si necesitas)
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
