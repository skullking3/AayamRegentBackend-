package com.aayam.hotel.backend.controller;

import com.aayam.hotel.backend.model.Booking;
import com.aayam.hotel.backend.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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

    @GetMapping("/all")
    public List<Booking> getAllBookings() {
        return repository.findAll();
    }

    @DeleteMapping("/delete/{id}")
    public String deleteBooking(@PathVariable String id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return "Booking with ID " + id + " deleted successfully!";
        } else {
            return "Booking not found!";
        }
    }
}