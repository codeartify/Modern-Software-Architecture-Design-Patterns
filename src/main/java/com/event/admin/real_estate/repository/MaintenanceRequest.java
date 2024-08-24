package com.event.admin.real_estate.repository;

 import jakarta.persistence.*;
 import lombok.AllArgsConstructor;
 import lombok.Getter;
 import lombok.RequiredArgsConstructor;
 import lombok.Setter;

 import java.util.Date;

@Entity
@Table(name = "MaintenanceRequests")
@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class MaintenanceRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;

    private Date requestDate;
    private String description;
    private String status;
    private Date resolutionDate;

    // Relationships
    @ManyToOne
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @ManyToOne
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

}
