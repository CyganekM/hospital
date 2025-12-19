package by.asb.traningprogect.hospital.reposotory;

import by.asb.traningprogect.hospital.model.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Integer> {

    List<Card> findCardByPatient_IdAndFinalDiagnosisIsNull(String patientId);
}