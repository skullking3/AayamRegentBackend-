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
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    // ─── GET ALL MEMBERS ───
    @GetMapping("/all")
    public ResponseEntity<java.util.List<User>> getAllMembers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    // ─── USER REGISTRATION ───
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: Email is already registered!");
        }
        user.setRole("MEMBER");
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
    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateMember(@PathVariable String id, @RequestBody User userDetails) {
        return userRepository.findById(id).map(user -> {
            user.setName(userDetails.getName());
            user.setAddress(userDetails.getAddress());
            user.setPhone(userDetails.getPhone());
            // Baki fields bhi update kar do
            return ResponseEntity.ok(userRepository.save(user));
        }).orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteMember(@PathVariable String id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.ok("Member with ID " + id + " deleted successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Member not found!");
        }
    }
}