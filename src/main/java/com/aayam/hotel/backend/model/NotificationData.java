package com.aayam.hotel.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "NotificationData")
public class NotificationData {

    @Id
    private String id; // MongoDB auto-generated ObjectId ko String me map karega
    private String title;
    private String message;
    private String type;

    // Default Constructor (Spring Data ke liye zaroori hai)
    public NotificationData() {
    }

    // Parameterized Constructor
    public NotificationData(String title, String message, String type) {
        this.title = title;
        this.message = message;
        this.type = type;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}