package com.aayam.hotel.backend.repository;

import com.aayam.hotel.backend.model.NotificationData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends MongoRepository<NotificationData, String> {
}