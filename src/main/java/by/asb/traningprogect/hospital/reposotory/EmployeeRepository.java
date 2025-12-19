package by.asb.traningprogect.hospital.reposotory;

import by.asb.traningprogect.hospital.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Optional<Employee> findByEmail(String email);
    boolean existsByEmail(String email);
}