package com.app.cinerma.dulceria;

import java.util.List;
import java.util.Locale;

public class Dulceria {
    private String id;
    private String title;
    private List<String> description;
    private String cost;
    private String category; //Falta usarlo

    private String urlImage;

    public Dulceria() {
    }

    public Dulceria(String category, String cost, List<String> description, String id, String title, String urlImage) {
        this.category = category;
        this.cost = cost;
        this.description = description;
        this.id = id;
        this.title = title;
        this.urlImage = urlImage;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getDescription() {
        return description;
    }

    public void setDescription(List<String> description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
}
