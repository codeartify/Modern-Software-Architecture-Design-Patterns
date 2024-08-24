package com.event.admin.real_estate.repository;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "Payments")
@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    private Date paymentDate;
    private Double amount;
    private String paymentMethod;
    private String paymentStatus;

    @ManyToOne
    @JoinColumn(name = "rental_id", nullable = false)
    private Rental rental;

}
