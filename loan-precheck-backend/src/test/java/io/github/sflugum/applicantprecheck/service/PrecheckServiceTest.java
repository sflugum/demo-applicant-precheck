package io.github.sflugum.applicantprecheck.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;


public class PrecheckServiceTest {
	
	private PrecheckService service = new PrecheckService();
	
	@Test
	public void testHighCreditHighIncome_ShouldBeApproved() {
		String result = service.evaluateApplicant(750, 50000);
		assertEquals("APPROVED", result);
	}
	
	@Test
	public void testLowCredit_ShouldBeReview() {
		String result = service.evaluateApplicant(300, 50000);
		assertEquals("REVIEW", result);
	}

}
