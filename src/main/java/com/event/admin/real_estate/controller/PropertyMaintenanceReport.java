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
public class PropertyMaintenanceReport {
    private String propertyAddress;
    private List<TenantMaintenanceReport> tenants;
    private long totalRequests;
    private long completedRequests;
    private long openRequests;
}
