package io.github.sflugum.applicantprecheck.security;

import io.github.bucket4j.Bucket;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/*
Intercepts incoming HTTP requests to enforce rate limits per user IP address.
Extends OncePerRequestFilter to guarantee it only fires once per dispatch lifecycle.
 */
@Component
public class RateLimitingFilter extends OncePerRequestFilter {

    private final RateLimitingService rateLimitingService;

    public RateLimitingFilter(RateLimitingService rateLimitingService) {
        this.rateLimitingService = rateLimitingService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        // Extract and normalize the client IP to ensure accurate rate limiting
        String ipAddress = getClientIp(request);
        Bucket bucket = rateLimitingService.resolveBucket(ipAddress);

        // Try to consume 1 token. If the bucket is empty, reject the request immediately
        // before it reaches the controllers, protecting backend resources.
        if (!bucket.tryConsume(1)) {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.getWriter().write("Too many requests. Please wait one moment.");
            return;
        }

        // Token consumed successfully; allow the request to proceed
        filterChain.doFilter(request, response);
    }

    /*
    Resolves the true IP address of the client, handling reverse proxies and
    formatting inconsistencies to prevent rate limit bypasses.
     */
    private String getClientIp(HttpServletRequest request) {
        // The application runs behind a reverse proxy (Render), so the true client IP
        // is injected into the X-Forwarded-For header.
        String ipAddress = request.getHeader("X-Forwarded-For");

        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        } else {
            // X-Forwarded-For can contain multiple IPs if routed through multiple proxies.
            // The first IP in the comma-separated list is the original client.
            ipAddress = ipAddress.split(",")[0].trim();
        }

        return normalizeIp(ipAddress);
    }

    /*
    Normalize IP addresses to a standard format.
    Specifically protects against an exploit where an attacker exhausts their IPv4
    rate limit, then bypasses it by sending the IPv4-mapped IPv6 equivalent.
     */
    private String normalizeIp(String ip) {
        if (ip == null) {
            return null;
        }

        // IPv6 is not case-sensitive, normalize to lowercase first.
        String lowerIp = ip.toLowerCase();
        if (lowerIp.startsWith("::ffff:")) {
            // Strip the IPv6 mapping prefix (length of 7) to return the standard IPv4
            return lowerIp.substring(7);
        }

        // Handles standard compressed IPv4-mapped IPv6 addresses (::ffff:) to prevent
        // rate limit bypasses. Note: Uncompressed variations (e.g., 0:0:0:0:0:ffff:)
        // are not covered here to avoid brittle custom parsing logic. A production
        // environment should replace this with an established IP parsing library.
        return ip;
    }
}