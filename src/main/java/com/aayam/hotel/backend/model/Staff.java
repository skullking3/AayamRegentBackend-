package com.aayam.hotel.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "staff_users")
public class Staff {
    @Id
    private String id;
    private String name;
    private String username;  // 👈 Bilkul sahi, database se matching 'username'
    private String password;
    private String role;
    private String status;

    public Staff() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getUsername() { return username; } // 👈 Getter sahi kiya
    public void setUsername(String username) { this.username = username; } // 👈 Setter sahi kiya

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}