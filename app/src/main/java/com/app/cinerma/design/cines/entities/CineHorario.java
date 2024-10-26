package com.app.cinerma.design.cines.entities;

import java.util.List;

public class CineHorario {
    private String cineId;
    private String name;
    private List<String> horarios;

    public CineHorario() {
    }

    public CineHorario(String cineId, List<String> horarios, String name) {
        this.cineId = cineId;
        this.horarios = horarios;
        this.name = name;
    }

    public String getCineId() { return cineId; }

    public void setCineId(String cineId) {
        this.cineId = cineId;
    }

    public List<String> getHorarios() { return horarios; }

    public void setHorarios(List<String> horarios) {
        this.horarios = horarios;
    }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }
}
