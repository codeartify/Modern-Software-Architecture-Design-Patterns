package com.event.admin.real_estate.repository;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "Tenants")
@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class Tenant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tenantId;

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;

    @OneToMany(mappedBy = "tenant")
    private List<Rental> rentals;

    @OneToMany(mappedBy = "tenant")
    private List<MaintenanceRequest> maintenanceRequests;

}
