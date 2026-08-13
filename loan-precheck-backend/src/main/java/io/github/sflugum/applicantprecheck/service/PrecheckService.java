package io.github.sflugum.applicantprecheck.service;


import io.github.sflugum.applicantprecheck.model.Applicant;
import io.github.sflugum.applicantprecheck.repository.ApplicantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Contains the core business rule for deciding whether an applicant is
 * pre-approved. Every evaluation is persisted, regardless of outcome.
 */
@Service
public class PrecheckService {

	private static final Logger log = LoggerFactory.getLogger(PrecheckService.class);

	private final ApplicantRepository repository;

	public PrecheckService(ApplicantRepository repository) {
		this.repository = repository;
	}

	public String evaluateApplicant(int creditScore, int income) {

		log.info("Evaluating new applicant -> Credit Score: {}, Income: {}", creditScore, income);

		String status;

		// Inquiry validation
		// creditScore/income are already validated at the DTO layer (ApplicantRequest),
		// so this mostly guards against something calling the service directly and skipping that.
		if (creditScore < 300 || creditScore > 850 || income < 0) {
			status = "REVIEW";
		}
		// Anti-fraud/High-income review check
		// Sends unusually high income relative to credit score to manual review instead
		// of auto-approving, since that combination is a common fraud pattern.
		else if (creditScore > 750 && income > 3000000) {
			status = "REVIEW";
		}
		// Standard approval
		else if (creditScore > 650 && income > 40000) {
			status = "APPROVED";
		} else {
			status = "REVIEW";
		}

		log.info("Applicant evaluation complete -> Decision: {}", status);

		// merchantId is hardcoded for now since this project only supports one merchant.
		// Keeping it on the model/DB schema now sets up for multi-merchant support later
		// without needing a schema change, but the API and DTOs stay simple until that's
		// actually needed.
		Applicant applicant = new Applicant(creditScore, income, "DEFAULT_MERCHANT", status);
		repository.save(applicant);

		return status;
	}
}
