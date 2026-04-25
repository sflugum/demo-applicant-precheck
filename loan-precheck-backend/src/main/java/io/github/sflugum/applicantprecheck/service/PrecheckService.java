package io.github.sflugum.applicantprecheck.service;

import org.springframework.stereotype.Service;

@Service
public class PrecheckService {
	public String evaluateApplicant(int creditScore, int income) {

		// validation first
		if (creditScore < 300 || creditScore > 850 || income < 0) {
			return "REVIEW";
		}

		// business rules | Anti-fraud/High-income review check
		else if (creditScore > 750 && income > 3000000) {
			return "REVIEW";
		}

		// business rules | standard approval
		else if (creditScore > 650 && income > 40000) {
			return "APPROVED";
		} else {
			return "REVIEW";
		}
	}
}
