package com.aayam.hotel.backend.controller;

import com.aayam.hotel.backend.model.Offers;
import com.aayam.hotel.backend.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class OfferController {

    @Autowired
    private OfferRepository offerRepository;

    // 🌐 1. GET ALL OFFERS
    @GetMapping("/public/offers")
    public ResponseEntity<List<Offers>> getAllOffers() {
        List<Offers> offers = offerRepository.findAll();
        return ResponseEntity.ok(offers);
    }

    // 🔐 2. CREATE OFFER (Admin)
    @PostMapping("/public/create")
    public ResponseEntity<Offers> createOffer(@RequestBody Offers offer) {
        Offers savedOffer = offerRepository.save(offer);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOffer);
    }

    // ✏️ 3. UPDATE OFFER
    @PutMapping("/public/update/{id}")
    public ResponseEntity<?> updateOffer(@PathVariable String id, @RequestBody Offers updatedOffer) {
        Optional<Offers> existingOfferOptional = offerRepository.findById(id);

        if (existingOfferOptional.isPresent()) {
            Offers offerToUpdate = existingOfferOptional.get();
            // Fields update ho rahe hain aapke model ke mutabik
            offerToUpdate.setTag(updatedOffer.getTag());
            offerToUpdate.setTitle(updatedOffer.getTitle());
            offerToUpdate.setDesc(updatedOffer.getDesc());
            offerToUpdate.setBtnText(updatedOffer.getBtnText());

            Offers savedOffer = offerRepository.save(offerToUpdate);
            return ResponseEntity.ok(savedOffer);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Offer not found with id: " + id);
        }
    }

    @DeleteMapping("/offers/delete/{id}")
    public ResponseEntity<?> deleteOffer(@PathVariable String id) {
        if (offerRepository.existsById(id)) {
            offerRepository.deleteById(id);
            return ResponseEntity.ok().body("Offer deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Offer not found with id: " + id);
        }
    }
}