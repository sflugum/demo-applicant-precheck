package com.example.demo.controller;

import com.example.demo.dto.PrecheckResponse;
import com.example.demo.model.Applicant;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/precheck")
public class PrecheckController {

    @PostMapping
    public ResponseEntity<PrecheckResponse> precheck(@RequestBody Applicant applicant) {

        int creditScore = applicant.getCreditScore();
        int income = applicant.getIncome();

        PrecheckResponse response = new PrecheckResponse();
        response.setCreditScore(creditScore);
        response.setIncome(income);

     // validation first
        if (creditScore < 300 || creditScore > 850 || income < 0) {
            response.setStatus("REVIEW");
        }
        else if (creditScore > 750 && income > 3000000) {
            response.setStatus("REVIEW");
        }
        else if (creditScore > 650 && income > 40000) {
            response.setStatus("APPROVED");
        }
        else {
            response.setStatus("REVIEW");
        }

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