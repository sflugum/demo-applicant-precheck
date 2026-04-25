package io.github.sflugum.applicantprecheck.dto;

public class ApplicantRequest {
	private int creditScore;
	private double salary;
	
	public int getCreditScore() {
		return creditScore;
	}
	public void setCreditScore(int creditScore) {
		this.creditScore = creditScore;
	}
	
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	
}
