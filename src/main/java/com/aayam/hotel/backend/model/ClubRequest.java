package com.aayam.hotel.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "club_requests")
public class ClubRequest {

    @Id
    private String id;
    private String userName;     // Logged-in user ka naam
    private String userEmail;    // Logged-in user ki email id
    private String userPhone;    // Logged-in user ka phone number
    private String selectedTier; // Jo tier unhone select kiya (Silver, Gold, Platinum)
    private String pricingTier;  // Us tier ka pricing text
    private LocalDateTime appliedAt = LocalDateTime.now(); // Kis time request aayi

    // Default Constructor
    public ClubRequest() {}

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public String getUserPhone() { return userPhone; }
    public void setUserPhone(String userPhone) { this.userPhone = userPhone; }

    public String getSelectedTier() { return selectedTier; }
    public void setSelectedTier(String selectedTier) { this.selectedTier = selectedTier; }

    public String getPricingTier() { return pricingTier; }
    public void setPricingTier(String pricingTier) { this.pricingTier = pricingTier; }

    public LocalDateTime getAppliedAt() { return appliedAt; }
    public void setAppliedAt(LocalDateTime appliedAt) { this.appliedAt = appliedAt; }
}