package io.github.sflugum.applicantprecheck.security;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/*
Manages the generation and storage of rate-limiting buckets.
Utilizes Bucket4j to implement a token-bucket algorithm for traffic shaping.
 */
@Service
public class RateLimitingService {

    // A thread-safe cache mapping IP addresses to their specific rate limit buckets.
    // This isolates buckets by IP so a single bad actor cannot drain a global bucket
    // and cause a denial of service for legitimate users.
    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    /*
    Retrieves the existing bucket for an IP, or creates a new one if it's their first request.
    computeIfAbsent is used for thread safety to prevent race conditions when creating new buckets.
     */
    public Bucket resolveBucket(String ipAddress) {
        return cache.computeIfAbsent(ipAddress, this::newBucket);
    }

    /*
    Configures the rate limiting rules for a new user.
     */
    private Bucket newBucket(String ipAddress) {
        // Defines the capacity and refill rate, 10 requests allowed per minute.
        // refillGreedy adds tokens back smoothly over time, rather than in one block at the end of the minute,
        // which provides a more consistent user experience.
        Bandwidth limit = Bandwidth.builder()
                .capacity(10)
                .refillGreedy(10, Duration.ofMinutes(1))
                .build();

        return Bucket.builder()
                .addLimit(limit)
                .build();
    }
}