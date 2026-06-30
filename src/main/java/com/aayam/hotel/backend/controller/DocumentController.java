package com.aayam.hotel.backend.controller;

import com.aayam.hotel.backend.services.DocumentStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private static final Logger logger = LoggerFactory.getLogger(DocumentController.class);

    @Autowired
    private DocumentStorageService documentStorageService;

    /**
     * 📤 Upload PDF to Supabase Storage
     */
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> uploadDocument(@RequestParam("file") MultipartFile file) {
        Map<String, Object> response = new HashMap<>();

        try {
            // Check if file is empty
            if (file == null || file.isEmpty()) {
                response.put("success", false);
                response.put("error", "Please select a valid PDF file");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            // File type validation
            String contentType = file.getContentType();
            if (contentType == null || !contentType.equals("application/pdf")) {
                response.put("success", false);
                response.put("error", "Only PDF files are allowed");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            // File size validation (10MB max)
            long maxFileSize = 10 * 1024 * 1024;
            if (file.getSize() > maxFileSize) {
                response.put("success", false);
                response.put("error", "File size exceeds maximum limit of 10MB");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            logger.info("Uploading file: {} (Size: {} bytes)", file.getOriginalFilename(), file.getSize());

            // Upload to Supabase
            String fileUrl = documentStorageService.uploadPDF(file);

            // Success response
            response.put("success", true);
            response.put("message", "File uploaded successfully!");
            response.put("url", fileUrl);
            response.put("fileName", file.getOriginalFilename());
            response.put("fileSize", file.getSize());

            logger.info("✅ File uploaded successfully: {}", fileUrl);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (IllegalArgumentException e) {
            logger.error("Validation error: {}", e.getMessage());
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

        } catch (Exception e) {
            logger.error("❌ Upload failed: {}", e.getMessage(), e);
            response.put("success", false);
            response.put("error", "Upload failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 🗑️ Delete PDF from Supabase Storage
     */
    @DeleteMapping("/delete")
    public ResponseEntity<Map<String, Object>> deleteDocument(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();

        try {
            String fileUrl = request.get("url");

            if (fileUrl == null || fileUrl.trim().isEmpty()) {
                response.put("success", false);
                response.put("error", "File URL is required for deletion");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            logger.info("🗑️ Deleting file: {}", fileUrl);
            documentStorageService.deleteFile(fileUrl);

            response.put("success", true);
            response.put("message", "File deleted successfully from Supabase storage");

            logger.info("✅ File deleted successfully");
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            logger.error("Invalid delete request: {}", e.getMessage());
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

        } catch (Exception e) {
            logger.error("❌ Delete failed: {}", e.getMessage(), e);
            response.put("success", false);
            response.put("error", "Failed to delete file: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 📋 List all files in bucket
     */
    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> listDocuments() {
        Map<String, Object> response = new HashMap<>();

        try {
            java.util.List<String> files = documentStorageService.listAllFiles();

            response.put("success", true);
            response.put("files", files);
            response.put("count", files.size());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Error listing files: {}", e.getMessage(), e);
            response.put("success", false);
            response.put("error", "Failed to list files: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}