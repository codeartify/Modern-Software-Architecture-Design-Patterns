package com.event.admin.ticket.model;

public class DiscountCode {
    private Long id;
    private String code;
    private double discountPercentage;
    private String applicableTicketType;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public String getApplicableTicketType() {
        return applicableTicketType;
    }

    public void setApplicableTicketType(String applicableTicketType) {
        this.applicableTicketType = applicableTicketType;
    }
}
