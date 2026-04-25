package io.github.sflugum.applicantprecheck.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.github.sflugum.applicantprecheck.dto.PrecheckResponse;
import io.github.sflugum.applicantprecheck.model.Applicant;

import io.github.sflugum.applicantprecheck.service.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping
public class PrecheckController {

	@Autowired
	private PrecheckService precheckService; //call service layer to evaluate the applicant

	@PostMapping
	public ResponseEntity<PrecheckResponse> precheck(@RequestBody Applicant applicant) {
		String status = precheckService.evaluateApplicant(applicant.getCreditScore(), applicant.getIncome());

		PrecheckResponse response = new PrecheckResponse();
		response.setCreditScore(applicant.getCreditScore());
		response.setIncome(applicant.getIncome());
		response.setStatus(status);

		return ResponseEntity.ok(response);
	}

	@GetMapping
	public ResponseEntity<PrecheckResponse> getStatus() {

		PrecheckResponse response = new PrecheckResponse();
		response.setStatus("NO_REQUEST");
		response.setMessage("No application submitted yet");

		return ResponseEntity.ok(response);
	}
}