package com.event.admin.real_estate.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class MaintenanceReport {
    private int totalProperties;
    private List<PropertyMaintenanceReport> propertyReports;
}
