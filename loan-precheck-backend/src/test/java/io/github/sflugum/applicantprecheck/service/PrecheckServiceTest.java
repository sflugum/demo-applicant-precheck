package io.github.sflugum.applicantprecheck.service;

import io.github.sflugum.applicantprecheck.model.Applicant;
import io.github.sflugum.applicantprecheck.repository.ApplicantRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

/*
Unit tests for the PrecheckService.
Validates the core business logic for evaluating applicant qualifications
and ensures data is properly passed to the persistence layer.
 */
@ExtendWith(MockitoExtension.class)
public class PrecheckServiceTest {

	@Mock
	private ApplicantRepository repository;

	@InjectMocks
	private PrecheckService precheckService;

	@Test
	@DisplayName("Should return APPROVED and save applicant when credit score and income meet requirements")
	void whenValidApplicant_thenReturnsApproved() {
		// Arrange: Input parameters for a highly qualified applicant
		int creditScore = 700;
		int income = 50000;

		// Act: Evaluate the applicant using the service
		String status = precheckService.evaluateApplicant(creditScore, income);

		// Assert: The status should be APPROVED and the applicant should be saved to the database
		assertEquals("APPROVED", status);
		verify(repository).save(any(Applicant.class));
	}

	@Test
	@DisplayName("Should return REVIEW and save applicant when credit score is below the automatic approval threshold")
	void whenLowCreditScore_thenReturnsReview() {
		// Arrange: Input parameters for and applicant needing manual review
		int creditScore = 600;
		int income = 50000;

		// Act: Evaluate the applicant using the service
		String status = precheckService.evaluateApplicant(creditScore, income);

		// Assert: The status should flag for REVIEW and the applicant should still be saved
		assertEquals("REVIEW", status);
		verify(repository).save(any(Applicant.class));
	}
}
