package io.github.sflugum.applicantprecheck.service;

import io.github.sflugum.applicantprecheck.model.Applicant;
import io.github.sflugum.applicantprecheck.repository.ApplicantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PrecheckServiceTest {

	@Mock
	private ApplicantRepository repository;

	@InjectMocks
	private PrecheckService precheckService;

	@Test
	void whenValidApplicant_thenReturnsApproved() {
		String status = precheckService.evaluateApplicant(700, 50000);
		assertEquals("APPROVED", status);
		verify(repository).save(any(Applicant.class));
	}

	@Test
	void whenLowCreditScore_thenReturnsReview() {
		String status = precheckService.evaluateApplicant(600, 50000);
		assertEquals("REVIEW", status);
		verify(repository).save(any(Applicant.class));
	}
}
