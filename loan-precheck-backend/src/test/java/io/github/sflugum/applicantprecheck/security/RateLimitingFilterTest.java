package io.github.sflugum.applicantprecheck.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import jakarta.servlet.ServletException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
* Unit tests for the RateLimitingFilter.
* Ensures that HTTP requests are properly allowed or blocked based on IP address limits.
*/
public class RateLimitingFilterTest {

    private RateLimitingFilter filter;

    @BeforeEach
    void setUp() {
        // Arrange: Instantiate dependencies manually to keep tests fast and isolated
        RateLimitingService rateLimitingService = new RateLimitingService();
        filter = new RateLimitingFilter(rateLimitingService);
    }

    @Test
    @DisplayName("Should allow normal traffic when user is under the rate limit")
    void shouldAllowRequestsUnderLimit() throws ServletException, IOException {
        // Arrange: Simulate a standard HTTP request from a user
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRemoteAddr("127.0.0.1");
        MockHttpServletResponse response = new MockHttpServletResponse();

        // Act: Pass the request through the filter
        filter.doFilter(request, response, new MockFilterChain());

        // Assert: The request should be allowed to proceed (HTTP 200 OK)
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    @DisplayName("Should block user with HTTP 429 when they exceed the maximum allowed requests")
    void shouldReturn429WhenLimitExceeded() throws ServletException, IOException {
        // Arrange: Simulate a user exhausting their 10 allowed requests
        String ip = "10.0.0.50";
        for (int i = 0; i < 10; i++) {
            MockHttpServletRequest request = new MockHttpServletRequest();
            request.setRemoteAddr(ip);
            MockHttpServletResponse response = new MockHttpServletResponse();

            filter.doFilter(request, response, new MockFilterChain());
            assertEquals(HttpStatus.OK.value(), response.getStatus(), "First 10 requests should pass.");
        }

        MockHttpServletRequest blockedRequest = new MockHttpServletRequest();
        blockedRequest.setRemoteAddr(ip);
        MockHttpServletResponse blockedResponse = new MockHttpServletResponse();

        // Act: Attempt the 11th request
        filter.doFilter(blockedRequest, blockedResponse, new MockFilterChain());

        // Assert: The filter should intercept and return a 429 Too Many Requests error
        assertEquals(HttpStatus.TOO_MANY_REQUESTS.value(), blockedResponse.getStatus());
        assertEquals("Too many requests. Please wait one moment.", blockedResponse.getContentAsString());
    }

    @Test
    @DisplayName("Should isolate rate limits correctly when users share a proxy IP (X-Forwarded-For)")
    void shouldIsolateUsersBehindSameProxy() throws ServletException, IOException {
        // Arrange: Set up a scenario where an attacker and an innocent user share the same proxy IP
        String renderProxyIp = "198.51.100.254";
        String attackerIp = "203.0.113.1";
        String innocentUserIp = "203.0.113.2";

        // The attacker spams the server and exhausts rate limit of 10
        for (int i = 0; i < 11; i++) {
            MockHttpServletRequest request = new MockHttpServletRequest();
            request.setRemoteAddr(renderProxyIp);
            request.addHeader("X-Forwarded-For", attackerIp);
            MockHttpServletResponse response = new MockHttpServletResponse();

            filter.doFilter(request, response, new MockFilterChain());

            if(i == 10) {
                assertEquals(HttpStatus.TOO_MANY_REQUESTS.value(), response.getStatus(), "Attacker should be blocked");
            }
        }

        // Arrange: Innocent user makes request through same proxy
        MockHttpServletRequest innocentRequest = new MockHttpServletRequest();
        innocentRequest.setRemoteAddr(renderProxyIp);
        innocentRequest.addHeader("X-Forwarded-For", innocentUserIp);
        MockHttpServletResponse innocentResponse = new MockHttpServletResponse();

        // Act: Process the innocent user's request
        filter.doFilter(innocentRequest, innocentResponse, new MockFilterChain());

        // Assert: Innocent user should NOT be blocked
        assertEquals(HttpStatus.OK.value(), innocentResponse.getStatus(),"Innocent user should be allowed through");
    }

    @Test
    @DisplayName("Should prevent rate limit bypass using IPv4-mapped IPv6 addresses")
    void shouldPreventRateLimitBypass_ViaIPv4MappedIPv6() throws ServletException, IOException {
        // Arrange: Set up the attacker's IPv4 and its IPv6-mapped equivalent
        String renderProxyIp = "198.51.100.254";
        String attackerIpv4 = "203.0.113.5";
        String attackerMappedIpv6 = "::ffff:203.0.113.5";

        // 1. Attacker exhausts their rate limit using standard IPv4
        for (int i = 0; i < 10; i++) {
            MockHttpServletRequest request = new MockHttpServletRequest();
            request.setRemoteAddr(renderProxyIp);
            request.addHeader("X-Forwarded-For", attackerIpv4);
            MockHttpServletResponse response = new MockHttpServletResponse();

            filter.doFilter(request, response, new MockFilterChain());
        }

        // 2. Attacker attempts to bypass the limit by using the IPv4-mapped IPv6 notation
        MockHttpServletRequest bypassRequest = new MockHttpServletRequest();
        bypassRequest.setRemoteAddr(renderProxyIp);
        bypassRequest.addHeader("X-Forwarded-For", attackerMappedIpv6);
        MockHttpServletResponse bypassResponse = new MockHttpServletResponse();

        // Act: Process attacker's bypass attempt
        filter.doFilter(bypassRequest, bypassResponse, new MockFilterChain());

        // Assert: The system should normalize the IP and block the request anyway
        assertEquals(HttpStatus.TOO_MANY_REQUESTS.value(), bypassResponse.getStatus(),
                "Attacker should be blocked even when attempting to bypass using an IPv4-mapped IPv6 address");
    }
}
