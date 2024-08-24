package com.event.admin.real_estate.repository;


 import jakarta.persistence.*;
 import lombok.AllArgsConstructor;
 import lombok.Getter;
 import lombok.RequiredArgsConstructor;
 import lombok.Setter;

 import java.util.List;

@Entity
@Table(name = "Properties")
@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long propertyId;

    private String address;
    private String city;
    private String state;
    private String postalCode;
    private String propertyType;
    private int numUnits;
    private String ownerName;
    private Double purchasePrice;

    // Relationships
    @OneToMany(mappedBy = "property")
    private List<Rental> rentals;

    @OneToMany(mappedBy = "property")
    private List<MaintenanceRequest> maintenanceRequests;


}
