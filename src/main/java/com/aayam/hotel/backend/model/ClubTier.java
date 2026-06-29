package com.aayam.hotel.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "club_tiers")
public class ClubTier {

    @Id
    private String id;
    private String name; // "Elite Silver", "Royal Gold", "Imperial Platinum"
    private String price;
    private List<String> features;

    // Default Constructor
    public ClubTier() {}

    // Parameterized Constructor (Helper for init)
    public ClubTier(String name, String price, List<String> features) {
        this.name = name;
        this.price = price;
        this.features = features;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }

    public List<String> getFeatures() { return features; }
    public void setFeatures(List<String> features) { this.features = features; }
}