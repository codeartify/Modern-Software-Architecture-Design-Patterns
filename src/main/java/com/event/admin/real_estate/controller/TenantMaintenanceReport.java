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
public class TenantMaintenanceReport {
    private String tenantName;
    private List<MaintenanceRequestDetail> maintenanceRequests;
}
