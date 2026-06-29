package com.aayam.hotel.backend.controller;

import com.aayam.hotel.backend.model.NotificationData;
import com.aayam.hotel.backend.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/public")
// 🔥 CRITICAL CORS FIX: Wildcard * hata kar explicit constraints diye hain taaki global Security credentials se na takraye
@CrossOrigin(originPatterns = "*", allowedHeaders = "*", allowCredentials = "true", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class PublicContentController {

    @Autowired
    private NotificationRepository notificationRepository;

    // 1. GET: Fetch all notifications safely
    @GetMapping("/notifications")
    public ResponseEntity<List<NotificationData>> getAllNotifications() {
        List<NotificationData> list = notificationRepository.findAll();
        return ResponseEntity.ok(list);
    }

    // 2. POST: Auto-generated safe Atlas integration
    @PostMapping(value = "/notifications", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> createNotification(@RequestBody NotificationData request) {
        request.setId(null); // Force database automatic ObjectId schema definition
        NotificationData savedNotif = notificationRepository.save(request);
        return ResponseEntity.ok(Map.of(
                "message", "Notification pushed to database successfully!",
                "id", savedNotif.getId()
        ));
    }

    // 3. PUT: Update metadata by ID path variable
    @PutMapping(value = "/notifications/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updateNotification(@PathVariable String id, @RequestBody NotificationData updatedData) {
        Optional<NotificationData> existingNotif = notificationRepository.findById(id);

        if (existingNotif.isPresent()) {
            NotificationData notif = existingNotif.get();
            notif.setTitle(updatedData.getTitle());
            notif.setMessage(updatedData.getMessage());
            notif.setType(updatedData.getType());

            notificationRepository.save(notif);
            return ResponseEntity.ok(Map.of("message", "Notification updated in Atlas successfully!"));
        } else {
            return ResponseEntity.status(404).body(Map.of("error", "Notification not found with ID: " + id));
        }
    }

    // 4. DELETE: Clean up resource from collection
    @DeleteMapping("/notifications/{id}")
    public ResponseEntity<?> deleteNotification(@PathVariable String id) {
        if (notificationRepository.existsById(id)) {
            notificationRepository.deleteById(id);
            return ResponseEntity.ok(Map.of("message", "Notification permanently deleted from Atlas!"));
        } else {
            return ResponseEntity.status(404).body(Map.of("error", "Notification not found with ID: " + id));
        }
    }
}