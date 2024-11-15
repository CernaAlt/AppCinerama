package com.app.cinerma.design.peliculas.entities;

import java.util.UUID;

public class PaymentInfo {
    private String id;
    private String nombre;
    private String email;
    private String metodoPago;
    private String opcionSecundaria; // Para la opción seleccionada en el spinner
    private double total;

    public PaymentInfo() {
    }

    // Constructor
    public PaymentInfo(String nombre, String email, String metodoPago, String opcionSecundaria, double total) {
        this.nombre = nombre;
        this.email = email;
        this.metodoPago = metodoPago;
        this.opcionSecundaria = opcionSecundaria;
        this.total = total;
        this.id= UUID.randomUUID().toString();
    }

    // Getters y Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }

    public String getOpcionSecundaria() { return opcionSecundaria; }
    public void setOpcionSecundaria(String opcionSecundaria) { this.opcionSecundaria = opcionSecundaria; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
}
