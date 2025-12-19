package by.asb.traningprogect.hospital.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO for {@link Card}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record CardTestDto(Integer id, Integer employeeId, @NotNull @Size(max = 100) String employeeName,
                          String preliminaryDiagnosis) {
}