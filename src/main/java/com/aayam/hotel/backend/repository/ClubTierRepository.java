package com.aayam.hotel.backend.repository;

import com.aayam.hotel.backend.model.ClubTier;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClubTierRepository extends MongoRepository<ClubTier, String> {

    Optional<ClubTier> findByName(String name);
}