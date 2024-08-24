package com.event.admin.real_estate.controller;


import com.event.admin.real_estate.service.MaintenanceRequestService;
import com.event.admin.real_estate.repository.MaintenanceRequestRepository;
import com.event.admin.real_estate.repository.PropertyRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
@AllArgsConstructor
public class MaintenanceRequestController {

    private MaintenanceRequestRepository maintenanceRequestRepository;

    private PropertyRepository propertyRepository;

    private MaintenanceRequestService maintenanceRequestService;


    // Endpoint to get a grouped maintenance report
    @GetMapping("/maintenance")
    public MaintenanceReport getGroupedMaintenanceReport() {
        return maintenanceRequestService.createMaintenanceReport();
    }


}
