package com.aayam.hotel.backend.controller;

import com.aayam.hotel.backend.model.ClubRequest;
import com.aayam.hotel.backend.model.User;
import com.aayam.hotel.backend.repository.ClubRequestRepository;
import com.aayam.hotel.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/club")
@CrossOrigin(originPatterns = "*", allowCredentials = "true") // 🔥 CORS Error Fix
public class ClubRequestController {

    @Autowired
    private ClubRequestRepository clubRequestRepository;

    @Autowired
    private UserRepository userRepository;

    // 🔍 1. FETCH LOGGED-IN USER DETAILS FOR MODAL PREVIEW
    // Yeh endpoint tumhare preview modal me user ka name, email, phone dikhane me madad karega
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserDetailsForModal(@PathVariable String id) {
        Optional<User> userOpt = userRepository.findById(id);

        if (userOpt.isPresent()) {
            return ResponseEntity.ok(userOpt.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: Current logged-in user context not found in database!");
        }
    }

    // 📤 2. SUBMIT NEW CLUB INVITATION REQUEST
    // Yeh user ke preview screen par confirm karte hi request data ko DB me store karega
    @PostMapping("/request")
    public ResponseEntity<?> submitClubRequest(@RequestBody ClubRequest userRequest) {
        try {
            ClubRequest savedLog = clubRequestRepository.save(userRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedLog);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: Failed to save club request structure to database.");
        }
    }
}