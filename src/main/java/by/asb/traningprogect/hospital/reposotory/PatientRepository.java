package by.asb.traningprogect.hospital.reposotory;

import by.asb.traningprogect.hospital.model.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, String>, JpaSpecificationExecutor<Patient> {

    List<Patient> findByStatus(byte status);
}