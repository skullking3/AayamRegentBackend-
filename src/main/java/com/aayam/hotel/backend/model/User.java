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

    @Indexed(unique = true) // Isse duplicate accounts prevent honge
    private String email;

    private String password;

    private String phone;

    private String role = "USER"; // Default status USER rahega

    private LocalDateTime createdAt = LocalDateTime.now();
}