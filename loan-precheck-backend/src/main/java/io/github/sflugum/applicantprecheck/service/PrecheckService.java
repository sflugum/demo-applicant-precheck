package io.github.sflugum.applicantprecheck.service;


import io.github.sflugum.applicantprecheck.model.Applicant;
import io.github.sflugum.applicantprecheck.repository.ApplicantRepository;
import org.springframework.stereotype.Service;

@Service
public class PrecheckService {

	private final ApplicantRepository repository;

	public PrecheckService(ApplicantRepository repository) {
		this.repository = repository;
	}

	public String evaluateApplicant(int creditScore, int income) {

		String status;

		// Inquiry validation
		if (creditScore < 300 || creditScore > 850 || income < 0) {
			status = "REVIEW";
		}

		// Anti-fraud/High-income review check
		else if (creditScore > 750 && income > 3000000) {
			status = "REVIEW";
		}

		// Standard approval
		else if (creditScore > 650 && income > 40000) {
			status = "APPROVED";
		} else {
			status = "REVIEW";
		}

		Applicant applicant = new Applicant(creditScore, income, "DEFAULT_MERCHANT", status);

		repository.save(applicant);

		return status;
	}
}
