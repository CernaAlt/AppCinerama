package com.app.cinerma.design.peliculas.entities;

import java.util.List;
import java.util.UUID;

public class Reserva {

    private  String id;
    private String city;
    private String movie;
    private String cinema;
    private String hour;
    private String date;
    private String seat;
    private List<Ticket> tickets;
    private List<PaymentInfo> payments;

    public Reserva() {} // Constructor sin argumentos

    /*public Reserva(String city, String movie, String cinema, String hour, String date, String seat, List<Ticket> tickets, List<PaymentInfo> payments) {
        this.city = city;
        this.movie = movie;
        this.cinema = cinema;
        this.hour = hour;
        this.date = date;
        this.seat = seat;
        this.tickets = tickets;
        this.payments = payments;
    }*/

    // Constructor sin el parámetro id
    // Constructor que incluye el campo `id`
    public Reserva(String id, String city, String movie, String cinema, String hour,
                   String date, String seat, List<Ticket> tickets, List<PaymentInfo> payments) {
        this.id = id;
        this.city = city;
        this.movie = movie;
        this.cinema = cinema;
        this.hour = hour;
        this.date = date;
        this.seat = seat;
        this.tickets = tickets;
        this.payments = payments;
    }

    // Getters y Setters para cada campo
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCinema() {
        return cinema;
    }

    public void setCinema(String cinema) {
        this.cinema = cinema;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public List<PaymentInfo> getPayments() {
        return payments;
    }

    public void setPayments(List<PaymentInfo> payments) {
        this.payments = payments;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
}

