package com.aayam.hotel.backend.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DocumentStorageService {

    private static final Logger logger = LoggerFactory.getLogger(DocumentStorageService.class);

    @Autowired
    @Qualifier("s3Client")
    private S3Client s3Client;

    @Value("${supabase.s3.bucket:agreements}")
    private String bucketName;

    @Value("${supabase.url}")
    private String supabaseUrl;

    public String uploadPDF(MultipartFile file) {
        try {
            String originalFilename = file.getOriginalFilename();
            String fileExtension = ".pdf";
            if (originalFilename != null && originalFilename.contains(".")) {
                fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String uniqueFilename = UUID.randomUUID().toString() + fileExtension;

            logger.info("📤 Uploading file: {} as {}", originalFilename, uniqueFilename);
            logger.info("🪣 Target bucket: {}", bucketName);

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(uniqueFilename)
                    .contentType("application/pdf")
                    .build();

            s3Client.putObject(putObjectRequest,
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            String fileUrl = constructPublicUrl(uniqueFilename);
            logger.info("✅ Upload successful: {}", fileUrl);

            return fileUrl;

        } catch (IOException e) {
            logger.error("❌ IO Error: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to read file: " + e.getMessage());
        } catch (S3Exception e) {
            logger.error("❌ S3 Error: {}", e.awsErrorDetails().errorMessage(), e);
            throw new RuntimeException("Failed to upload file to Supabase: " + e.awsErrorDetails().errorMessage());
        }
    }

    public void deleteFile(String fileUrl) {
        try {
            String filename = extractFilenameFromUrl(fileUrl);

            if (filename == null || filename.trim().isEmpty()) {
                throw new IllegalArgumentException("Could not extract filename from URL: " + fileUrl);
            }

            logger.info("🗑️ Deleting file: {} from bucket: {}", filename, bucketName);

            DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(filename)
                    .build();

            s3Client.deleteObject(deleteRequest);
            logger.info("✅ File deleted successfully: {}", filename);

        } catch (S3Exception e) {
            logger.error("❌ Delete error: {}", e.awsErrorDetails().errorMessage(), e);
            throw new RuntimeException("Failed to delete file: " + e.awsErrorDetails().errorMessage());
        }
    }

    public List<String> listAllFiles() {
        try {
            ListObjectsV2Request listRequest = ListObjectsV2Request.builder()
                    .bucket(bucketName)
                    .build();

            ListObjectsV2Response listResponse = s3Client.listObjectsV2(listRequest);

            return listResponse.contents().stream()
                    .map(s3Object -> {
                        String url = constructPublicUrl(s3Object.key());
                        return String.format("%s (Size: %d bytes)", url, s3Object.size());
                    })
                    .collect(Collectors.toList());

        } catch (S3Exception e) {
            logger.error("❌ List error: {}", e.awsErrorDetails().errorMessage(), e);
            throw new RuntimeException("Failed to list files: " + e.awsErrorDetails().errorMessage());
        }
    }

    private String extractFilenameFromUrl(String fileUrl) {
        try {
            String decodedUrl = URLDecoder.decode(fileUrl, StandardCharsets.UTF_8.toString());
            URI uri = new URI(decodedUrl);
            String path = uri.getPath();

            if (path.contains("/public/")) {
                String[] parts = path.split("/public/");
                if (parts.length > 1) {
                    String bucketAndFile = parts[1];
                    if (bucketAndFile.startsWith(bucketName + "/")) {
                        return bucketAndFile.substring(bucketName.length() + 1);
                    }
                }
            }

            String[] segments = path.split("/");
            String filename = segments[segments.length - 1];

            if (filename.contains("?")) {
                filename = filename.substring(0, filename.indexOf("?"));
            }

            return filename;

        } catch (Exception e) {
            logger.error("Error extracting filename from URL: {}", fileUrl, e);
            if (fileUrl.contains("/")) {
                String filename = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
                if (filename.contains("?")) {
                    filename = filename.substring(0, filename.indexOf("?"));
                }
                return filename;
            }
            return fileUrl;
        }
    }

    private String constructPublicUrl(String filename) {
        return String.format("%s/storage/v1/object/public/%s/%s",
                supabaseUrl, bucketName, filename);
    }
}