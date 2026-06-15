package io.github.sflugum.applicantprecheck.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import io.github.sflugum.applicantprecheck.model.Applicant;
import io.github.sflugum.applicantprecheck.repository.ApplicantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PrecheckServiceTest {

	@Mock
	private ApplicantRepository repository;

	@InjectMocks
	private PrecheckService service;
	
	@Test
	public void testHighCreditHighIncome_ShouldBeApproved() {
		String result = service.evaluateApplicant(750, 50000);
		assertEquals("APPROVED", result);

		verify(repository).save(any(Applicant.class));
	}
	
	@Test
	public void testLowCredit_ShouldBeReview() {
		String result = service.evaluateApplicant(300, 50000);
		assertEquals("REVIEW", result);

		verify(repository).save(any(Applicant.class));
	}
}
