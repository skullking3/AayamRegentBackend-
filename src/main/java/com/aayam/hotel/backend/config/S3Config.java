package com.aayam.hotel.backend.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import jakarta.annotation.PostConstruct;
import java.net.URI;

@Configuration
public class S3Config {

    private static final Logger logger = LoggerFactory.getLogger(S3Config.class);

    @Value("${supabase.s3.endpoint}")
    private String endpoint;

    @Value("${supabase.s3.access-key}")
    private String accessKey;

    @Value("${supabase.s3.secret-key}")
    private String secretKey;

    @Value("${supabase.s3.region}")
    private String region;

    @Value("${supabase.s3.bucket:agreements}")
    private String bucketName;

    @Bean(name = "s3Client")
    public S3Client s3Client() {
        logger.info("🔧 Initializing Supabase S3 Client...");
        logger.info("📍 Endpoint: {}", endpoint);
        logger.info("🌍 Region: {}", region);
        logger.info("🪣 Bucket: {}", bucketName);

        return S3Client.builder()
                .endpointOverride(URI.create(endpoint))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKey, secretKey)))
                .region(Region.of(region))
                .forcePathStyle(true)
                .build();
    }

    @PostConstruct
    public void verifyBucketAccess() {
        try {
            S3Client client = S3Client.builder()
                    .endpointOverride(URI.create(endpoint))
                    .credentialsProvider(StaticCredentialsProvider.create(
                            AwsBasicCredentials.create(accessKey, secretKey)))
                    .region(Region.of(region))
                    .forcePathStyle(true)
                    .build();

            logger.info("🔍 Checking bucket '{}' accessibility...", bucketName);

            try {
                client.headBucket(builder -> builder.bucket(bucketName));
                logger.info("✅ Bucket '{}' is accessible!", bucketName);
            } catch (Exception e) {
                logger.error("❌ Cannot access bucket '{}': {}", bucketName, e.getMessage());
            }

            client.close();
        } catch (Exception e) {
            logger.error("❌ Failed to verify bucket: {}", e.getMessage());
        }
    }
}