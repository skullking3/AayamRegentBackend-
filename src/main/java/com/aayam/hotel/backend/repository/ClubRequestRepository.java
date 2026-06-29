package com.aayam.hotel.backend.repository;

import com.aayam.hotel.backend.model.ClubRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubRequestRepository extends MongoRepository<ClubRequest, String> {
    // Basic CRUD operations (like save, findAll) auto-configured hain
}