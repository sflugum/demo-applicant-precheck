package io.github.sflugum.applicantprecheck.controller;


import io.github.sflugum.applicantprecheck.service.PrecheckService;
import io.github.sflugum.applicantprecheck.dto.PrecheckResponse;
import io.github.sflugum.applicantprecheck.dto.ApplicantRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Exposes the precheck endpoint the frontend submits applicant data to.
 * Request validation happens automatically via @Valid, with failures
 * handled by GlobalExceptionHandler rather than here.
 * CORS is handled globally by CorsConfig.
 */
@RestController
@RequestMapping("/api/precheck")
public class PrecheckController {

	private final PrecheckService precheckService;

	public PrecheckController(PrecheckService precheckService) {
		this.precheckService = precheckService;
	}

	@PostMapping
	public ResponseEntity<PrecheckResponse> evaluateApplicant(@Valid @RequestBody ApplicantRequest request) {

		String decision = precheckService.evaluateApplicant(request.getCreditScore(), request.getIncome());

		PrecheckResponse response = new PrecheckResponse();
		response.setStatus(decision);

		return ResponseEntity.ok(response);
	}
}