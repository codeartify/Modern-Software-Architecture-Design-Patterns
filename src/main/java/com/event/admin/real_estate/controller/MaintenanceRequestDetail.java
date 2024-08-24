package com.event.admin.real_estate.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class MaintenanceRequestDetail {
    private java.util.Date requestDate;
    private String description;
    private String status;
    private java.util.Date resolutionDate;

}
