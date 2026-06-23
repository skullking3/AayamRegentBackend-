package com.aayam.hotel.backend.repository;

import com.aayam.hotel.backend.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email); // Ye method AuthController ke liye zaroori hai
}