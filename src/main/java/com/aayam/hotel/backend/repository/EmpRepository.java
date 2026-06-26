package com.aayam.hotel.backend.repository;

import com.aayam.hotel.backend.model.Emp; // Yahan Staff ki jagah Emp import karo
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmpRepository extends MongoRepository<Emp, String> {
}