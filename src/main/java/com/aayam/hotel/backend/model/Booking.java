package com.aayam.hotel.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Document(collection = "Booking_bar")
@Data
public class Booking {
    @Id
    private String id; // MongoDB automatically handle karega
    private String destination;
    private String checkIn;
    private String checkOut;
    private String guests;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private String customerAddress;
    private String isMemberOrOwner;
}