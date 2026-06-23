package com.aayam.hotel.backend.dto;

public class StaffLoginResponse {
    private boolean success;
    private String message;
    private String username;
    private String role;

    public StaffLoginResponse(boolean success, String message, String username, String role) {
        this.success = success;
        this.message = message;
        this.username = username;
        this.role = role;
    }

    // Getters and Setters
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public String getUsername() { return username; }
    public String getRole() { return role; }
}