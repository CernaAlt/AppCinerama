package com.app.cinerma.design.cines.entities;

import java.util.ArrayList;
import java.util.List;

public class Cinema {
    private String name;
    private String address;
    private String city;
    private List<String> format;
    private List<String> Horas;
    private String urlImage;
    private CoordenadasCine location;

    public Cinema() {
        this.format = new ArrayList<>();
        this.Horas = new ArrayList<>();
    }


    public Cinema(String name, String address, String city, List<String> format, List<String> horas, String urlImage, CoordenadasCine location) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.format = format;
        Horas = horas;
        this.urlImage = urlImage;
        this.location = location;
    }

    public List<String> getHoras() {
        return Horas;
    }

    public void setHoras(List<String> horas) {
        Horas = horas;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<String> getFormat() {
        return format;
    }

    public void setFormat(List<String> format) {
        this.format = format;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public CoordenadasCine getLocation() {
        return location;
    }

    public void setLocation(CoordenadasCine location) {
        this.location = location;
    }
}
