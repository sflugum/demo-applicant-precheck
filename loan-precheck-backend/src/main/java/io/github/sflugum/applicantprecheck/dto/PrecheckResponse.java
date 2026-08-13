package io.github.sflugum.applicantprecheck.dto;

/**
 * Response payload sent back to the frontend after a precheck request.
 * The type and message fields exist for future error/detail reporting.
 * PrecheckController currently only ever sets status.
 */
public class PrecheckResponse {

    private String status;   // APPROVED, REVIEW, 
    private String type;     // Error handling, validation vs server
    private String message;

    private Integer creditScore;
    private Integer income;

    public PrecheckResponse() {}

    public PrecheckResponse(String status, String type, String message, Integer creditScore, Integer income) {
        this.status = status;
        this.type = type;
        this.message = message;
        this.creditScore = creditScore;
        this.income = income;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(Integer creditScore) {
        this.creditScore = creditScore;
    }

    public Integer getIncome() {
        return income;
    }

    public void setIncome(Integer income) {
        this.income = income;
    }
}