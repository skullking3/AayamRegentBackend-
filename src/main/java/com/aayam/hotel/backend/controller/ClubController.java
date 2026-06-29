package com.aayam.hotel.backend.controller;

import com.aayam.hotel.backend.model.ClubTier;
import com.aayam.hotel.backend.repository.ClubTierRepository;
import jakarta.annotation.PostConstruct;
// Note: Agar purana spring boot (v2) hai toh `import javax.annotation.PostConstruct;` use karna

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(originPatterns = "*", allowCredentials = "true") // 🔥 CORS Fix Applied
public class ClubController {

    @Autowired
    private ClubTierRepository clubTierRepository;

    // 🚀 BONUS FIX: Jab App start ho, toh khali DB me 3 Fixed Cards daal de!
    @PostConstruct
    public void initFixedTiers() {
        if (clubTierRepository.count() == 0) {
            clubTierRepository.save(new ClubTier("Elite Silver", "₹25,000 / yr", Arrays.asList("10% Additional Discount on Suites", "Complimentary Welcome Drink & Fruit Platter", "Early Check-in / Late Check-out")));
            clubTierRepository.save(new ClubTier("Royal Gold", "₹55,000 / yr", Arrays.asList("20% Additional Discount on All Bookings", "Free Room Upgrade to Next Higher Category", "Complimentary Buffet Breakfast for 2 Guests")));
            clubTierRepository.save(new ClubTier("Imperial Platinum", "₹1,200,000 / yr", Arrays.asList("30% Fixed Discount on Presidential Suites", "Guaranteed Room Upgrades & Free Stay Vouchers", "Private Yacht & Helicopter Charter Privileges")));
            System.out.println("✨ Default Aayam Club Tiers auto-generated in Database!");
        }
    }

    // 🌐 1. GET ALL TIERS (Frontend main page ke liye)
    @GetMapping("/public/club")
    public ResponseEntity<List<ClubTier>> getAllTiers() {
        List<ClubTier> tiers = clubTierRepository.findAll();
        return ResponseEntity.ok(tiers);
    }

    // 🌐 2. GET SINGLE TIER (Admin panel me form load karne ke liye)
    @GetMapping("/public/club/{name}")
    public ResponseEntity<?> getTierByName(@PathVariable String name) {
        Optional<ClubTier> tier = clubTierRepository.findByName(name);
        if (tier.isPresent()) {
            return ResponseEntity.ok(tier.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tier not found");
        }
    }

    // 🔐 3. UPDATE TIER (Admin panel se changes save karne ke liye)
    @PutMapping("/admin/club/{name}")
    public ResponseEntity<?> updateTier(@PathVariable String name, @RequestBody ClubTier updatedData) {
        Optional<ClubTier> existingTierOpt = clubTierRepository.findByName(name);

        if (existingTierOpt.isPresent()) {
            ClubTier existingTier = existingTierOpt.get();
            // Sirf Price aur Features update karenge, ID aur Name fix rahenge
            existingTier.setPrice(updatedData.getPrice());
            existingTier.setFeatures(updatedData.getFeatures());

            ClubTier savedTier = clubTierRepository.save(existingTier);
            return ResponseEntity.ok(savedTier);
        } else {
            // Fallback: In case kisi ne DB delete maar di, toh naya bana dega update karte waqt
            updatedData.setName(name);
            ClubTier savedTier = clubTierRepository.save(updatedData);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedTier);
        }
    }
}