package com.event.admin.real_estate.service;

import com.event.admin.real_estate.repository.MaintenanceRequestRepository;
import com.event.admin.real_estate.repository.PropertyRepository;
import com.event.admin.real_estate.repository.Rental;
import com.event.admin.real_estate.controller.MaintenanceReport;
import com.event.admin.real_estate.controller.MaintenanceRequestDetail;
import com.event.admin.real_estate.controller.PropertyMaintenanceReport;
import com.event.admin.real_estate.controller.TenantMaintenanceReport;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MaintenanceRequestService {
    private MaintenanceRequestRepository maintenanceRequestRepository;

    private PropertyRepository propertyRepository;

    public MaintenanceReport createMaintenanceReport() {
        var properties = propertyRepository.findAll();
        var totalNumberOfProperties = properties.size();

        // For each property, group maintenance requests by tenant and calculate summary statistics
        List<PropertyMaintenanceReport> propertyReports = properties.stream()
                .map(property -> {
                    List<TenantMaintenanceReport> tenantReports = property.getRentals()
                            .stream()
                            .map(Rental::getTenant)
                            .distinct()  // Avoid duplicates if the tenant has multiple rentals in the same property
                            .map(tenant -> {
                                List<MaintenanceRequestDetail> maintenanceDetails = maintenanceRequestRepository.findByPropertyAndTenant(property, tenant).stream()
                                        .map(request -> new MaintenanceRequestDetail(
                                                request.getRequestDate(),
                                                request.getDescription(),
                                                request.getStatus(),
                                                request.getResolutionDate()
                                        )).collect(Collectors.toList());
                                return new TenantMaintenanceReport(tenant.getFirstName() + " " + tenant.getLastName(), maintenanceDetails);
                            })
                            .toList();

                    long totalRequests = maintenanceRequestRepository.countByProperty(property);
                    long completedRequests = maintenanceRequestRepository.countByPropertyAndStatus(property, "Completed");
                    long openRequests = totalRequests - completedRequests;

                    return new PropertyMaintenanceReport(property.getAddress(), tenantReports, totalRequests, completedRequests, openRequests);
                })
                .toList();

        return new MaintenanceReport(totalNumberOfProperties, propertyReports);
    }
}
