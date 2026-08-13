package io.github.sflugum.applicantprecheck.repository;

import io.github.sflugum.applicantprecheck.model.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Standard JPA repository for Applicant. No custom queries yet since the
 * app currently only writes evaluation records, it doesn't read them back.
 */
@Repository
public interface ApplicantRepository extends JpaRepository<Applicant, Long> {
}