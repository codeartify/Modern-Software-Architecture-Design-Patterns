package com.event.admin.ticket.model;

public class Ticket {
    private Long id;
    private double price;
    private String type;
    private String qrCode;
    private Event event;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQRCode() {
        return qrCode;
    }

    public void setQRCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
