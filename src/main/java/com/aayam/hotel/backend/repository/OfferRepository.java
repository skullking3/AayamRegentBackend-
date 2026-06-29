package com.aayam.hotel.backend.repository;

import com.aayam.hotel.backend.model.Offers;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepository extends MongoRepository<Offers, String> {
}