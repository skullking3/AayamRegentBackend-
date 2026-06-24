package com.aayam.hotel.backend.controller;

import com.aayam.hotel.backend.model.Booking;
import com.aayam.hotel.backend.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingRepository repository;

    @PostMapping("/create")
    public ResponseEntity<String> createBooking(@RequestBody Booking booking) {
        repository.save(booking);
        return ResponseEntity.ok("Booking saved to MongoDB Atlas successfully!");
    }
}