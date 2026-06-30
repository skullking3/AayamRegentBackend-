package com.aayam.hotel.backend.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Members_detailed")
public class User {

    @Id
    private String id;

    private String name;

    @Indexed(unique = true)
    private String email;

    private String password;

    private String phone; // Mobile No

    // Nayi fields add ki hain:
    private String dob;
    private String address;
    private String pinCode; // Number store karega
    private String identityNo; // PAN/Aadhar

    private String role = "MEMBER";
    private LocalDateTime createdAt = LocalDateTime.now();
    private String agreementUrl;
}