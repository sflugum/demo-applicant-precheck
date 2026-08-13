package io.github.sflugum.applicantprecheck.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit tests for the PrecheckController.
 * Verifies that the API endpoint correctly receives HTTP requests, processes payloads,
 * and returns the expected JSON response. Currently only covers the approval path.
 * No test yet for a REVIEW result or an invalid payload.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class PrecheckControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Should return HTTP 200 and APPROVED status for a valid applicant payload")
    void whenValidApplicationSubmitted_thenReturns200OkAndApproved() throws Exception {
        // Arrange: Prepare a valid JSON payload representing an applicant's data
        String jsonPayload = "{\"creditScore\": 720, \"income\": 60000}";

        // Act: Perform a POST request to the precheck endpoint
        mockMvc.perform(post("/api/precheck")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPayload))

        // Assert: Verify the HTTP status is 200 OK and the JSON response contains the correct approval status
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("APPROVED"));
    }

}
