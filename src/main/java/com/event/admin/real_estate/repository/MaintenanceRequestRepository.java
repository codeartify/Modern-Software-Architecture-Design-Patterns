package com.event.admin.real_estate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaintenanceRequestRepository extends JpaRepository<MaintenanceRequest, Long> {
    List<MaintenanceRequest> findByPropertyAndTenant(Property property, Tenant tenant);

    long countByProperty(Property property);

    long countByPropertyAndStatus(Property property, String status);
}
