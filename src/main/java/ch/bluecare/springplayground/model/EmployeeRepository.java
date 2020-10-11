package ch.bluecare.springplayground.model;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Supports creating new instances, updating existing ones, deleting and finding.
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long> {}
