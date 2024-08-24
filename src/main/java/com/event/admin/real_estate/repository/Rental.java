package com.event.admin.real_estate.repository;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Rentals")
@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rentalId;

    private String unitNumber;
    private Date leaseStartDate;
    private Date leaseEndDate;
    private Double monthlyRent;
    private Double depositAmount;

    // Relationships
    @ManyToOne
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @ManyToOne
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    @OneToMany(mappedBy = "rental")
    private List<Payment> payments;
}
