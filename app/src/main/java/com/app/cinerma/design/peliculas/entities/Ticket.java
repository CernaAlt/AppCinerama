package com.app.cinerma.design.peliculas.entities;

import java.util.UUID;

public class Ticket {
    private String id;
    private String type;
    private double price;

    public Ticket() {
    }

    public Ticket(String type, double price) {
        this.type = type;
        this.price = price;
        this.id = UUID.randomUUID().toString(); //genermaos el id aumatico
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) { this.type =  type; }

    public double getPrice() {
        return price;
    }
    public  void setPrice(double price) { this.price = price; }
}
