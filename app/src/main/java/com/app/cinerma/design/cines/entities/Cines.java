package com.app.cinerma.design.cines.entities;

import java.util.List;
import java.util.SplittableRandom;

public class Cines {
    private String name;
    private List<String> sala;
    private List<String> Horarios;
    private String id;

    public Cines() {
    }

    public Cines(List<String> horarios, String id, String name, List<String> sala) {
        Horarios = horarios;
        this.id = id;
        this.name = name;
        this.sala = sala;
    }

    public List<String> getHorarios() {
        return Horarios;
    }

    public void setHorarios(List<String> horarios) {
        Horarios = horarios;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getSala() {
        return sala;
    }

    public void setSala(List<String> sala) {
        this.sala = sala;
    }
}
