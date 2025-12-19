package by.asb.traningprogect.hospital.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class CardResponseDto implements Serializable {
    Integer id;
    Integer employeeId;
    String patientId;
    String preliminaryDiagnosis;
    LocalDate preliminaryDiagnosisDate;
    String finalDiagnosis;
    LocalDate finalDiagnosisDate;
    String description;
    List<ProcedureDto> procedures;
}