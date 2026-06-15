package io.github.sflugum.applicantprecheck.repository;

import io.github.sflugum.applicantprecheck.model.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicantRepository extends JpaRepository<Applicant, Long> {
}