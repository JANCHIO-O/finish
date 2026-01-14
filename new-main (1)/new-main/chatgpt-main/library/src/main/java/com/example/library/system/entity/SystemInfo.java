package com.example.library.system.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "system_info")
public class SystemInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String systemName;

    @Column(nullable = false, length = 800)
    private String overview;

    @Column(nullable = false, length = 800)
    private String coreFunctions;

    @Column(nullable = false, length = 800)
    private String serviceScope;

    @Column(nullable = false, length = 800)
    private String securityGuarantee;

    @Column(nullable = false, length = 800)
    private String maintenanceSupport;

    public SystemInfo() {
    }

    public SystemInfo(String systemName,
                      String overview,
                      String coreFunctions,
                      String serviceScope,
                      String securityGuarantee,
                      String maintenanceSupport) {
        this.systemName = systemName;
        this.overview = overview;
        this.coreFunctions = coreFunctions;
        this.serviceScope = serviceScope;
        this.securityGuarantee = securityGuarantee;
        this.maintenanceSupport = maintenanceSupport;
    }

    public Long getId() {
        return id;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getCoreFunctions() {
        return coreFunctions;
    }

    public void setCoreFunctions(String coreFunctions) {
        this.coreFunctions = coreFunctions;
    }

    public String getServiceScope() {
        return serviceScope;
    }

    public void setServiceScope(String serviceScope) {
        this.serviceScope = serviceScope;
    }

    public String getSecurityGuarantee() {
        return securityGuarantee;
    }

    public void setSecurityGuarantee(String securityGuarantee) {
        this.securityGuarantee = securityGuarantee;
    }

    public String getMaintenanceSupport() {
        return maintenanceSupport;
    }

    public void setMaintenanceSupport(String maintenanceSupport) {
        this.maintenanceSupport = maintenanceSupport;
    }
}
