package com.app.cinerma.design.cines.entities;

public class CoordenadasCine {
    private double latitud;
    private double longitud;

    public CoordenadasCine(double latitud, double longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }

    // Getters y setters
    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    @Override
    public String toString() {
        return "Latitud: " + latitud + ", Longitud: " + longitud;
    }
}
