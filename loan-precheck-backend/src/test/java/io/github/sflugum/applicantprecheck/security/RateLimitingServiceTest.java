package io.github.sflugum.applicantprecheck.security;

import io.github.bucket4j.Bucket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the RateLimitingService.
 * Validates the core token-bucket logic used to track and limit IP addresses.
 */
public class RateLimitingServiceTest {

    private RateLimitingService rateLimitingService;

    @BeforeEach
    void setUp() {
        rateLimitingService = new RateLimitingService();
    }

    @Test
    @DisplayName("Should successfully consume 10 tokens before rejecting further requests")
    void shouldAllow10RequestsThenReject() {
        // Arrange: Retrieve the token bucket for a specific IP
        String ip = "192.0.2.1";
        Bucket bucket = rateLimitingService.resolveBucket(ip);

        // Act & Assert: The first 10 requests should successfully consume a token
        for (int i = 0; i < 10; i++) {
            assertTrue(bucket.tryConsume(1), "Request " + (i + 1) + " should be allowed");
        }

        assertFalse(bucket.tryConsume(1), "11th request should be rejected");
    }

    @Test
    @DisplayName("Should track rate limits independently for different IP addresses")
    void shouldIsolateBucketsByIpAddress() {
        // Arrange: Create buckets for two entirely different IP addresses
        String ip1 = "198.51.100.1";
        String ip2 = "203.0.113.1";
        Bucket bucket1 = rateLimitingService.resolveBucket(ip1);
        Bucket bucket2 = rateLimitingService.resolveBucket(ip2);

        // Act: Exhaust the token bucket for IP1
        for (int i = 0; i < 10; i++) {
            bucket1.tryConsume(1);
        }

        // Assert: IP1 is blocked, but IP2 should remain unaffected
        assertFalse(bucket1.tryConsume(1), "IP1 bucket should be exhausted");
        assertTrue(bucket2.tryConsume(1), "IP2 bucket should still have tokens available");
    }
}
