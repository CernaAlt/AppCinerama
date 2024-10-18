package com.app.cinerma.design.peliculas.entities;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;

import java.util.List;

public class CineHorarios {
    private String idCine; // ID del cine
    private String idioma; // Idioma de la película en ese cine
    private String sala; // Sala donde se proyecta la película
    private List<String> horarios; // Horarios de proyección

    // Constructor vacío para Firestore
    public CineHorarios() {}

    // Constructor con parámetros
    public CineHorarios(String idCine, String idioma, String sala, List<String> horarios) {
        this.idCine = idCine;
        this.idioma = idioma;
        this.sala = sala;
        this.horarios = horarios;
    }

    public String getIdCine() {
        return idCine;
    }
    public void setIdCine(String idCine) {
        this.idCine = idCine;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public List<String> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<String> horarios) {
        this.horarios = horarios;
    }
}
