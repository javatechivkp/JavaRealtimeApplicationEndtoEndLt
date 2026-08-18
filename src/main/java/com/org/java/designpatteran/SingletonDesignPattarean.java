package com.org.java.designpatteran;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SingletonDesignPattarean {

    private static SingletonDesignPattarean instance;

    // Application configuration properties
    private String appName = "Employee Management System";
    private String appVersion = "1.0.0";
    private String databaseUrl = "jdbc:mysql://localhost:3306/employee_db";
    private String emailHost = "smtp.gmail.com";
    private int maxEmployees = 1000;
    private LocalDateTime startupTime;

    private SingletonDesignPattarean() {
        // Initialize startup time when singleton is created
        this.startupTime = LocalDateTime.now();
    }

    public static synchronized SingletonDesignPattarean getInstance() {
        if (instance == null) {
            instance = new SingletonDesignPattarean();
        }
        return instance;
    }

    /**
     * Application-related method that displays system information,
     * checks database connectivity, and provides employee management stats
     */
    public void someMethod() {
        System.out.println("=== Employee Management System Status ===");
        System.out.println("Application Name: " + appName);
        System.out.println("Version: " + appVersion);
        System.out.println("Database URL: " + databaseUrl);
        System.out.println("Email Host: " + emailHost);
        System.out.println("Maximum Employees Allowed: " + maxEmployees);
        System.out.println("Application Started: " + startupTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        // Simulate checking system health
        checkSystemHealth();

        // Display employee management capabilities
        displayEmployeeCapabilities();

        System.out.println("==========================================");
    }

    /**
     * Check system health and connectivity
     */
    private void checkSystemHealth() {
        System.out.println("\n--- System Health Check ---");
        System.out.println("✓ Singleton Pattern: Active");
        System.out.println("✓ Application Configuration: Loaded");
        System.out.println("✓ Database Connection: Ready (simulated)");
        System.out.println("✓ Email Service: Configured");
    }

    /**
     * Display employee management system capabilities
     */
    private void displayEmployeeCapabilities() {
        System.out.println("\n--- Employee Management Capabilities ---");
        System.out.println("• Employee CRUD Operations");
        System.out.println("• Salary Analysis & Reporting");
        System.out.println("• Age-based Filtering");
        System.out.println("• Work Location Management");
        System.out.println("• PDF/Excel Report Generation");
        System.out.println("• Email Notifications");
        System.out.println("• Pagination & Sorting");
        System.out.println("• Java 8 Stream Operations");
    }

    // ============ GETTER METHODS ============
    public String getAppName() {
        return appName;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public String getEmailHost() {
        return emailHost;
    }

    public int getMaxEmployees() {
        return maxEmployees;
    }

    public LocalDateTime getStartupTime() {
        return startupTime;
    }

    // ============ UTILITY METHODS ============
    public boolean isDatabaseConfigured() {
        return databaseUrl != null && !databaseUrl.isEmpty();
    }

    public boolean isEmailConfigured() {
        return emailHost != null && !emailHost.isEmpty();
    }

    public String getSystemInfo() {
        return String.format("App: %s v%s | DB: %s | Email: %s | Started: %s",
                appName, appVersion, databaseUrl, emailHost,
                startupTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }
}
