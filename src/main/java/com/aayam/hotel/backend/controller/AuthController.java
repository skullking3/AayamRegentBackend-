package com.aayam.hotel.backend.controller;

import com.aayam.hotel.backend.model.User;
import com.aayam.hotel.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173") // Tumhare Vite React frontend ke liye access permit
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    // ─── USER REGISTRATION ───
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: Email is already registered!");
        }
        user.setRole("USER");
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }

    // ─── USER LOGIN ───
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User loginDetails) {
        Optional<User> userOpt = userRepository.findByEmail(loginDetails.getEmail());

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getPassword().equals(loginDetails.getPassword())) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Error: Incorrect Password!");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Error: User not found!");
    }
}