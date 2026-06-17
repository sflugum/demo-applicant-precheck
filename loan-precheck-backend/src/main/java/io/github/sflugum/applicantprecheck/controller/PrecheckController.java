package io.github.sflugum.applicantprecheck.controller;


import io.github.sflugum.applicantprecheck.service.PrecheckService;
import io.github.sflugum.applicantprecheck.dto.PrecheckResponse;
import io.github.sflugum.applicantprecheck.dto.ApplicantRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/precheck")
@CrossOrigin(origins = "http://localhost:3000")
public class PrecheckController {

	private final PrecheckService precheckService;

	public PrecheckController(PrecheckService precheckService) {
		this.precheckService = precheckService;
	}

	@PostMapping("/precheck")
	public ResponseEntity<PrecheckResponse> evaluateApplicant(@Valid @RequestBody ApplicantRequest request) {

		String decision = precheckService.evaluateApplicant(request.getCreditScore(), request.getIncome());

		PrecheckResponse response = new PrecheckResponse();

		return ResponseEntity.ok(response);
	}
}