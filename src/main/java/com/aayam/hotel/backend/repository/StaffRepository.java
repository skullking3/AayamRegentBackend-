package com.aayam.hotel.backend.repository;

import com.aayam.hotel.backend.model.Staff;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface StaffRepository extends MongoRepository<Staff, String> {
    // 👈 Dobara isko bas 'findByUsername' kar do taaki ye direct matching kare
    Optional<Staff> findByUsername(String username);
}