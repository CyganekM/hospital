package by.asb.traningprogect.hospital.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class CardCreatorDto implements Serializable {
    Integer id;
    @NotBlank(message = "The patient's preliminaryDiagnosis cannot be empty")
    String preliminaryDiagnosis;
    LocalDate preliminaryDiagnosisDate;
    String finalDiagnosis;
    LocalDate finalDiagnosisDate;
    String description;
    @NotNull
    EmployeeGetCardDto employee;
    @NotNull
    PatientCardDto patient;
}