package com.aayam.hotel.backend.repository;

import com.aayam.hotel.backend.model.Booking    ;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookingRepository extends MongoRepository<Booking, String> {
}